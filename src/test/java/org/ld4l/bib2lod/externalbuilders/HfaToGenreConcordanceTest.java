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
import org.ld4l.bib2lod.csv.hfa.FilmGenreConcordanceManager;
import org.ld4l.bib2lod.csv.hfa.TelevisionGenreConcordanceManager;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
/**
 * Tests the HfaToGenreConcordance class.
 */
public class HfaToGenreConcordanceTest extends AbstractHfaTest {
    
	private ConcordanceReferenceBuilder genreBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, ConverterException {
    	HfaToGenreConcordanceBuilder builder = new HfaToGenreConcordanceBuilder();
    	builder.setTelevisionConcordanceManager(new TelevisionGenreConcordanceManager("/test_television_genre.csv"));
    	builder.setFilmConcordanceManager(new FilmGenreConcordanceManager("/test_film_genre.csv"));
        genreBuilder = builder;
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validFilmGenreRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FILM_GENRES);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.GENRE_FORM);
		Assert.assertNotNull(uris);
		Assert.assertEquals(2, uris.size());
	}
	
	@Test
	public void validTwoLineFilmGenreRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TWO_LINE_FILM_GENRE);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.GENRE_FORM);
		Assert.assertNotNull(uris);
		Assert.assertEquals(5, uris.size());
	}
	
	@Test
	public void validFilmGenreAndKeywordRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FILM_GENRE_WITH_KEYWORD);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.GENRE_FORM);
		Assert.assertNotNull(uris);
		Assert.assertEquals(2, uris.size());
		
		List<Attribute> attrs = parentEntity.getAttributes(HfaDatatypeProp.KEYWORDS);
		Assert.assertNotNull(attrs);
		Assert.assertEquals(1, attrs.size());
		Attribute attr = attrs.get(0);
		Assert.assertEquals(ENTRY_NOT_IN_CONCORDANCE, attr.getValue());
	}
	
	@Test
	public void validTelevisionGenreRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TELEVISION_GENRES);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.GENRE_FORM);
		Assert.assertNotNull(uris);
		Assert.assertEquals(2, uris.size());
	}
	
	@Test
	public void validTelevisionGenreAndKeywordRecord() throws Exception {
		
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TELEVISION_GENRES_WITH_KEYWORD);
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParentEntity(parentEntity);
		
		genreBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(Ld4lObjectProp.GENRE_FORM);
		Assert.assertNotNull(uris);
		Assert.assertEquals(2, uris.size());
		
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
}
