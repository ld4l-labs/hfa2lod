/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.externalbuilders.ConcordanceReferenceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToGenreConcordanceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToTopicConcordanceBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HarvardType;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
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
    
    static final int ITEM_NUMBER_LENGTH = 10;
    private static final String ITEM_NUMBER_STRING_FORMAT = "%" + ITEM_NUMBER_LENGTH + "s"; // "%10s"
 
    private static final ColumnAttributeText[] activityColumns =
    	   {ColumnAttributeText.DP_CINEMATOGRAPHER,
			ColumnAttributeText.EDITOR,
			ColumnAttributeText.PRODUCER,
			ColumnAttributeText.SCRIPT};

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

    	this.record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a MovingImage.");
        }
        
        this.work = new Entity(Ld4lWorkType.MOVING_IMAGE);
        
        buildTitle();
        buildInstances();
        buildDirectorActivities();
        addIdentifiers();
        buildActivies();
        try {
			addGenres();
			addTopics();
		} catch (ConverterException e) {
            throw new EntityBuilderException(
            		e.getMessage(), e);
		}

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.INSTANCE);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void buildDirectorActivities() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(HfaActivityType.DIRECTOR_ACTIVITY);

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
    
    private void addGenres() throws ConverterException {
        
    	ConcordanceReferenceBuilder builder = new HfaToGenreConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }
    
    private void addTopics() throws ConverterException {
        
    	ConcordanceReferenceBuilder builder = new HfaToTopicConcordanceBuilder();

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(work);        
        builder.build(params);
    }

    private void buildActivies() throws EntityBuilderException {

        EntityBuilder builder = getBuilder(Ld4lActivityType.ACTIVITY);

        for (ColumnAttributeText column : activityColumns) {
        	HfaTextField field = record.getField(column);
            if (field == null) {
            	LOGGER.debug("No field for [{}]", column.getColumnAttributeText());
            	continue;
            }
            
            Type activityType = getTypeFromColumn(column);
            
            BuildParams params = new BuildParams()
                    .setRecord(record)
                    .setParent(work)
                    .setType(activityType)
                    .setField(field);
            builder.build(params);
        }    	
    }
    
    /*
     * Get entity type for expected field name.
     * protected for allow for unit tests.
     */
    protected Type getTypeFromColumn(ColumnAttributeText column) throws EntityBuilderException {
    	
        Type activityType = null;
        switch (column) {
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
            default:
            	throw new EntityBuilderException("Column name must match an expected value.");
        }
        return activityType;
    }
}
