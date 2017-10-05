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

public class AspectRatioConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_aspect_ratio.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new AspectRatioConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new AspectRatioConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			AspectRatioConcordanceManager mgr = new AspectRatioConcordanceManager(TEST_CSV_FILE);
			Map<String, AspectRatioConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(5, map.size());
			
			AspectRatioConcordanceBean bean = mgr.getConcordanceEntry("1:1.85");
			Assert.assertNotNull(bean);
			Assert.assertEquals("1:1.85", bean.getMatchingText());
			Assert.assertEquals("mi:Widescreen185", bean.getNamedIndividual());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
