/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.conversion.Converter.ConverterException;
import org.ld4l.bib2lod.csv.hfa.NamesConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the HfaToAgentBuilder class.
 */
public class HfaToAgentBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder agentBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;
	private HfaTextField directorField;

    @Before
    public void setUp() throws RecordException, ConverterException, URISyntaxException, IOException {
    	HfaToAgentBuilder builder = new HfaToAgentBuilder();
    	builder.setNamesConcordanceManager(new NamesConcordanceManager("/test_names.csv"));
        agentBuilder = builder;
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FILM_DIRECTOR_HFA_RECORD);
        parentEntity = new Entity(Ld4lAgentType.AGENT);
		Element element = XmlTestUtils.buildElementFromString(HfaTestData.FILM_DIRECTOR_HFA_FIELD);
		directorField = new HfaTextField(element);
    }
	
	@Test
	public void validFullAgentRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setValue(directorField.getTextValue());
		
		Entity agentEntity = agentBuilder.build(params);

		Assert.assertNotNull(agentEntity);
		List<Type> types = agentEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lAgentType.AGENT));
		Assert.assertNotNull(agentEntity.getAttribute(Ld4lDatatypeProp.LABEL));
		Assert.assertNotNull(agentEntity.getExternal(HfaObjectProp.HAS_PUBLIC_IDENTITY));
		Assert.assertNotNull(agentEntity.getExternal(HfaObjectProp.HAS_WEB_PAGE));
	}
	
	@Test
	public void validMissingConcordanceValueAgentRecord() throws Exception {
		
		String agentName = "not-in-concordance-file";
		String agentElementString = "<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() +
				"'>" + agentName + "</col>";

		Element element = XmlTestUtils.buildElementFromString(agentElementString);
		HfaTextField directorField = new HfaTextField(element);
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setValue(directorField.getTextValue());
		
		Entity agentEntity = agentBuilder.build(params);

		Assert.assertNotNull(agentEntity);
		List<Type> types = agentEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lAgentType.AGENT));
		Assert.assertNotNull(agentEntity.getAttribute(Ld4lDatatypeProp.LABEL));
		Assert.assertEquals(agentName, agentEntity.getAttribute(Ld4lDatatypeProp.LABEL).getValue());
		Assert.assertNull(agentEntity.getExternal(HfaObjectProp.HAS_PUBLIC_IDENTITY));
		Assert.assertNull(agentEntity.getExternal(HfaObjectProp.HAS_WEB_PAGE));
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build an agent.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity)
				.setValue(directorField.getTextValue());
		
		agentBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build an agent.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null)
				.setValue(directorField.getTextValue());
		
		agentBuilder.build(params);
	}
	
	@Test
	public void nullValue_noDirector_throwsException() throws Exception {
		expectException(EntityBuilderException.class, "A value is required containing the agent name.");
		hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_HFA_RECORD);
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity)
				.setValue(null);
		
		agentBuilder.build(params);
	}
}
