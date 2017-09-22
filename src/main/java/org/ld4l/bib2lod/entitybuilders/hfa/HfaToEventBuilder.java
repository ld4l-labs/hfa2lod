/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.hfa.HfaLoan;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;

/**
 * Builds an Item individual from a Record.
 */
public class HfaToEventBuilder extends HfaToLd4lEntityBuilder {
    
    private HfaRecord record;
    private Entity parent;
    private Type type;
//    private List<HfaLoan> hfaLoans;
    private Entity event;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (HfaRecord) params.getRecord();
		if (record == null) {
			throw new EntityBuilderException(
					"A HfaRecord is required to build an Event.");
		}

		this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an Event.");
        }
        
        this.type = params.getType();
        if (type == null) {
            throw new EntityBuilderException(
                    "A Type is required to build an Event.");
        }
        
        // Building this Entity depends solely on the existence of HfaLoan values.
//        hfaLoans = record.getHfaLoanFields();
//        if (hfaLoans.isEmpty()) {
//        	return null;
//        }

        this.event = new Entity(type);
        
        buildLoanEvent();
        
        parent.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, event);
        
        return event;
    }
    
    private void buildLoanEvent() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.TITLE);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(event)
                .setType(type);
        builder.build(params);
    }

}
