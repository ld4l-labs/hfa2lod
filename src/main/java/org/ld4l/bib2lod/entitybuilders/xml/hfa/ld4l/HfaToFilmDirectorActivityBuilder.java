/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.hfa.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds a FilmDirectorActivity Entity.
 */
public class HfaToFilmDirectorActivityBuilder extends HfaToLd4lEntityBuilder {

    private HfaRecord record;
    private Entity filmDirectoryActivityEntity;
    private HfaTextField hfaDirector;

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	this.record = (HfaRecord) params.getRecord();
        if (this.record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a title.");
        }

        Entity parentEntity = params.getParentEntity();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build a title.");
        }
        
        this.hfaDirector = record.getField(HfaRecord.ColumnAttributeText.DIRECTOR);
        if (this.hfaDirector == null) {
        	LOGGER.debug("No field for [{}]", HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText());
        	return null;
        }

        this.filmDirectoryActivityEntity = new Entity(HfaActivityType.FILM_DIRECTOR_ACTIVITY);
        this.filmDirectoryActivityEntity.addAttribute(Ld4lDatatypeProp.LABEL,
        		new Attribute(HfaActivityType.FILM_DIRECTOR_ACTIVITY.label()));
    	parentEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, this.filmDirectoryActivityEntity);
        
        addAgents();
       
        return this.filmDirectoryActivityEntity;
    }
    
    private void addAgents() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setField(hfaDirector)
                .setParentEntity(filmDirectoryActivityEntity);        
        builder.build(params);
    }

    
}
