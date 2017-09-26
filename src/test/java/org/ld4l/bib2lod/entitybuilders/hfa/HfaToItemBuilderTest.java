/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.MapCachingService;
import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.hfa.HfaDatatypeProp;
import org.ld4l.bib2lod.ontology.hfa.HfaEventType;
import org.ld4l.bib2lod.ontology.hfa.HfaHistoryType;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.uris.RandomUriMinter;
import org.ld4l.bib2lod.uris.UriService;
import org.ld4l.bib2lod.util.HfaConstants;

/**
 * Tests the HfaToTitleBuilder class.
 */
public class HfaToItemBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder itemBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	
    private static BaseMockBib2LodObjectFactory factory;
    private static final String TITLE = "The title";
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(UriService.class, new RandomUriMinter());
        factory.addInstance(CachingService.class, new MapCachingService());
        factory.addInstance(EntityBuilderFactory.class, new HfaToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        itemBuilder = new HfaToItemBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_ITEM_HFA_RECORD);
        parentEntity = new Entity(Ld4lInstanceType.INSTANCE);
    }
	
	@Test
	public void validItemRecord() throws Exception {
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		Entity item = itemBuilder.build(params);

		Assert.assertNotNull(item);
		Type type = item.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(Ld4lItemType.ITEM, type);
		
		Attribute labelAttr = item.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		String prefixPlusTitle = HfaTestData.PREFIX_TEXT + " " + HfaTestData.TITLE_TEXT;
		Assert.assertEquals(prefixPlusTitle, labelAttr.getValue());
		
		Entity custodialHistory = item.getChild(HfaObjectProp.HAS_CUSTODIAL_HISTORY);
		Assert.assertNotNull(custodialHistory);
		String label = custodialHistory.getValue(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(label);
		Assert.assertEquals(HfaHistoryType.CUSTODIAL_HISTORY.label(), label);
		
		Entity itemEvent = custodialHistory.getChild(Ld4lObjectProp.HAS_PART);
		Assert.assertNotNull(itemEvent);
		type = itemEvent.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaEventType.ITEM_EVENT, type);

		Entity loanEvent = itemEvent.getChild(Ld4lObjectProp.IS_PART_OF);
		Assert.assertNotNull(loanEvent);
		type = loanEvent.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaEventType.LOAN_EVENT, type);
		
		Attribute loanDate = loanEvent.getAttribute(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(loanDate);
		Assert.assertEquals(HfaTestData.LOAN_DATE, loanDate.getValue());
		Assert.assertEquals(XsdDatatype.DATE, loanDate.getDatatype());
		
		String location = loanEvent.getExternal(Ld4lObjectProp.HAS_LOCATION);
		Assert.assertNotNull(location);
		String expectedLocation = HfaTestData.STATE + ", " + HfaTestData.COUNTRY1;
		Assert.assertEquals(expectedLocation, location);
		
		Entity borrower = loanEvent.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.BORROWER_ACTIVITY);
		Assert.assertNotNull(borrower);
		Entity lender = loanEvent.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.LENDER_ACTIVITY);
		Assert.assertNotNull(lender);
		
		Entity exhibition = item.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaEventType.EXHIBITION_EVENT);
		Assert.assertNotNull(exhibition);
		label = exhibition.getValue(Ld4lDatatypeProp.LABEL);
		Assert.assertEquals(HfaEventType.EXHIBITION_EVENT.label(), label);
		
		Attribute exhibitionDate = exhibition.getAttribute(Ld4lDatatypeProp.DATE);
		Assert.assertNotNull(exhibitionDate);
		Assert.assertEquals(HfaTestData.LOAN_DATE, exhibitionDate.getValue());
		Assert.assertEquals(XsdDatatype.DATE, exhibitionDate.getDatatype());
		
		Attribute runningTime = item.getAttribute(HfaDatatypeProp.DURATION_SCHEMA);
		Assert.assertNotNull(runningTime);
		Assert.assertEquals(HfaTestData.ISO_8601_DURATION, runningTime.getValue());
		runningTime = item.getAttribute(HfaDatatypeProp.DURATION_BF);
		Assert.assertNotNull(runningTime);
		Assert.assertEquals(HfaTestData.DURATION, runningTime.getValue());
		
	}
	
	@Test
	public void valid2LoanItemRecord() throws Exception {
		
		hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_ITEM_HFA_RECORD_2_LOANS);
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		Entity item = itemBuilder.build(params);
		Assert.assertNotNull(item);
		
		Entity custodialHistory = item.getChild(HfaObjectProp.HAS_CUSTODIAL_HISTORY);
		Assert.assertNotNull(custodialHistory);
		String label = custodialHistory.getValue(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(label);
		Assert.assertEquals(HfaHistoryType.CUSTODIAL_HISTORY.label(), label);
		
		Entity itemEvent = custodialHistory.getChild(Ld4lObjectProp.HAS_PART);
		Assert.assertNotNull(itemEvent);
		Type type = itemEvent.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(HfaEventType.ITEM_EVENT, type);
		
		List<Entity> loanEvents = itemEvent.getChildren(Ld4lObjectProp.IS_PART_OF);
		Assert.assertEquals(2, loanEvents.size());
		
		Entity loan = loanEvents.get(0);
		Entity borrower = loan.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.BORROWER_ACTIVITY);
		Assert.assertNotNull(borrower);
		Entity agent = borrower.getChild(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agent);
		Attribute labelAttr = agent.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		String agentLabel = HfaTestData.OUTSIDE_BORROWER1 + ", " + HfaTestData.COMPANY1_NAME;
		Assert.assertEquals(agentLabel, labelAttr.getValue());
		
		Entity lender = loan.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.LENDER_ACTIVITY);
		Assert.assertNotNull(lender);
		String lenderAgent = lender.getExternal(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(lenderAgent);
		Assert.assertEquals(HfaConstants.HARVARD_LIBRARY_CREATOR, lenderAgent);
		
		loan = loanEvents.get(1);
		borrower = loan.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.BORROWER_ACTIVITY);
		Assert.assertNotNull(borrower);
		agent = borrower.getChild(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(agent);
		labelAttr = agent.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		agentLabel = HfaTestData.OUTSIDE_BORROWER2;
		Assert.assertEquals(agentLabel, labelAttr.getValue());
		
		lender = loan.getChild(Ld4lObjectProp.HAS_ACTIVITY, HfaActivityType.LENDER_ACTIVITY);
		Assert.assertNotNull(lender);
		lenderAgent = lender.getExternal(Ld4lObjectProp.HAS_AGENT);
		Assert.assertNotNull(lenderAgent);
		Assert.assertEquals(HfaConstants.HARVARD_LIBRARY_CREATOR, lenderAgent);

		List<Entity> exhibitions = item.getChildren(Ld4lObjectProp.HAS_ACTIVITY, HfaEventType.EXHIBITION_EVENT);
		Assert.assertEquals(2, exhibitions.size());
}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build an Item.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setParent(parentEntity);
		
		itemBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build an Item.");
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(null);
		
		itemBuilder.build(params);
	}
}
