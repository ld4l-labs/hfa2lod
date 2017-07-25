/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.hfa.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HarvardType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the HfaToMovingImageBuilder class.
 */
public class HfaToMovingImageBuilderTest extends AbstractTestClass {
    
	private EntityBuilder movingImageBuilder;
	private HfaRecord hfaRecord;
	
    private static BaseMockBib2LodObjectFactory factory;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new HfaToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        movingImageBuilder = new HfaToMovingImageBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
    }
	
	@Test
	public void validFullRecord() throws Exception {
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		
		Entity movingImageEntity = movingImageBuilder.build(params);

		Assert.assertNotNull(movingImageEntity);
		List<Type> types = movingImageEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertEquals(1, types.size());
		Assert.assertTrue(types.contains(Ld4lWorkType.MOVING_IMAGE));
		
		List<Entity> titleEntities = movingImageEntity.getChildren(Ld4lObjectProp.HAS_TITLE);
		Assert.assertNotNull(titleEntities);
		Assert.assertEquals(1, titleEntities.size());
		
		List<Entity> instanceEntities = movingImageEntity.getChildren(Ld4lObjectProp.HAS_INSTANCE);
		Assert.assertNotNull(instanceEntities);
		Assert.assertEquals(1, instanceEntities.size());
		
		Entity identifierEntity = movingImageEntity.getChild(Ld4lObjectProp.IDENTIFIED_BY);
		Assert.assertNotNull(identifierEntity);
		List<Type> identifierTypes = identifierEntity.getTypes();
		Assert.assertNotNull(identifierTypes);
		Assert.assertEquals(1, identifierTypes.size());
		Assert.assertTrue(identifierTypes.contains(HarvardType.HFA_NUMBER));
		
		Attribute identifierAttr = identifierEntity.getAttribute(Ld4lDatatypeProp.VALUE);
		Assert.assertNotNull(identifierAttr);
		String identifierValue = identifierAttr.getValue();
		Assert.assertNotNull(identifierValue);
		Assert.assertTrue(identifierValue.contains(HfaTestData.HFA_NUMBER));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a MovingImage.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		
		movingImageBuilder.build(params);
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
