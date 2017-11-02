/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.io.IOException;
import java.net.URISyntaxException;

import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.csv.hfa.MaterialTypeConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.MaterialTypeConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.externalbuilders.ConcordanceReferenceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToCharacteristicsConcordanceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToSoundConcordanceBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaCollectionType;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedType;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds an Instance individual from a Record.
 */
public class HfaToInstanceBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity work;
    private Entity instance;
	private MaterialTypeConcordanceManager concordanceManager;
  
	public HfaToInstanceBuilder() throws ConverterException {
    	try {
			this.concordanceManager = new MaterialTypeConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate MaterialTypeConcordanceManager", e);
		}
	}
	
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (HfaRecord) params.getRecord();
		if (record == null) {
			throw new EntityBuilderException(
					"A HfaRecord is required to build an Instance.");
		}

		this.work = params.getParent();
        if (work == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an Instance.");
        }

        // need to pass title along
        this.instance = new Entity(Ld4lInstanceType.INSTANCE);
        
        buildTitles();
        buildCollection();
        buildItem();
        addLanguages();
        addOriginalFormat();
		addCharacteristics();			
        
        work.addRelationship(Ld4lObjectProp.HAS_INSTANCE, instance);
        
        return instance;
    }
    
    private void buildTitles() throws EntityBuilderException {
    	
    	HfaTextField hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.TITLE);
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setField(hfaTitleField)
                .setParent(instance);
        Entity titleEntity = builder.build(params);
        // add primary title as label for this entity
        instance.addAttribute(Ld4lDatatypeProp.LABEL, titleEntity.getAttribute(Ld4lDatatypeProp.LABEL));
        
        // build any alternative title records
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.ALTERNATE_TITLE);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
        
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.ALSO_KNOWN_AS_TITLE);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
        
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.AKA_TITLE);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
        
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.ORIGINAL_TITLE);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
        
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.ENGLISH_TITLE);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
        
        hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.TITLE_ON_PRINT);
        if (hfaTitleField != null) {
        	params.setField(hfaTitleField);
        	builder.build(params);
        }
    }
    
    private void buildCollection() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(HfaCollectionType.COLLECTION);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.ITEM);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void addLanguages() {
    	
    	// TODO: will not need this eventually once concordances are complete
    	String tempUriBase = "http://localhost/bogus-base/";
    	
    	// FIXME: lookup exteral URI for language in concordance file
    	HfaTextField field = record.getField(ColumnAttributeText.LANGUAGE);
    	if (field != null) {
    		// String uri = concordance.getLanguage(field.field.getTextValue().trim()
    		// if (uri != null) { add external relationship, otherwise do nothing }
    		String fieldText = field.getTextValue().trim();
    		fieldText = fieldText.replace(' ', '_').replace("\n", "_")
    				.replace("[", "").replace("]", ""); // TODO: remove - temporary until there is a URI
    		instance.addExternalRelationship(Ld4lObjectProp.HAS_LANGUAGE, tempUriBase + fieldText);
    	}
    	
    	// FIXME: lookup exteral URI for language in concordance file
    	field = record.getField(ColumnAttributeText.SUBTITLES_LANGUAGE);
    	if (field != null) {
    		// String uri = concordance.getLanguage(field.field.getTextValue().trim()
    		// if (uri != null) { add external relationship, otherwise do nothing }
    		String fieldText = field.getTextValue().trim();
    		fieldText = fieldText.replace(' ', '_').replace("\n", "_")
    				.replace("[", "").replace("]", ""); // TODO: remove - temporary until there is a URI
    		instance.addExternalRelationship(HfaObjectProp.HAS_SUBTITLE_LANGUAGE, tempUriBase + fieldText);
    	}
    	
    	// FIXME: lookup exteral URI for language in concordance file
    	field = record.getField(ColumnAttributeText.INTERTITLES_LANGUAGE);
    	if (field != null) {
    		// String uri = concordance.getLanguage(field.field.getTextValue().trim()
    		// if (uri != null) { add external relationship, otherwise do nothing }
    		String fieldText = field.getTextValue().trim();
    		fieldText = fieldText.replace(' ', '_').replace("\n", "_")
    				.replace("[", "").replace("]", ""); // TODO: remove - temporary until there is a URI
    		instance.addExternalRelationship(HfaObjectProp.HAS_INTERTITLE_LANGUAGE, tempUriBase + fieldText);
    	}
    }
    
    private void addCharacteristics() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToCharacteristicsConcordanceBuilder();
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);        
        builder.build(params);

        builder = new HfaToSoundConcordanceBuilder();
        params = new BuildParams()
                .setRecord(record)
                .setParent(instance);        
        builder.build(params);
    }
    
    private void addOriginalFormat() throws EntityBuilderException {
    	
    	HfaTextField originalFormatField = record.getField(ColumnAttributeText.ORIGINAL_FORMAT);
    	if (originalFormatField != null) {
    		String fieldText = originalFormatField.getTextValue();
    		// common bad data needing removal is a trailing period '.'
    		if (fieldText.endsWith(".")) {
    			fieldText = fieldText.substring(0, fieldText.lastIndexOf('.'));
    		}
    		
    		MaterialTypeConcordanceBean bean = concordanceManager.getConcordanceEntry(fieldText);
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
    			instance.addType(type);
    		}
    	}
    }

}
