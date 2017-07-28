package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class FilmGenreConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_film_genre.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new FilmGenreConcordanceManager(TEST_CSV_FILE);
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
			FilmGenreConcordanceManager mgr = new FilmGenreConcordanceManager(TEST_CSV_FILE);
			Map<String, FilmGenreConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(4, map.size());
			
			FilmGenreConcordanceBean bean = mgr.getConcordanceEntry("Non fiction");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Non fiction", bean.getHfaTag());
			Assert.assertEquals("http://id.loc.gov/authorities/genreForms/gf2011026423", bean.getLocGenre());
			Assert.assertEquals("", bean.getCombinedAdditionalLocGenre());
			Assert.assertEquals("http://id.worldcat.org/fast/1710269", bean.getFastForm());
			Assert.assertEquals("", bean.getCombinedAdditionalFastForm());
			Assert.assertEquals("http://vocab.getty.edu/aat/300375156", bean.getMovingImageOntClass());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);

		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
