/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.hfa.HfaEventType;
import org.ld4l.bib2lod.ontology.hfa.HfaHistoryType;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaLoan;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds an Item individual from a Record.
 */
public class HfaToItemBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity instance;
    private Entity item;
    private List<HfaLoan> hfaLoans;
  
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
        
        // Building this Entity depends solely on the existence of HfaLoan values.
        hfaLoans = record.getHfaLoanFields();
        if (hfaLoans.isEmpty()) {
        	return null;
        }

        this.item = new Entity(Ld4lItemType.ITEM);
        Attribute labelAttr = instance.getAttribute(Ld4lDatatypeProp.LABEL);
        if (labelAttr != null) {
        	this.item.addAttribute(Ld4lDatatypeProp.LABEL, labelAttr.getValue());
        }
        
        // FIXME: This might be the wrong type and, also, the construct may not conform to ontology.
        Entity custodialHistoryEvent = new Entity(HfaHistoryType.CUSTODIAL_HISTORY);
        custodialHistoryEvent.addAttribute(Ld4lDatatypeProp.LABEL, HfaHistoryType.CUSTODIAL_HISTORY.label());
        item.addRelationship(HfaObjectProp.HAS_CUSTODIAL_HISTORY, custodialHistoryEvent);
        
        Entity itemEvent = new Entity(HfaEventType.ITEM_EVENT);
        custodialHistoryEvent.addRelationship(Ld4lObjectProp.HAS_PART, itemEvent);
        
        buildLoanEvent(itemEvent);
        buildExhibitionEvent();
        
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
        
        return item;
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
    		
    		// FIXME: This needs to be transformed into a geolocation URI via concordance file.
    		// TODO: lookup exteral URI for location in concordance file
    		// String locationUri = concordance.lookup(location);
    		String location = getLocation(loan, false);
    		if (location != null) {
    			loanEvent.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, location);
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
    		// FIXME: This needs to be transformed into a geolocation URI via concordance file.
			// TODO: lookup exteral URI for location in concordance file
			// String locationUri = concordance.lookup(location);
    		if (location != null) {
    			exhibitionEvent.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, location);
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

}
