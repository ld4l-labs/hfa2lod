package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class SubjectConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_subject.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new SubjectConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new SubjectConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			SubjectConcordanceManager mgr = new SubjectConcordanceManager(TEST_CSV_FILE);
			Map<String, SubjectConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(15, map.size());
			
			SubjectConcordanceBean bean = mgr.getConcordanceEntry("Holiday");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Holiday", bean.getHfaTag());
			Assert.assertEquals("http://id.loc.gov/authorities/subjects/sh85061453", bean.getLcsh());
			Assert.assertEquals("http://id.worldcat.org/fast/958730", bean.getFast());
			Assert.assertEquals("http://vocab.getty.edu/aat/300400818", bean.getGetty());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
			
			bean = mgr.getConcordanceEntry("Industry");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Industry", bean.getHfaTag());
			Assert.assertEquals("", bean.getLcsh());
			Assert.assertEquals("", bean.getFast());
			Assert.assertEquals("", bean.getGetty());
			
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testMixedCaseKey() {
		try {
			SubjectConcordanceManager mgr = new SubjectConcordanceManager(TEST_CSV_FILE);
			Map<String, SubjectConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			SubjectConcordanceBean bean = mgr.getConcordanceEntry("hoLiDay");
			Assert.assertNotNull(bean);
			
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
