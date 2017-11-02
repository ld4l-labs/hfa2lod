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
import org.ld4l.bib2lod.csv.hfa.AspectRatioConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedNamedIndividual;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;

/**
 * Tests the AspectRatioConcordanceManager class.
 */
public class HfaToAspectRatioConcordanceBuilderTest extends AbstractHfaTest {
    
	private ConcordanceReferenceBuilder aspectRatioBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
	private static final Logger LOGGER = LogManager.getLogger();

    @Before
    public void setUp() throws RecordException, URISyntaxException, IOException, EntityBuilderException {
    	HfaToAspectRatioConcordanceBuilder builder = new HfaToAspectRatioConcordanceBuilder();
    	builder.setAspectRatioConcordanceManager(new AspectRatioConcordanceManager("/test_aspect_ratio.csv"));
    	aspectRatioBuilder = builder;
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validAspectRationConcordanceMatch() throws Exception {
		
		// this requires a bf:MovingImage parent
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		aspectRatioBuilder.build(params);
		
		List<String> uris = parentEntity.getExternals(HfaObjectProp.HAS_CHARACTERISTIC);
		Assert.assertNotNull(uris);
		Assert.assertEquals(1, uris.size());
		String uri = uris.get(0);
		HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, "Widescreen166");
		Assert.assertEquals(namedIndividual.uri(), uri);
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity);
		
		aspectRatioBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null);
		
		aspectRatioBuilder.build(params);
	}
}
