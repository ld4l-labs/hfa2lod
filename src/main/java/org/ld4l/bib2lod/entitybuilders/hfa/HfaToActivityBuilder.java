/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.regex.Pattern;

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
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds an Activity Entity.
 */
public class HfaToActivityBuilder extends HfaToLd4lEntityBuilder {

    private HfaRecord record;
    private Entity activityEntity;
    private String agentFieldText;
    
    private static Pattern commaRegex;

    private static final Logger LOGGER = LogManager.getLogger();
    
    static {
    	commaRegex = Pattern.compile(",|/");
    }

    /**
     * Rather than passing in a HfaTextField via the BuildParams, pass in the text of this
     * field since the calling class may need to first parse (comma- or slash-separated)
     * multiple values out of the text of the HfaTextField.
     * 
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
     */
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	this.record = (HfaRecord) params.getRecord();
        if (this.record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build an activity.");
        }

        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an activity.");
        }
        
        HfaActivityType activityType = (HfaActivityType) params.getType();
        if (activityType == null) {
            throw new EntityBuilderException(
                    "A Type is required to build an Activity.");
        }
        
        this.agentFieldText = params.getValue();
        if (agentFieldText == null) {
            throw new EntityBuilderException(
                    "A field text value is required to build an Activity.");
        }

        this.activityEntity = new Entity(activityType);
        this.activityEntity.addAttribute(Ld4lDatatypeProp.LABEL,
        		new Attribute(activityType.label()));
    	parentEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, this.activityEntity);
    	
    	// date and location are only specific to a certain Activity
    	if (HfaActivityType.PRODUCTION_COMPANY_ACTIVITY.equals(activityType)) {
    		HfaTextField dateField = this.record.getField(ColumnAttributeText.YEAR_OF_RELEASE);
    		if (dateField != null) {
    			this.activityEntity.addAttribute(Ld4lDatatypeProp.DATE, dateField.getTextValue());
    		}
    		
    		HfaTextField countryField = this.record.getField(ColumnAttributeText.COUNTRY);
    		// parse possible multiple country names
    		if (countryField != null) {
    			// FIXME: need to look up country URI, not the country String value.
    			String[] countries = commaRegex.split(countryField.getTextValue());
    			for (String country : countries) {
    				// TODO: lookup location URI in either concordance file or external service
    				this.activityEntity.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, "http://some.country.authority/" + country.trim());
    			}
    		}
    	}
        
        addAgents();
       
        return this.activityEntity;
    }
    
    private void addAgents() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.AGENT);
        
        // tokenize possible comma-separated names
        String[] names = commaRegex.split(agentFieldText);
        for (String n : names) {
        	String name = n.trim();

        	BuildParams params = new BuildParams()
        			.setRecord(record)
        			.setParent(activityEntity)
        			.setValue(name);        
        	builder.build(params);
        }
    }
    
}
