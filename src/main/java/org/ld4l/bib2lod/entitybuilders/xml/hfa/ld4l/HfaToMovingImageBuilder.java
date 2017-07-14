/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.hfa.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
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
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

    	this.record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a Cartography.");
        }
        
        this.work = new Entity(Ld4lWorkType.MOVING_IMAGE);
        
        buildTitle();
        buildInstances();
        addIdentifiers();

        return this.work;
    }
    
    private void buildTitle() throws EntityBuilderException { 
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);
        builder.build(params);
    }
    
    private void buildInstances() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lInstanceType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(work);        
        builder.build(params);
    }
    
    private void addIdentifiers() {
    	
		Entity identifier = new Entity(Ld4lIdentifierType.LOCAL);
		String hfaNumber = record.getField(ColumnAttributeText.HFA_NUMBER).getTextValue(); // should have been validated as non-null
		identifier.addAttribute(Ld4lDatatypeProp.VALUE, hfaNumber);
		work.addRelationship(Ld4lObjectProp.IDENTIFIED_BY, identifier);
    }
        
}
