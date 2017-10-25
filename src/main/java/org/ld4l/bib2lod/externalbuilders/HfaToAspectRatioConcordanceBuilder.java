/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.csv.hfa.AspectRatioConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.AspectRatioConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedNamedIndividual;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Adds external URI's pulled from a concordance file to the parent Entity.
 * If no match is found there will be no modification to the parent Entity.
 */
public class HfaToAspectRatioConcordanceBuilder implements ConcordanceReferenceBuilder {

    private AspectRatioConcordanceManager aspectRatioConcordanceManager;
    
	private static final Logger LOGGER = LogManager.getLogger();

	public HfaToAspectRatioConcordanceBuilder() throws ConverterException {
    	try {
			this.aspectRatioConcordanceManager = new AspectRatioConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate AspectRatioConcordanceManager", e);
		}
	}

    @Override
    public void build(BuildParams params) throws RecordConversionException {
        
        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
        	throw new RecordConversionException("A parent Entity is required to build a title.");
        }
        
        HfaRecord record = (HfaRecord)params.getRecord();
        if (record == null) {
        	throw new RecordConversionException("A HfaRecord is required to build a title.");
        }
        
    	HfaTextField hfaField = record.getField(ColumnAttributeText.ASPECT_RATIO);
    	if (hfaField != null) {
    		AspectRatioConcordanceBean aspectRationBean = aspectRatioConcordanceManager.getConcordanceEntry(hfaField.getTextValue().trim());
    		if (aspectRationBean != null) {
    			String ni = aspectRationBean.getNamedIndividual();
    			String[] parts = parseNamedIndividualText(ni);
    			HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
    			HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(ns, parts[1]);
    			parentEntity.addExternalRelationship(HfaObjectProp.HAS_CHARACTERISTIC, namedIndividual);
    		} else {
    			LOGGER.debug("No concordance match for [{}]", hfaField.getTextValue());
    		}
    	}
    }
    
    private String[] parseNamedIndividualText(String s) {
    	String[] tokens = s.split(":");
    	return tokens;
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setAspectRatioConcordanceManager(AspectRatioConcordanceManager concordanceManager) {
		this.aspectRatioConcordanceManager = concordanceManager;
    }
}
