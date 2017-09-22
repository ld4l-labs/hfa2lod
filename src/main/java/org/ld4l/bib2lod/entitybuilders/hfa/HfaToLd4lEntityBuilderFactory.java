/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.HashMap;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaCollectionType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;

public class HfaToLd4lEntityBuilderFactory extends BaseEntityBuilderFactory {

    private static HashMap<Type, Class<? extends EntityBuilder>> typeToBuilder = 
            new HashMap<>();
    static {
        typeToBuilder.put(Ld4lInstanceType.INSTANCE, HfaToInstanceBuilder.class);
        typeToBuilder.put(Ld4lWorkType.MOVING_IMAGE, HfaToMovingImageBuilder.class);
        typeToBuilder.put(Ld4lTitleType.TITLE, HfaToTitleBuilder.class);
        typeToBuilder.put(Ld4lActivityType.ACTIVITY, HfaToActivityBuilder.class);
        typeToBuilder.put(Ld4lAgentType.AGENT, HfaToAgentBuilder.class);
        typeToBuilder.put(HfaCollectionType.COLLECTION, HfaToCollectionBuilder.class);
        typeToBuilder.put(Ld4lAnnotationType.ANNOTATION, HfaToAnnotationBuilder.class);
        typeToBuilder.put(Ld4lItemType.ITEM, HfaToItemBuilder.class);
    }
    
    @Override
    public HashMap<Type, Class<? extends EntityBuilder>> 
            getTypeToBuilderClassMap() {
        return typeToBuilder;
    }
      
}
