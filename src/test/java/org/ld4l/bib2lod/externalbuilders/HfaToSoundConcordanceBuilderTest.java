/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

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
import org.ld4l.bib2lod.csv.hfa.SoundConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedNamedIndividual;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
/**
 * Tests the HfaToSoundConcordanceBuilder class.
 */
public class HfaToSoundConcordanceBuilderTest extends AbstractHfaTest {
    
	private ConcordanceReferenceBuilder soundBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, ConverterException {
    	HfaToSoundConcordanceBuilder builder = new HfaToSoundConcordanceBuilder();
    	builder.setSoundConcordanceManager(new SoundConcordanceManager("/test_sound.csv"));
        soundBuilder = builder;
        parentEntity = new Entity(Ld4lInstanceType.INSTANCE);
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
    }
	
	@Test
	public void validSoundConcordanceMatch() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		soundBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(HfaObjectProp.HAS_CHARACTERISTIC);
		Assert.assertEquals(2, uris.size());
		String uri = uris.get(0);
		HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.SOUND_ASPECT, "1005");
		Assert.assertEquals(namedIndividual.uri(), uri);
		uri = uris.get(1);
		namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.SOUND_CONTENT, "1001");
		Assert.assertTrue(uris.contains(namedIndividual.uri()));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity);
		
		soundBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null);
		
		soundBuilder.build(params);
	}
}
