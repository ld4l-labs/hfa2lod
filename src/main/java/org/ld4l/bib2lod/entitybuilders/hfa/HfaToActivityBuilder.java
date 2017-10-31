/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.CachingService.CachingServiceException;
import org.ld4l.bib2lod.caching.CachingService.MapType;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
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
import org.ld4l.bib2lod.uris.UriService;
import org.ld4l.bib2lod.util.Hfa2LodDateUtils;
import org.ld4l.bib2lod.util.HfaConstants;

/**
 * Builds an Activity Entity.
 */
public class HfaToActivityBuilder extends HfaToLd4lEntityBuilder {

    private HfaRecord record;
    private Entity parentEntity;
    private Entity activityEntity;
    private String agentText;
    private CachingService cachingService;
    
    private static Pattern commaRegex;

    private static final Logger LOGGER = LogManager.getLogger();
    
    static {
    	commaRegex = Pattern.compile(",|/|\\|"); // includes pipe '|' char as well as ',' and '/'
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
        
    	cachingService = CachingService.instance();
        
    	this.record = (HfaRecord) params.getRecord();
        if (this.record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build an activity.");
        }

        parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build an activity.");
        }
        
        HfaActivityType activityType = (HfaActivityType) params.getType();
        if (activityType == null) {
            throw new EntityBuilderException(
                    "A Type is required to build an Activity.");
        }

        this.agentText = params.getValue();

        this.activityEntity = new Entity(activityType);
        this.activityEntity.addAttribute(Ld4lDatatypeProp.LABEL,
        		new Attribute(activityType.label()));
    	parentEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, this.activityEntity);
    	
    	// Date and location are only specific to a Provider Activity while, at the same time,
    	// there are no Agents to add.
    	if (HfaActivityType.PROVIDER_ACTIVITY.equals(activityType)) {
    		// TODO: ignore "[unknown]" and "[various]" and similar
    		HfaTextField dateField = this.record.getField(ColumnAttributeText.YEAR_OF_RELEASE);
    		String dateText = null;
    		if (dateField != null && 
    				(!dateField.getTextValue().contains("[unknown]") && !dateField.getTextValue().contains("[various]") ) ) {
    			dateText = dateField.getTextValue();
    		}
    		HfaTextField countryField = this.record.getField(ColumnAttributeText.COUNTRY);
    		if (dateText == null && countryField == null) {
    			return null;
    		}
    		
    		// check for and remove sentinel values
    		if (dateText != null) {
    			// some dates have extra brackets -- need to strip them
    			dateText = dateText.replace("[", "").replace("]", "");
    			if (Hfa2LodDateUtils.isCircaDate(dateText)) {
    				dateText = Hfa2LodDateUtils.convertCircaDateToISOStandard(dateText);
    			} else {
    				dateText = dateText.trim();
    			}
    			this.activityEntity.addAttribute(Ld4lDatatypeProp.DATE, dateText, BibDatatype.EDTF);
    		}
    		// parse possible multiple country names
    		if (countryField != null) {
    	    	
    	    	// TODO: will not need this eventually once concordances are complete
    	    	String tempUriBase = "http://localhost/bogus-base/";

    	    	String[] locations = commaRegex.split(countryField.getTextValue());
    			for (String location : locations) {
    				// FIXME: lookup exteral URI for location in concordance file
					// Agreed to remove "?" from location		
    				// String locationUri = concordance.lookup(location.replace("[", "").replace("]", "").replace("?", ""));
    				location = location.trim().replace(' ', '_').replace("\n", "_").replace("?", "")
    						.replace("[", "").replace("]", ""); // TODO: remove - temporary until there is a URI
    				this.activityEntity.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, tempUriBase + location.trim());
    			}
    		}
    	} else if (HfaActivityType.LENDER_ACTIVITY.equals(activityType)) {
    		// create agent here with URI
    		this.activityEntity.addExternalRelationship(Ld4lObjectProp.HAS_AGENT, HfaConstants.HARVARD_LIBRARY_CREATOR);
    	} else {
            
            // An agent text value is needed for all Activity types except PROVIDER_ACTIVITY so
    		// that Agent(s) can be added.
            if (agentText == null) {
                throw new EntityBuilderException(
                        "A field text value is required to build an Activity.");
            }
            
            // Special case where activityType is a DonorActivity:
            // The Agent Entity is created locally rather than calling its Builder class,
            // and its URI should be cached and reused if another donor name is identical.
            if (HfaActivityType.DONOR_ACTIVITY.equals(activityType)) {
            	handleDonorAgent(agentText.trim());
            } else {
            	addAgent();
            }
    	}
       
        return this.activityEntity;
    }
    
    private void addAgent() throws EntityBuilderException {
        
    	if (agentText == null) {
    		return;
    	}
    	
        EntityBuilder builder = getBuilder(Ld4lAgentType.AGENT);
    	BuildParams params = new BuildParams()
    			.setRecord(record)
    			.setParent(activityEntity)
    			.setValue(agentText.trim());
    	builder.build(params);
    }
    
    private void handleDonorAgent(String agentName) throws EntityBuilderException {
    	
        Map<String, String> mapToUri = cachingService.getMap(MapType.NAMES_TO_URI);
        String cachedAgentUri = mapToUri.get(agentName);
        Entity agentEntity = null;
        if (cachedAgentUri == null) {
        	agentEntity = new Entity(Ld4lAgentType.AGENT);
        	agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, agentName);
        	String agentUri = UriService.getUri(agentEntity);
        	
        	agentEntity.buildResource(agentUri);
        	try {
				cachingService.putUri(MapType.NAMES_TO_URI, agentName, agentUri);
			} catch (CachingServiceException e) {
				throw new EntityBuilderException(e);
			}
        	activityEntity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
        } else {

        	agentEntity = new Entity(Ld4lAgentType.AGENT);
        	agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, agentName);
        	
        	agentEntity.buildResource(cachedAgentUri);
        	activityEntity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
        }
    }
    
}
