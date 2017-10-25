/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.MapCachingService;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
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
        factory.addInstance(CachingService.class, new MapCachingService());
    }

    @Before
    public void setUp() throws RecordException {
        activityBuilder = new HfaToActivityBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FULL_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
        Element element = XmlTestUtils.buildElementFromString(HfaTestData.PRODUCER1_HFA_FIELD);
        field = new HfaTextField(element);
    }
	
	@Test
	public void validRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PRODUCER_ACTIVITY)
				.setValue(field.getTextValue());
		
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
		Assert.assertEquals(1, agents.size());
		Entity agent = agents.get(0);
	}
	
	@Test
	public void validProductionCompanyRecord() throws Exception {

        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_PRODUCTION_COMPANY_HFA_RECORD);

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PRODUCTION_COMPANY_ACTIVITY)
				.setValue(HfaTestData.PRODUCTION_COMPANY1);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		Type type = activityEntity.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaActivityType.PRODUCTION_COMPANY_ACTIVITY, type);
		List<Entity> agentEntities = activityEntity.getChildren(Ld4lObjectProp.HAS_AGENT);
		Assert.assertEquals(1, agentEntities.size());
		Entity agentEntity = agentEntities.get(0);
		Assert.assertEquals(HfaTestData.PRODUCTION_COMPANY1, agentEntity.getAttribute(Ld4lDatatypeProp.LABEL).getValue());
	}
	
	@Test
	public void validProviderRecord() throws Exception {

        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_COUNTRIES_AND_YEAR_RECORD);

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PROVIDER_ACTIVITY)
				.setValue(null);
		
		Entity activityEntity = activityBuilder.build(params);

		Assert.assertNotNull(activityEntity);
		Type type = activityEntity.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaActivityType.PROVIDER_ACTIVITY, type);
		List<Entity> agentEntities = activityEntity.getChildren(Ld4lObjectProp.HAS_AGENT);
		Assert.assertEquals(0, agentEntities.size());
		
		Attribute dateAttr = activityEntity.getAttribute(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(dateAttr);
		Assert.assertEquals(HfaTestData.YEAR_OF_RELEASE, dateAttr.getValue());
		Assert.assertEquals(BibDatatype.EDTF, dateAttr.getDatatype());
		
		List<String> locations = activityEntity.getExternals(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertEquals(4, locations.size());
	}
	
	@Test
	public void missingYearAndLocationProviderRecord() throws Exception {

        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_HFA_RECORD);

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PROVIDER_ACTIVITY)
				.setValue(null);
		
		Entity activityEntity = activityBuilder.build(params);
		Assert.assertNull(activityEntity);
	}
	
	@Test
	public void yearOnlyProviderRecord() throws Exception {

        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_YEAR_ONLY_RECORD);

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PROVIDER_ACTIVITY)
				.setValue(null);
		
		Entity activityEntity = activityBuilder.build(params);
		Assert.assertNotNull(activityEntity);
		Attribute dateAttr = activityEntity.getAttribute(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(dateAttr);
		Assert.assertEquals(HfaTestData.YEAR_OF_RELEASE, dateAttr.getValue());
		Assert.assertEquals(BibDatatype.EDTF, dateAttr.getDatatype());
		
		List<String> locations = activityEntity.getExternals(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertEquals(0, locations.size());
	}
	
	@Test
	public void locationOnlyProviderRecord() throws Exception {

        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_COUNTRY_ONLY_RECORD);

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.PROVIDER_ACTIVITY)
				.setValue(null);
		
		Entity activityEntity = activityBuilder.build(params);
		Assert.assertNotNull(activityEntity);
		
		List<String> locations = activityEntity.getExternals(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertEquals(1, locations.size());
		String location = locations.get(0);
		// FIXME: this is just to accommodate kludge until concordances are complete
		Assert.assertEquals(HfaTestData.tempUriBase + HfaTestData.COUNTRY1.replace(' ', '_'), location);

		Attribute dateAttr = activityEntity.getAttribute(Ld4lDatatypeProp.DATE);
		Assert.assertNull(dateAttr);
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
	public void nullValue_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A field text value is required to build an Activity.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.EDITOR_ACTIVITY)
				.setValue(null);
		
		activityBuilder.build(params);
	}
	
	@Test
	public void validMusicRecord() throws Exception {

		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setType(HfaActivityType.MUSICIAN_ACTIVITY)
				.setValue(HfaTestData.MUSIC);
		
		Entity activityEntity = activityBuilder.build(params);
		Assert.assertNotNull(activityEntity);
		Type type = activityEntity.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaActivityType.MUSICIAN_ACTIVITY, type);
		List<Entity> agentEntities = activityEntity.getChildren(Ld4lObjectProp.HAS_AGENT);
		Assert.assertEquals(1, agentEntities.size());
		Entity agentEntity = agentEntities.get(0);
		Assert.assertEquals(HfaTestData.MUSIC, agentEntity.getAttribute(Ld4lDatatypeProp.LABEL).getValue());
	}

}
