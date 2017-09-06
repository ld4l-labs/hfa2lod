/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.hfa.HfaCollectionType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;

/**
 * Builds an Instance individual from a Record.
 */
public class HfaToInstanceBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity work;
    private Entity instance;
  
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
        
        buildTitle();
        buildCollection();
        
        work.addRelationship(Ld4lObjectProp.HAS_INSTANCE, instance);
        
        return instance;
    }
    
    private void buildTitle() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildCollection() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(HfaCollectionType.COLLECTION);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }

}
