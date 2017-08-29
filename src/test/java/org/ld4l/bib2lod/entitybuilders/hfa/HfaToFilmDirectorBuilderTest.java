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
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.hfa.HfaToFilmDirectorActivityBuilder;
import org.ld4l.bib2lod.entitybuilders.hfa.HfaToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.hfa.HfaActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.HfaTestData;

/**
 * Tests the HfaToFilmDirectorActivityBuilder class.
 */
public class HfaToFilmDirectorBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder filmDirecotryActivityBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;

    private static BaseMockBib2LodObjectFactory factory;

    @BeforeClass
    public static void setUpClass() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();
        factory.addInstance(EntityBuilderFactory.class, new HfaToLd4lEntityBuilderFactory());
    }

    @Before
    public void setUp() throws RecordException {
        filmDirecotryActivityBuilder = new HfaToFilmDirectorActivityBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_FILM_DIRECTOR_HFA_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validFilmDirectorRecord() throws Exception {
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		Entity filmDirectorActivity = filmDirecotryActivityBuilder.build(params);

		Assert.assertNotNull(filmDirectorActivity);
		List<Type> types = filmDirectorActivity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(HfaActivityType.FILM_DIRECTOR_ACTIVITY));
		Attribute labelAttr = filmDirectorActivity.getAttribute(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labelAttr);
		Assert.assertEquals(HfaActivityType.FILM_DIRECTOR_ACTIVITY.label(), labelAttr.getValue());
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(null)
				.setParent(parentEntity);
		
		filmDirecotryActivityBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(null);
		
		filmDirecotryActivityBuilder.build(params);
	}
	
	@Test
	public void nullReturned_noDirector() throws Exception {
		hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_HFA_RECORD);
		
		BuildParams params = new BuildParams()
				.setRecord(hfaRecord)
				.setParent(parentEntity);
		
		Entity entity = filmDirecotryActivityBuilder.build(params);
		Assert.assertNull(entity);
	}
}
