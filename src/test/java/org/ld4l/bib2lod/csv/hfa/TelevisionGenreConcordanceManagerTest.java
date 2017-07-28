package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TelevisionGenreConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_television_genre.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new TelevisionGenreConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new FilmGenreConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			TelevisionGenreConcordanceManager mgr = new TelevisionGenreConcordanceManager(TEST_CSV_FILE);
			Map<String, TelevisionGenreConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(2, map.size());
			
			TelevisionGenreConcordanceBean bean = mgr.getConcordanceEntry("Serial");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Serial", bean.getHfaTag());
			Assert.assertEquals("http://id.loc.gov/authorities/genreForms/gf2011026680", bean.getLocGenre());
			Assert.assertEquals("", bean.getCombinedAdditionalLocGenre());
			Assert.assertEquals("http://id.worldcat.org/fast/1710566", bean.getFastForm());
			Assert.assertEquals("", bean.getCombinedAdditionalFastForm());
			Assert.assertEquals("", bean.getMovingImageOntClass());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
			
			bean = mgr.getConcordanceEntry("Television programs");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Television programs", bean.getHfaTag());
			Assert.assertEquals("", bean.getLocGenre());
			Assert.assertEquals("", bean.getCombinedAdditionalLocGenre());
			Assert.assertEquals("", bean.getFastForm());
			Assert.assertEquals("", bean.getCombinedAdditionalFastForm());
			Assert.assertEquals("", bean.getMovingImageOntClass());

		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
