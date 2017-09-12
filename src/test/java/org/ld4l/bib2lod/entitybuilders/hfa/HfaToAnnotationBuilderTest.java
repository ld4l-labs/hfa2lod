/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTextualBodyType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the HfaToAnnotationBuilder class.
 */
public class HfaToAnnotationBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder annotationBuilder;
	private Entity parentEntity;
	private HfaTextField field;
	private NamedIndividual namedIndividual;

    @Before
    public void setUp() throws RecordException {
        annotationBuilder = new HfaToAnnotationBuilder();
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
        Element element = XmlTestUtils.buildElementFromString(HfaTestData.CAST_MEMBERS_HFA_FIELD);
        field = new HfaTextField(element);
        namedIndividual = HfaNamedIndividual.LISTING_CREDITS;
    }
	
	@Test
	public void validPrincipalCastRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setParent(parentEntity)
				.setField(field)
				.setNamedIndividual(namedIndividual);
		
		Entity annotationEntity = annotationBuilder.build(params);

		Assert.assertNotNull(annotationEntity);
		Type type = annotationEntity.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(Ld4lAnnotationType.ANNOTATION, type);
		Assert.assertEquals(HfaNamedIndividual.LISTING_CREDITS.uri(),
				annotationEntity.getExternal(Ld4lObjectProp.MOTIVATED_BY));
		String creatorEntity =  annotationEntity.getExternal(Ld4lObjectProp.HAS_CREATOR);
		Assert.assertNotNull(creatorEntity);
		Assert.assertEquals(HfaToAnnotationBuilder.HARVARD_LIBRARY_CREATOR, creatorEntity);
		
		Entity textualBodyEntity = annotationEntity.getChild(Ld4lObjectProp.HAS_BODY);
		Assert.assertNotNull(textualBodyEntity);
		type = textualBodyEntity.getType();
		Assert.assertNotNull(type);
		Assert.assertEquals(Ld4lTextualBodyType.TEXTUAL_BODY, type);
		Attribute valueAttr = textualBodyEntity.getAttribute(Ld4lDatatypeProp.VALUE);
		Assert.assertNotNull(valueAttr);
		Assert.assertEquals(HfaTestData.CAST_MEMBERS, valueAttr.getValue());
	}
	
	@Test
	public void nullField_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaTextField is required to build an Annotation.");
		BuildParams params = new BuildParams()
				.setParent(parentEntity)
				.setField(null)
				.setNamedIndividual(namedIndividual);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void nullNamedIndividual_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A NamedIndividual is required to build an Annotation.");
		BuildParams params = new BuildParams()
				.setParent(parentEntity)
				.setField(field)
				.setNamedIndividual(null);
		
		annotationBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build an Annotation.");
		BuildParams params = new BuildParams();
		params.setParent(null);
		params.setValue(HfaTestData.CAST1);
		
		annotationBuilder.build(params);
	}
}
