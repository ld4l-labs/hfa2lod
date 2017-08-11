/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.csv.hfa.AbstractGenreConcordanceManager;
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
public class HfaToGenreConcordanceBuilder implements ConcordanceReferenceBuilder {

    private FilmGenreConcordanceManager filmConcordanceManager;
    private TelevisionGenreConcordanceManager televisionConcordanceManager;
    private SubjectConcordanceManager subjectConcordanceManager;
    
    private static final String TELEVISION_SENTINEL = "television program";
    private static final String NEW_LINE = System.getProperty("line.separator");
	private static final Logger LOGGER = LogManager.getLogger();

	public HfaToGenreConcordanceBuilder() throws ConverterException {
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
    	try {
			this.subjectConcordanceManager = new SubjectConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new ConverterException("Could not instantiate SubjectConcordanceManager", e);
		}
	}

    @Override
    public void build(BuildParams params) throws RecordConversionException {
        
        Entity bibEntity = params.getParentEntity();
        if (bibEntity == null) {
        	throw new RecordConversionException("A parent Entity is required to build a title.");
        }
        
        HfaRecord record = (HfaRecord)params.getRecord();
        if (record == null) {
        	throw new RecordConversionException("A HfaRecord is required to build a title.");
        }
        
        AbstractGenreConcordanceManager<?> concordanceManager = filmConcordanceManager;
        
        // If there is text in the "Genre" field indicating television then use that
        // concordance file instead of the default film concordance file.
        List<HfaTextField> hfaTextFields = new ArrayList<>();
        HfaTextField genreHfaField = record.getField(HfaRecord.ColumnAttributeText.GENRE);
        if (genreHfaField != null) {
        	// Check to see if this is special television value (ignoring case).
        	// If so, we will use this value as a sentinel but not use to pull from the concordance file.
        	// NOTE: contains() is used for comparison since there is the possibility the plural,
        	// "television programs" is the value coming from the HFA data.
        	if (genreHfaField.getTextValue().toLowerCase().trim().contains(TELEVISION_SENTINEL)){
        		concordanceManager = televisionConcordanceManager;
        	} else {
        		hfaTextFields.add(genreHfaField);
        	}
        }
        HfaTextField hfaField = record.getField(HfaRecord.ColumnAttributeText.FICTION);
        if (hfaField != null) {
        	hfaTextFields.add(hfaField);
        }
        hfaField = record.getField(HfaRecord.ColumnAttributeText.NON_FICTION);
        if (hfaField != null) {
        	hfaTextFields.add(hfaField);
        }
        hfaField = record.getField(HfaRecord.ColumnAttributeText.SHORT);
        if (hfaField != null) {
        	hfaTextFields.add(hfaField);
        }
        hfaField = record.getField(HfaRecord.ColumnAttributeText.CLASSIFICATION);
        if (hfaField != null) {
        	hfaTextFields.add(hfaField);
        }

        // go through all fields and tokenize each by newline character then loop through each token
        for (HfaTextField field : hfaTextFields) {
        	String fieldText = field.getTextValue();
        	String[] tokenizedFieldText = fieldText.split(NEW_LINE);
        	Set<String> tokenizedTextSet = new HashSet<String>(Arrays.asList(tokenizedFieldText));
        	for (String token : tokenizedTextSet) {
        		ExternalUriBean concordanceBean = concordanceManager.getConcordanceEntry(token);
        		if (concordanceBean != null) {
        			// for concordance matches add external relationship to corresponding URI
        			for (String externalUri : concordanceBean.getExternalUris()) {
        				bibEntity.addExternalRelationship(Ld4lObjectProp.GENRE_FORM, externalUri);
        			}        			
        		} else {
        			// if no match found in concordance file AND the value from the Genre field
        			// is not in the subject concordance, only then then add datatype property triple
    				if (genreHfaField == null || genreHfaField.getTextValue().toLowerCase().contains(token.toLowerCase())) { // (genreHfaField could have multiple tokens)
        				ExternalUriBean subjectConcordanceBean = subjectConcordanceManager.getConcordanceEntry(token);
        				if (subjectConcordanceBean == null) {
        					bibEntity.addAttribute(HfaDatatypeProp.KEYWORDS, new Attribute(token, "en") );
        				}
        			} else {
    					bibEntity.addAttribute(HfaDatatypeProp.KEYWORDS, new Attribute(token, "en") );
        			}
        		}
        	}
        	
        }
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setFilmConcordanceManager(FilmGenreConcordanceManager concordanceManager) {
		this.filmConcordanceManager = concordanceManager;
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setTelevisionConcordanceManager(TelevisionGenreConcordanceManager concordanceManager) {
		this.televisionConcordanceManager = concordanceManager;
    }
}
