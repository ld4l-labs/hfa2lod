package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class CharacteristicsConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_characteristics.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new CharacteristicsConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new CharacteristicsConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readData() {
		try {
			CharacteristicsConcordanceManager mgr = new CharacteristicsConcordanceManager(TEST_CSV_FILE);
			Map<String, CharacteristicsConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(41, map.size());
			
			CharacteristicsConcordanceBean bean = mgr.getConcordanceEntry("Dirty");
			Assert.assertNotNull(bean);
			Assert.assertEquals("Dirty", bean.getMatchingText());
			Assert.assertEquals("Condition Defects", bean.getField());
			Assert.assertEquals("mi:Dirty", bean.getNamedIndividual());
			
			bean = mgr.getConcordanceEntry("no-entry");
			Assert.assertNull(bean);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}
