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

public class LocationConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_location.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new LocationConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new LocationConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			LocationConcordanceManager mgr = new LocationConcordanceManager(TEST_CSV_FILE);
			Map<String, LocationConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(2, map.size());
			
			LocationConcordanceBean bean = mgr.getConcordanceEntry("Upper Volta");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Upper Volta", bean.getMatchingText());
			Assert.assertEquals("Upper Volta", bean.getLabel());
			Assert.assertEquals("http://someplace.org/language/UpperVolta", bean.getUri());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
