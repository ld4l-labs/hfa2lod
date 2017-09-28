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
import org.ld4l.bib2lod.csv.hfa.CharacteristicsConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedNamedIndividual;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
/**
 * Tests the HfaToCharacteristicsConcordanceBuilder class.
 */
public class HfaToCharacteristicsConcordanceBuilderTest extends AbstractHfaTest {
    
	private ConcordanceReferenceBuilder characteristicsBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, ConverterException {
    	HfaToCharacteristicsConcordanceBuilder builder = new HfaToCharacteristicsConcordanceBuilder();
    	builder.setCharacteristicsConcordanceManager(new CharacteristicsConcordanceManager("/test_characteristics.csv"));
        characteristicsBuilder = builder;
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validColorConcordanceMatch() throws Exception {
		
		// this requires a bf:MovingImage parent
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		characteristicsBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(HfaObjectProp.HAS_COLOR_CONTENT);
		Assert.assertNotNull(uris);
		Assert.assertEquals(1, uris.size());
		String uri = uris.get(0);
		HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, "BW");
		Assert.assertEquals(namedIndividual.uri(), uri);
		
		uris = parentEntity.getExternals(HfaObjectProp.HAS_CHARACTERISTIC);
		Assert.assertEquals(0, uris.size()); // should be on bf:Item, not bf:MovingImage

	}
	
	@Test
	public void validConditionConcordanceMatch() throws Exception {
		
		// this requires a bf:Item
		parentEntity = new Entity(Ld4lItemType.ITEM);
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_ITEM_HFA_RECORD);
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		characteristicsBuilder.build(params);
		
		String uri = parentEntity.getExternal(HfaObjectProp.HAS_COLOR_CONTENT);
		Assert.assertNull(uri); // should be on bf:MovingImage, not bf:Item
		
		List<String> uris = parentEntity.getExternals(HfaObjectProp.HAS_CHARACTERISTIC);
		Assert.assertEquals(3, uris.size());
		HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, "Incomplete");
		Assert.assertTrue(uris.contains(namedIndividual.uri()));
		namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, "PoorSound");
		Assert.assertTrue(uris.contains(namedIndividual.uri()));
		namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, "ExcellentCondition");
		Assert.assertTrue(uris.contains(namedIndividual.uri()));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity);
		
		characteristicsBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(RecordConversionException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null);
		
		characteristicsBuilder.build(params);
	}
}
