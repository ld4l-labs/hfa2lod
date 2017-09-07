/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;

/**
 * Tests the HfaToPrincipalCastBuilder class.
 */
public class HfaToPrincipalCastBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder prinicpalCastBuilder;
//	private HfaRecord hfaRecord;
	private Entity parentEntity;

    @Before
    public void setUp() throws RecordException {
        prinicpalCastBuilder = new HfaToPrincipalCastBuilder();
//        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_HFA_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validPrincipalCastOnlyRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setParent(parentEntity)
				.setValue(HfaTestData.CAST1);
		
		Entity castEntity = prinicpalCastBuilder.build(params);

		Assert.assertNotNull(castEntity);
		List<Type> types = castEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lAgentType.PERSON));
		
		List<String> labels = castEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(HfaTestData.CAST1.trim(), labels.get(0).trim());
	}
	
	@Test
	public void nullValue_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A castName value is required to build a cast entry.");
		BuildParams params = new BuildParams()
				.setParent(parentEntity)
				.setValue(null);
		
		prinicpalCastBuilder.build(params);
	}
	
	@Test
	public void nullParentEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a cast entry.");
		BuildParams params = new BuildParams();
		params.setParent(null);
		params.setValue(HfaTestData.CAST1);
		
		prinicpalCastBuilder.build(params);
	}
}
