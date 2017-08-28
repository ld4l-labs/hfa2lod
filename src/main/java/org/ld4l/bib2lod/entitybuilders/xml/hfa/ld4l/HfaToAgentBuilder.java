/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.hfa.ld4l;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.csv.hfa.NamesConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.NamesConcordanceManager;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds a FilmDirectorActivity Entity.
 */
public class HfaToAgentBuilder extends HfaToLd4lEntityBuilder {
	
	private NamesConcordanceManager concordanceManager;

    private static final Logger LOGGER = LogManager.getLogger();

    public HfaToAgentBuilder() throws ConverterException {
    	try {
			this.concordanceManager = new NamesConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate FilmGenreConcordanceManager", e);
		}
    }
    
    /**
     * Expecting the Agent to be passed in as a parameter; not being pulled as part of HfaRecord.
     * 
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build(org.ld4l.bib2lod.entitybuilders.BuildParams)
     */
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	HfaRecord record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a title.");
        }

        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build a title.");
        }
        
        HfaTextField hfaDirector = (HfaTextField)params.getField();
        if (hfaDirector == null) {
            throw new EntityBuilderException(
                    "A field Entity is required to build a title.");
        }
        
        Entity agentEntity = new Entity(Ld4lAgentType.AGENT);
        parentEntity.addRelationship(Ld4lObjectProp.HAS_AGENT, agentEntity);
        agentEntity.addAttribute(Ld4lDatatypeProp.LABEL, new Attribute(hfaDirector.getTextValue()));
        
        NamesConcordanceBean nameConcordanceBean = concordanceManager.getConcordanceEntry(hfaDirector.getTextValue());
        if (nameConcordanceBean != null) {
        	String isniUri = nameConcordanceBean.getIsni();
        	if (isniUri != null) {
        		agentEntity.addExternalRelationship(HfaObjectProp.HAS_PUBLIC_IDENTITY, isniUri);
        	}
        	
        	String webPageUrl = nameConcordanceBean.getWebPage();
        	if (webPageUrl != null) {
        		agentEntity.addExternalRelationship(HfaObjectProp.HAS_WEB_PAGE, webPageUrl);
        	}
        	
        }
        
        return agentEntity;
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setNamesConcordanceManager(NamesConcordanceManager concordanceManager) {
		this.concordanceManager = concordanceManager;
    }
    
}
