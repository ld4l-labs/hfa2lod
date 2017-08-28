/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.csv.hfa.ExternalUriBean;
import org.ld4l.bib2lod.csv.hfa.FilmGenreConcordanceManager;
import org.ld4l.bib2lod.csv.hfa.SubjectConcordanceManager;
import org.ld4l.bib2lod.csv.hfa.TelevisionGenreConcordanceManager;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Adds external genre URI's pulled from a concordance file to an Entity.
 * Typically the URI's are pulled from the film concordance file unless a specific sentinel value
 * is found, in which case the URI's will be pulled from the television concordance file.
 * If no match is found then the values from the HFA data will be used to create keyword data properties.
 */
public class HfaToTopicConcordanceBuilder implements ConcordanceReferenceBuilder {

    private SubjectConcordanceManager subjectConcordanceManager;
    private FilmGenreConcordanceManager filmConcordanceManager;
    private TelevisionGenreConcordanceManager televisionConcordanceManager;
    
    private static final String NEW_LINE = System.getProperty("line.separator");
	private static final Logger LOGGER = LogManager.getLogger();

	public HfaToTopicConcordanceBuilder() throws ConverterException {
    	try {
			this.subjectConcordanceManager = new SubjectConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate SubjectConcordanceManager", e);
		}
    	try {
			this.filmConcordanceManager = new FilmGenreConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate FilmGenreConcordanceManager", e);
		}
    	try {
			this.televisionConcordanceManager = new TelevisionGenreConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate TelevisionGenreConcordanceManager", e);
		}
	}

    @Override
    public void build(BuildParams params) throws RecordConversionException {
        
        Entity bibEntity = params.getParent();
        if (bibEntity == null) {
        	throw new RecordConversionException("A parent Entity is required to build a title.");
        }
        
        HfaRecord record = (HfaRecord)params.getRecord();
        if (record == null) {
        	throw new RecordConversionException("A HfaRecord is required to build a title.");
        }
        
        HfaTextField hfaField = record.getField(HfaRecord.ColumnAttributeText.GENRE);
        if (hfaField != null) {
        	String fieldText = hfaField.getTextValue();
        	// tokenize field by newline character then loop through each token
        	String[] tokenizedFieldText = fieldText.split(NEW_LINE);
        	Set<String> tokenizedTextSet = new HashSet<String>(Arrays.asList(tokenizedFieldText));
        	for (String token : tokenizedTextSet) {
        		ExternalUriBean concordanceBean = subjectConcordanceManager.getConcordanceEntry(token);
        		if (concordanceBean != null) {
        			// for concordance matches add external relationship to corresponding URI
        			for (String externalUri : concordanceBean.getExternalUris()) {
        				bibEntity.addExternalRelationship(Ld4lObjectProp.HAS_SUBJECT, externalUri);
        			}        			
        		} else {
        			// if no match found in concordance file AND the value from the Genre field
        			// is not in the film or television genre concordance, only then then add datatype property triple
    				ExternalUriBean filmConcordanceBean = filmConcordanceManager.getConcordanceEntry(token);
    				ExternalUriBean televisionConcordanceBean = televisionConcordanceManager.getConcordanceEntry(token);
    				if (filmConcordanceBean == null && televisionConcordanceBean == null) {
    					bibEntity.addAttribute(HfaDatatypeProp.KEYWORDS, new Attribute(token, "en") );
    				}
        		}
        	}
        }
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setSubjectConcordanceManager(SubjectConcordanceManager concordanceManager) {
		this.subjectConcordanceManager = concordanceManager;
    }
}
