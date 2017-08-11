/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import static org.ld4l.bib2lod.testing.HfaTestData.ENTRY_NOT_IN_CONCORDANCE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.csv.hfa.SubjectConcordanceManager;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;
/**
 * Tests the HfaToTopicConcordanceBuilder class.
 */
public class HfaToTopicConcordanceBuilderTest extends AbstractTestClass {
    
	private ConcordanceReferenceBuilder genreBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, ConverterException {
    	HfaToTopicConcordanceBuilder builder = new HfaToTopicConcordanceBuilder();
    	builder.setSubjectConcordanceManager(new SubjectConcordanceManager("/test_subject.csv"));
        genreBuilder = builder;
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validTopicGenre() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TOPIC_GENRE);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertNotNull(uris);
		Assert.assertEquals(3, uris.size());
	}
	
	@Test
	public void validTwoLineFilmTopicGenreRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TWO_LINE_TOPIC_GENRE);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertNotNull(uris);
		Assert.assertEquals(4, uris.size());
	}
	
	@Test
	public void validTopicGenreAndKeywordRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TWO_LINE_TOPIC_GENRE_KEYWORD);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertNotNull(uris);
		Assert.assertEquals(3, uris.size());
		
		List<Attribute> attrs = parentEntity.getAttributes(HfaDatatypeProp.KEYWORDS);
		Assert.assertNotNull(attrs);
		Assert.assertEquals(1, attrs.size());
		Attribute attr = attrs.get(0);
		Assert.assertEquals(ENTRY_NOT_IN_CONCORDANCE, attr.getValue());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(null);
		
		genreBuilder.build(params);
	}

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
    private HfaRecord buildHfaRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new HfaRecord(element);
    }
}
