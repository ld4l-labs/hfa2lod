/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import static org.ld4l.bib2lod.testing.HfaTestData.ENTRY_NOT_IN_CONCORDANCE;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.csv.hfa.SubjectConcordanceManager;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
/**
 * Tests the HfaToTopicConcordanceBuilder class.
 */
public class HfaToTopicConcordanceBuilderTest extends AbstractHfaTest {
    
	private ConcordanceReferenceBuilder genreBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, EntityBuilderException {
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
		params.setParent(parentEntity);
		
		genreBuilder.build(params);
		
		List<Entity> subjectEntities = parentEntity.getChildren(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertEquals(3, subjectEntities.size());
		
		List<String> concordanceUris = new ArrayList<String>(3);
		concordanceUris.add("http://vocab.getty.edu/aat/300400818");
		concordanceUris.add("http://id.worldcat.org/fast/958730");
		concordanceUris.add("http://id.loc.gov/authorities/subjects/sh85061453");
		
		Entity subjectEntity = subjectEntities.get(0);
		Assert.assertTrue(concordanceUris.contains(subjectEntity.getResource().getURI()));
		Attribute attr = subjectEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(attr);
		Assert.assertEquals(HfaTestData.HOLIDAY_TOPIC, attr.getValue());

		subjectEntity = subjectEntities.get(1);
		Assert.assertTrue(concordanceUris.contains(subjectEntities.get(1).getResource().getURI()));
		attr = subjectEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(attr);
		Assert.assertEquals(HfaTestData.HOLIDAY_TOPIC, attr.getValue());

		subjectEntity = subjectEntities.get(2);
		Assert.assertTrue(concordanceUris.contains(subjectEntities.get(2).getResource().getURI()));
		attr = subjectEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(attr);
		Assert.assertEquals(HfaTestData.HOLIDAY_TOPIC, attr.getValue());
	}
	
	@Test
	public void validTwoLineFilmTopicGenreRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TWO_LINE_TOPIC_GENRE);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		genreBuilder.build(params);
		
		List<Entity> subjectEntities = parentEntity.getChildren(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertEquals(4, subjectEntities.size());
	}
	
	@Test
	public void validTopicGenreAndKeywordRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TWO_LINE_TOPIC_GENRE_KEYWORD);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		genreBuilder.build(params);
		
		List<Entity> subjectEntities = parentEntity.getChildren(Ld4lObjectProp.HAS_SUBJECT);
		Assert.assertEquals(3, subjectEntities.size());
		
		List<Attribute> attrs = parentEntity.getAttributes(HfaDatatypeProp.KEYWORDS);
		Assert.assertNotNull(attrs);
		Assert.assertEquals(1, attrs.size());
		Attribute attr = attrs.get(0);
		Assert.assertEquals(ENTRY_NOT_IN_CONCORDANCE, attr.getValue());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setParent(parentEntity);
		
		genreBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(null);
		
		genreBuilder.build(params);
	}
}
