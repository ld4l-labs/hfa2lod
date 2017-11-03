/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.csv.hfa.LocationConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.LocationConcordanceManager;
import org.ld4l.bib2lod.csv.hfa.MaterialTypeConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.MaterialTypeConcordanceManager;
import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.externalbuilders.ConcordanceReferenceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToCharacteristicsConcordanceBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.hfa.HfaEventType;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedType;
import org.ld4l.bib2lod.ontology.hfa.HfaHistoryType;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.hfa.HfaLoan;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds an Item individual from a Record.
 */
public class HfaToItemBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity instance;
    private Entity item;
    private List<HfaLoan> hfaLoans;
	private MaterialTypeConcordanceManager materialTypeConcordanceManager;
    private LocationConcordanceManager locationConcordanceManager;

	public HfaToItemBuilder() throws ConverterException {
    	try {
			this.materialTypeConcordanceManager = new MaterialTypeConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate MaterialTypeConcordanceManager", e);
		}
    	try {
			this.locationConcordanceManager = new LocationConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate LocationConcordanceManager", e);
		}
	}
	
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (HfaRecord) params.getRecord();
		if (record == null) {
			throw new EntityBuilderException(
					"A HfaRecord is required to build an Item.");
		}

		this.instance = params.getParent();
        if (instance == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an Item.");
        }

        this.item = new Entity(Ld4lItemType.ITEM);
        
        buildTitle();
		addCharacteristics();
        
        // add running time
        HfaTextField hfaTime = record.getField(ColumnAttributeText.HFA_TIME);
        if (hfaTime != null) {
        	String iso8601Duration = "P" + hfaTime.getTextValue() + "M";
        	item.addAttribute(HfaDatatypeProp.DURATION_SCHEMA, iso8601Duration);
        	item.addAttribute(HfaDatatypeProp.DURATION_BF, new Attribute(hfaTime.getTextValue(), "en"));
        }
        
        // A custodialHistoryEvent and itemEvent Entity will be built and added
        // to the item iff there is either loan donor data in the record.
        Entity itemEvent = null;
        
    	HfaTextField donorField = record.getField(ColumnAttributeText.DONATED_BY);
    	if (donorField != null) {
    		itemEvent = buildCustodialAndItemEventEntitiesAndAddToItemEntity();
        	buildGiftEvent(donorField, itemEvent);
    	}
        
        // Building this section depends solely on the existence of HfaLoan values.
        hfaLoans = record.getHfaLoanFields();
        if ( !hfaLoans.isEmpty()) {
        	
        	if (itemEvent == null) {
        		itemEvent = buildCustodialAndItemEventEntitiesAndAddToItemEntity();
        	}
        	
        	buildLoanEvent(itemEvent);
        	buildExhibitionEvent();
        }
        
        // add HFA Formats and Elements
        addHfaFormatAndElement();

        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
        
        return item;
    }
    
    /*
     * Build custodial event Entity, add to Item Entity, build item event Entity, add it
     * to custodial event Entity.
     * Return the item event Entity for later use.
     */
    private Entity buildCustodialAndItemEventEntitiesAndAddToItemEntity() {

        Entity custodialHistoryEvent = new Entity(HfaHistoryType.CUSTODIAL_HISTORY);
		custodialHistoryEvent.addAttribute(Ld4lDatatypeProp.LABEL, HfaHistoryType.CUSTODIAL_HISTORY.label());
		item.addRelationship(HfaObjectProp.HAS_CUSTODIAL_HISTORY, custodialHistoryEvent);
		
		Entity itemEvent = new Entity(HfaEventType.ITEM_EVENT);
		custodialHistoryEvent.addRelationship(Ld4lObjectProp.HAS_PART, itemEvent);

		return itemEvent;
    }
    
    private void buildTitle() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setField(record.getField(HfaRecord.ColumnAttributeText.TITLE))
                .setParent(item);
        Entity titleEntity = builder.build(params);
        // add primary title as label for this entity
        item.addAttribute(Ld4lDatatypeProp.LABEL, titleEntity.getAttribute(Ld4lDatatypeProp.LABEL));
    }
    
    private void addCharacteristics() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToCharacteristicsConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(item);        
        builder.build(params);
    }
    
    private void buildLoanEvent(Entity itemEvent) throws EntityBuilderException {
    	
    	List<HfaLoan> loans = record.getHfaLoanFields();
    	
    	for (HfaLoan loan : loans) {

    		Entity loanEvent = new Entity(HfaEventType.LOAN_EVENT);
    		itemEvent.addRelationship(Ld4lObjectProp.IS_PART_OF, loanEvent);
    		
    		HfaTextField dateOfLoan = loan.getField(HfaLoan.ColumnAttributeText.DATE_OF_LOAN);
    		if (dateOfLoan != null) {
    			loanEvent.addAttribute(Ld4lDatatypeProp.DATE, dateOfLoan.getTextValue().trim(), XsdDatatype.DATE);
    		}
    		
    		String location = getLocation(loan, false);
    		if (location != null) {
    			location = location.trim();
				LocationConcordanceBean bean = locationConcordanceManager.getConcordanceEntry(location);
	    		if (bean != null) {
	    			String uri = bean.getUri();
	    			loanEvent.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, uri);
	    		}
    		}
    		
    		HfaTextField outsideBorrower = loan.getField(HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER);
    		HfaTextField companyName = loan.getField(HfaLoan.ColumnAttributeText.COMPANY_NAME);
    		
    		StringBuilder agentText = new StringBuilder();
    		if (outsideBorrower != null) {
    			agentText.append(outsideBorrower.getTextValue().trim());
    			if (companyName != null) {
    				agentText.append(", ");
    			}
    		}
			if (companyName != null) {
				agentText.append(companyName.getTextValue().trim());
			}
    		
    		
    		EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);
    		BuildParams params = new BuildParams()
    				.setRecord(record)
    				.setParent(loanEvent)
    				.setType(HfaActivityType.BORROWER_ACTIVITY)
    				.setValue(agentText.toString());
    		builder.build(params);
    		
    		builder = getBuilder(Ld4lActivityType.ACTIVITY);
    		params = new BuildParams()
    				.setRecord(record)
    				.setParent(loanEvent)
    				.setType(HfaActivityType.LENDER_ACTIVITY);
    		builder.build(params);
    	}
    	
    }
    
    private void buildGiftEvent(HfaTextField donorField, Entity itemEvent) throws EntityBuilderException {
    	
    	if (donorField == null) {
    		return;
    	}

    	Entity giftEvent = new Entity(HfaEventType.GIFT_EVENT);
		itemEvent.addRelationship(Ld4lObjectProp.IS_PART_OF, giftEvent);
		
		EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);
		BuildParams params = new BuildParams()
				.setRecord(record)
				.setParent(giftEvent)
				.setType(HfaActivityType.DONOR_ACTIVITY)
				.setValue(donorField.getTextValue());
		builder.build(params);
    }

    private void buildExhibitionEvent() throws EntityBuilderException {
    	
    	List<HfaLoan> loans = record.getHfaLoanFields();
    	
    	// Creating the ExhibitionEvent here rather than calling a separate builder class.
    	for (HfaLoan loan : loans) {
    		HfaTextField playDate = loan.getField(HfaLoan.ColumnAttributeText.PLAY_DATE);
    		String location = getLocation(loan, true);
    		
    		// need at least one of these two to create this individual
    		if (playDate == null && location == null) {
    			continue;
    		}
    		
    		Entity exhibitionEvent = new Entity(HfaEventType.EXHIBITION_EVENT);
    		exhibitionEvent.addAttribute(Ld4lDatatypeProp.LABEL, HfaEventType.EXHIBITION_EVENT.label());
    		
    		if (playDate != null) {
    			exhibitionEvent.addAttribute(Ld4lDatatypeProp.DATE, playDate.getTextValue().trim(), XsdDatatype.DATE);
    		}
	    	
    		if (location != null) {
    			location = location.trim().replace("?", "");
				LocationConcordanceBean bean = locationConcordanceManager.getConcordanceEntry(location);
	    		if (bean != null) {
	    			String uri = bean.getUri();
	    			exhibitionEvent.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, uri);
	    		}
    		}

    		item.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, exhibitionEvent);
    	}
    }
    
    private String getLocation(HfaLoan loan, boolean includeCity) {
    	String location = null;
    	
		StringBuilder locationSb = new StringBuilder();
		
		if (includeCity) {
			HfaTextField city = loan.getField(HfaLoan.ColumnAttributeText.CITY);
			if (city != null) {
				locationSb.append(city.getTextValue().trim());
			}
		}
		
		HfaTextField stateProv = loan.getField(HfaLoan.ColumnAttributeText.STATE_PROVINCE);
		if (stateProv != null) {
			if (locationSb.length() > 0) {
				locationSb.append(", ");
			}
			locationSb.append(stateProv.getTextValue().trim());
		}
		HfaTextField country = loan.getField(HfaLoan.ColumnAttributeText.COUNTRY);
		if (country != null) {
			if (locationSb.length() > 0) {
				locationSb.append(", ");
			}
			locationSb.append(country.getTextValue().trim());
		}

		if (locationSb.length() > 0) {
			location = locationSb.toString();
		}
    	
    	return location;
    }
    
    // adding the "HFA Format" and not the "Format"
    private void addHfaFormatAndElement() throws EntityBuilderException {
    
    	HfaTextField hfaFormatField = record.getField(ColumnAttributeText.HFA_FORMAT);
    	if (hfaFormatField!= null) {
    		MaterialTypeConcordanceBean bean = materialTypeConcordanceManager.getConcordanceEntry(hfaFormatField.getTextValue());
    		if (bean != null) {
    			String ontClass = bean.getOntClass();
    			Type type = null;
    			try {
    				String[] parts = ontClass.split(":");
    				HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
    				type = new HfaGeneratedType(ns, parts[1]);
    			} catch (Exception e) {
    				throw new EntityBuilderException("Could not parse HfaGeneratedNamedIndividual from concordance value: " + ontClass);
    			}
    			item.addType(type);
    		}
    	}
    	
    	HfaTextField hfaElementField = record.getField(ColumnAttributeText.ELEMENT);
    	if (hfaElementField != null) {
    		MaterialTypeConcordanceBean bean = materialTypeConcordanceManager.getConcordanceEntry(hfaElementField.getTextValue());
    		if (bean != null) {
    			String ontClass = bean.getOntClass();
    			Type type = null;
    			try {
    				String[] parts = ontClass.split(":");
    				HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
    				type = new HfaGeneratedType(ns, parts[1]);
    			} catch (Exception e) {
    				throw new EntityBuilderException("Could not parse HfaGeneratedNamedIndividual from concordance value: " + ontClass);
    			}
    			item.addType(type);
    		}
    	}
    }

}
