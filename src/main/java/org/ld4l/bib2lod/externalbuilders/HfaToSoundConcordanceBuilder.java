/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.csv.hfa.SoundConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.SoundConcordanceManager;
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
public class HfaToSoundConcordanceBuilder implements ConcordanceReferenceBuilder {

    private SoundConcordanceManager soundConcordanceManager;
    
    private static List<ColumnAttributeText> attrs;
    
	private static final Logger LOGGER = LogManager.getLogger();
	
	static {
		attrs = new ArrayList<>();
		attrs.add(ColumnAttributeText.SOUND_ASPECTS);
		attrs.add(ColumnAttributeText.SOUNDTRACK_TYPE);
	}

	public HfaToSoundConcordanceBuilder() throws ConverterException {
    	try {
			this.soundConcordanceManager = new SoundConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate SoundConcordanceManager", e);
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
        
        for (ColumnAttributeText fieldName : attrs){
        	HfaTextField hfaField = record.getField(fieldName);
        	if (hfaField != null) {
        		SoundConcordanceBean characteristicBean = soundConcordanceManager.getConcordanceEntry(hfaField.getTextValue());
        		if (characteristicBean != null) {
        			String ni = characteristicBean.getNamedIndividual();
        			String[] parts = parseNamedIndividualText(ni);
        			HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
        			HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(ns, parts[1]);
        			parentEntity.addExternalRelationship(HfaObjectProp.HAS_CHARACTERISTIC, namedIndividual);
        		} else {
        			LOGGER.warn("No concordance match for [{}]", hfaField.getTextValue());
        		}
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
    protected void setSoundConcordanceManager(SoundConcordanceManager concordanceManager) {
		this.soundConcordanceManager = concordanceManager;
    }
}
