/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class MaterialTypeConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_material_types.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new MaterialTypeConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new MaterialTypeConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			MaterialTypeConcordanceManager mgr = new MaterialTypeConcordanceManager(TEST_CSV_FILE);
			Map<String, MaterialTypeConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(7, map.size());
			
			MaterialTypeConcordanceBean bean = mgr.getConcordanceEntry("35mm");
			Assert.assertNotNull(bean);
			Assert.assertEquals("35mm", bean.getMatchingText());
			Assert.assertEquals("HFA Format(s)", bean.getLabel());
			Assert.assertEquals("mi:35mmFilm", bean.getOntClass());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
