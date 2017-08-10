/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.hfa.ld4l;

import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.externalbuilders.ConcordanceReferenceBuilder;
import org.ld4l.bib2lod.externalbuilders.HfaToGenreConcordanceBuilder;
import org.ld4l.bib2lod.ontology.hfa.HarvardType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;

/**
 * Builds a MovingImage individual from a Record.
 */
public class HfaToMovingImageBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity work;
    
    static final int ITEM_NUMBER_LENGTH = 10;
    private static final String ITEM_NUMBER_STRING_FORMAT = "%" + ITEM_NUMBER_LENGTH + "s"; // "%10s"
  
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
        addIdentifiers();
        try {
			addGenres();
		} catch (ConverterException e) {
            throw new EntityBuilderException(
            		e.getMessage(), e);
		}

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(work);        
        builder.build(params);
    }
    
    /*
     * Pad the item number with zero to eight places unless this value begins with a 'V'.
     */
    private void addIdentifiers() {
    	
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
                .setParentEntity(work);        
        builder.build(params);
    }
}
