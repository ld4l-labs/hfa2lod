/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.MapCachingService;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaCollectionType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.uris.RandomUriMinter;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Tests the HfaToCollectionBuilder class.
 */
public class HfaToCollectionBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder collectionBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
    private static BaseMockBib2LodObjectFactory factory;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(UriService.class, new RandomUriMinter());
        factory.addInstance(CachingService.class, new MapCachingService());
        factory.addInstance(EntityBuilderFactory.class, new HfaToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        collectionBuilder = new HfaToCollectionBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validFullRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		Entity collectionEntity = collectionBuilder.build(params);

		Assert.assertNotNull(collectionEntity);
		Attribute labelAttr = collectionEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		Assert.assertEquals(HfaTestData.COLLECTION, labelAttr.getValue());
		
		List<Type> types = collectionEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertTrue(types.contains(HfaCollectionType.COLLECTION));
	}
	
	@Test
	public void nullParent_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a collection.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null);
		
		collectionBuilder.build(params);
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a collection.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity);
		
		collectionBuilder.build(params);
	}
}
