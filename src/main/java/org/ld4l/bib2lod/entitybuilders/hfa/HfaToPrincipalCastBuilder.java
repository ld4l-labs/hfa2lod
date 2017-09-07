/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;

/**
 * Builds a Principal Cast Entity.
 */
public class HfaToPrincipalCastBuilder extends HfaToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build a cast entry.");
        }
        
        String castName = params.getValue();
        if (castName == null) {
            throw new EntityBuilderException(
                    "A castName value is required to build a cast entry.");
        }

        // FIXME: this type might not be correct
        Entity castEntity = new Entity(Ld4lAgentType.PERSON);
        castEntity.addAttribute(Ld4lDatatypeProp.LABEL, castName.trim());
        // FIXME: this object property might not be correct
    	parentEntity.addRelationship(Ld4lObjectProp.HAS_ANNOTATION, castEntity);
        
        return castEntity;
    }
    
}
