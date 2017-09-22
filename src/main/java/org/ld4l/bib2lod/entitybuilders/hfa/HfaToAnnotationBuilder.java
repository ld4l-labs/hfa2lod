/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTextualBodyType;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;
import org.ld4l.bib2lod.util.HfaConstants;

/**
 * Builds an Annotation Entity.
 */
public class HfaToAnnotationBuilder extends HfaToLd4lEntityBuilder {
	
	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
	 */
	@Override
	public Entity build(BuildParams params) throws EntityBuilderException {
        
        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an Annotation.");
        }

        HfaTextField field = (HfaTextField) params.getField();
        if (field == null) {
        	throw new EntityBuilderException("A HfaTextField is required to build an Annotation.");
        }
        
        NamedIndividual namedIndividual = params.getNamedIndividual();
        if (namedIndividual == null) {
        	throw new EntityBuilderException("A NamedIndividual is required to build an Annotation.");
        }
        
        Entity annotation = new Entity(Ld4lAnnotationType.ANNOTATION);
        annotation.addExternalRelationship(Ld4lObjectProp.MOTIVATED_BY, namedIndividual);
        annotation.addExternalRelationship(Ld4lObjectProp.HAS_CREATOR, HfaConstants.HARVARD_LIBRARY_CREATOR);
        Entity textualBody = new Entity(Ld4lTextualBodyType.TEXTUAL_BODY);
		textualBody.addAttribute(Ld4lDatatypeProp.VALUE, field.getTextValue());
		annotation.addRelationship(Ld4lObjectProp.HAS_BODY, textualBody);
		parentEntity.addRelationship(Ld4lObjectProp.HAS_ANNOTATION, annotation);
        
        return annotation;
	}

}
