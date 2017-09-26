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
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.uris.RandomUriMinter;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Tests the HfaToInstanceBuilder class.
 */
public class HfaToInstanceBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder instanceBuilder;
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
        instanceBuilder = new HfaToInstanceBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validFullRecord() throws Exception {
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		Entity instanceEntity = instanceBuilder.build(params);

		Assert.assertNotNull(instanceEntity);
		List<Type> types = instanceEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertTrue(types.contains(Ld4lInstanceType.INSTANCE));
		
		Attribute labelAttr = instanceEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		String prefixPlusTitle = HfaTestData.PREFIX_TEXT + " " + HfaTestData.TITLE_TEXT;
		Assert.assertEquals(prefixPlusTitle, labelAttr.getValue());
		
		List<Entity> titleEntities = instanceEntity.getChildren(Ld4lObjectProp.HAS_TITLE);
		Assert.assertNotNull(titleEntities);
		Assert.assertEquals(7, titleEntities.size());
		
		Entity collectionEntity = instanceEntity.getChild(HfaObjectProp.IS_PART_OF);
		Assert.assertNotNull(collectionEntity);
		
		String language = instanceEntity.getExternal(Ld4lObjectProp.HAS_LANGUAGE);
		Assert.assertNotNull(language);
		Assert.assertEquals(HfaTestData.LANGUAGE, language);
		
		language = instanceEntity.getExternal(HfaObjectProp.HAS_SUBTITLE_LANGUAGE);
		Assert.assertNotNull(language);
		Assert.assertEquals(HfaTestData.LANGUAGE, language);
		
		language = instanceEntity.getExternal(HfaObjectProp.HAS_INTERTITLE_LANGUAGE);
		Assert.assertNotNull(language);
		Assert.assertEquals(HfaTestData.LANGUAGE, language);
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build an Instance.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		
		instanceBuilder.build(params);
	}
}
