/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.externalbuilders.ConcordanceReferenceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToAspectRatioConcordanceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToCharacteristicsConcordanceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToGenreConcordanceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToTopicConcordanceBuilder;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HarvardType;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.hfa.HfaNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds a MovingImage individual from a Record.
 */
public class HfaToMovingImageBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity work;
    
    private static Pattern commaRegex;
    
    static final int ITEM_NUMBER_LENGTH = 10; // not private since used in tests
    private static final String ITEM_NUMBER_STRING_FORMAT = "%" + ITEM_NUMBER_LENGTH + "s"; // "%10s"
 
    private static final ColumnAttributeText[] activityColumns = {
    		ColumnAttributeText.DIRECTOR,
    		ColumnAttributeText.DP_CINEMATOGRAPHER,
			ColumnAttributeText.EDITOR,
			ColumnAttributeText.PRODUCER,
			ColumnAttributeText.SCRIPT,
			ColumnAttributeText.PRODUCTION_COMPANY,
			ColumnAttributeText.MUSIC
	};
    
    private static final ColumnAttributeText[] annotationColumns = {
			ColumnAttributeText.PRINCIPAL_CAST,
			ColumnAttributeText.SYNOPSIS
	};

    private static final Logger LOGGER = LogManager.getLogger();
    
    static {
    	commaRegex = Pattern.compile(",|/");
    }

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

    	this.record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a MovingImage.");
        }
        
        this.work = new Entity(Ld4lWorkType.MOVING_IMAGE);
        
        buildTitles();
        buildInstances();
        addIdentifiers();
        buildActivies();
        buildAnnotations();
		addGenres();
		addTopics();
		addColor();
		addAspectRatio();

        // add HFA Time
        HfaTextField hfaTime = record.getField(ColumnAttributeText.HFA_TIME);
        if (hfaTime != null) {
        	String iso8601Duration = "P" + hfaTime.getTextValue() + "M";
        	work.addAttribute(HfaDatatypeProp.DURATION_SCHEMA, iso8601Duration);
        	work.addAttribute(HfaDatatypeProp.DURATION_BF, new Attribute(hfaTime.getTextValue(), "en"));
        }
        
        return this.work;
    }
    
    private void buildTitles() throws EntityBuilderException {
    	
    	HfaTextField hfaTitleField = record.getField(HfaRecord.ColumnAttributeText.TITLE);
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setField(hfaTitleField)
                .setParent(work);
        Entity titleEntity = builder.build(params);
        // add primary title as label for this entity
        work.addAttribute(Ld4lDatatypeProp.LABEL, titleEntity.getAttribute(Ld4lDatatypeProp.LABEL));
        
        // build any other title records
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
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.INSTANCE);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    /*
     * Pad the item number with zero to eight places unless this value begins with a 'V'.
     */
    private void addIdentifiers() throws EntityBuilderException {
    	
		Entity identifier = new Entity(HarvardType.HFA_NUMBER);
		String itemNumber = record.getField(ColumnAttributeText.ITEM_NUMBER).getTextValue(); // should have been validated as non-null
		// pad this out with '0' to 10 characters unless it begins with a 'V';
		if (itemNumber.length() < ITEM_NUMBER_LENGTH && !itemNumber.startsWith("V")) {
			itemNumber = String.format(ITEM_NUMBER_STRING_FORMAT, itemNumber).replace(' ', '0');
		}
		identifier.addAttribute(Ld4lDatatypeProp.VALUE, itemNumber);
		work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, identifier);
    }
    
    private void addGenres() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToGenreConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void addTopics() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToTopicConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void addColor() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToCharacteristicsConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void addAspectRatio() throws EntityBuilderException {
        
    	ConcordanceReferenceBuilder builder = new HfaToAspectRatioConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }


    private void buildAnnotations() throws EntityBuilderException {
    	
    	EntityBuilder builder = getBuilder(Ld4lAnnotationType.ANNOTATION);
    	
        // iterate through all possible values need to create annotations
    	for (ColumnAttributeText column : annotationColumns) {
        	HfaTextField field = record.getField(column);
            if (field == null) {
            	continue;
            }

            NamedIndividual namedIndividual = getNamedIndividualFromColumn(column);
        	
        	BuildParams params = new BuildParams()
        			.setParent(work)
        			.setField(field)
        			.setNamedIndividual(namedIndividual);
        	builder.build(params);
    	}
    }

    private void buildActivies() throws EntityBuilderException {

        EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);

        // iterate through all possible values need to create activities
        for (ColumnAttributeText column : activityColumns) {
        	HfaTextField field = record.getField(column);
            if (field == null) {
            	continue;
            }
            
            Type activityType = getTypeFromColumn(column);
            
            // create an Activity for each comma- or slash-separated value
        	String[] fieldTextValues = commaRegex.split(field.getTextValue());
        	for (String value : fieldTextValues) {
                BuildParams params = new BuildParams()
                        .setRecord(record)
                        .setParent(work)
                        .setType(activityType)
                        .setValue(value); // this value will become an Agent
                builder.build(params);
        	}
        }
        
        // Add ProviderActivity separately since it depends on 2 data, not one,
        // only if both fields are non-null.
		HfaTextField yearOfReleaseField = record.getField(ColumnAttributeText.YEAR_OF_RELEASE);
		HfaTextField countryField = record.getField(ColumnAttributeText.COUNTRY);
		if ( !(yearOfReleaseField == null && countryField == null) ) {
			Type activityType = HfaActivityType.PROVIDER_ACTIVITY;
        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setParent(work)
        			.setType(activityType);
        	builder.build(params);
		} else {
			LOGGER.debug("Both Country and Year of Release fields are null -- not building Activity.");
		}
    }
    
    /*
     * Get entity type for expected field name.
     * protected for allow for unit tests.
     */
    protected Type getTypeFromColumn(ColumnAttributeText column) throws EntityBuilderException {
    	
        Type activityType = null;
        switch (column) {
        	case DIRECTOR:
        		activityType = HfaActivityType.DIRECTOR_ACTIVITY;
        		break;
            case DP_CINEMATOGRAPHER:
            	activityType = HfaActivityType.CINEMATOGRAPHER_ACTIVITY;
            	break;
            case EDITOR:
            	activityType = HfaActivityType.EDITOR_ACTIVITY;
            	break;
            case PRODUCER:
            	activityType = HfaActivityType.PRODUCER_ACTIVITY;
            	break;
            case SCRIPT:
            	activityType = HfaActivityType.SCREENWRITER_ACTIVITY;
            	break;
            case PRODUCTION_COMPANY:
            	activityType = HfaActivityType.PRODUCTION_COMPANY_ACTIVITY;
            	break;
            case MUSIC:
            	activityType = HfaActivityType.MUSICIAN_ACTIVITY;
            	break;
            default:
            	throw new EntityBuilderException("Column name must match an expected value.");
        }
        return activityType;
    }
    
    /*
     * Get named individual for expected field name.
     * protected for allow for unit tests.
     */
    protected NamedIndividual getNamedIndividualFromColumn(ColumnAttributeText column) throws EntityBuilderException {
    	
    	NamedIndividual namedIndividual = null;
        switch (column) {
        	case PRINCIPAL_CAST:
        		namedIndividual = HfaNamedIndividual.LISTING_CREDITS;
        		break;
            case SYNOPSIS:
            	namedIndividual = Ld4lNamedIndividual.SUMMARIZING;
            	break;
            default:
            	throw new EntityBuilderException("Column name must match an expected value.");
        }
        return namedIndividual;
    }
}
