/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

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
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the HfaToActivityBuilder class.
 */
public class HfaToActivityBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder activityBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	private HfaTextField field;

    private static BaseMockBib2LodObjectFactory factory;

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new HfaToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        activityBuilder = new HfaToActivityBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
        Element element = XmlTestUtils.buildElementFromString(HfaTestData.PRODUCERS_HFA_RECORD);
        field = new HfaTextField(element);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PRODUCER_ACTIVITY)
				.setField(field);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		List<Type> types = activityEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(HfaActivityType.PRODUCER_ACTIVITY));
		Attribute labelAttr = activityEntity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		Assert.assertEquals(HfaActivityType.PRODUCER_ACTIVITY.label(), labelAttr.getValue());
		
		List<Entity> agents = activityEntity.getChildren(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agents);
		Assert.assertEquals(2, agents.size());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build an activity.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity)
				.setType(HfaActivityType.EDITOR_ACTIVITY)
				.setField(field);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build an activity.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null)
				.setType(HfaActivityType.EDITOR_ACTIVITY)
				.setField(field);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullActivityType_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A Type is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(null)
				.setField(field);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void nullHfaField_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A field is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.EDITOR_ACTIVITY)
				.setField(null);
		
		activityBuilder.build(params);
	}

}
