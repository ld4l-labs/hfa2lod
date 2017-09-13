package org.ld4l.bib2lod.util;

import org.junit.Assert;
import org.junit.Test;

public class Hfa2LodDateUtilsTest {


	public static final String EXPECTED = "1999" + Hfa2LodDateUtils.ISO_UNCERTAIN_AND_APPROXIMATE_CHAR;
	
	@Test
	public void testConvertUncertainAndApproximateDateString() {
		
		String result = Hfa2LodDateUtils.convertCircaDateToISOStandard("ca. 1999");
		Assert.assertEquals(EXPECTED, result);
		
		result = Hfa2LodDateUtils.convertCircaDateToISOStandard(" ca. 1999");
		Assert.assertEquals(EXPECTED, result);
		
		result = Hfa2LodDateUtils.convertCircaDateToISOStandard(" ca. 1999 ");
		Assert.assertEquals(EXPECTED, result);
		
		result = Hfa2LodDateUtils.convertCircaDateToISOStandard("1999");
		Assert.assertEquals("1999", result);
	}
	
	@Test
	public void testIsConvertUncertainAndApproximateDateString() {
		
		boolean result = Hfa2LodDateUtils.isCircaDate("ca. 1999");
		Assert.assertTrue(result);
		
		result = Hfa2LodDateUtils.isCircaDate(" ca. 1999");
		Assert.assertTrue(result);
		
		result = Hfa2LodDateUtils.isCircaDate(" ca. 1999 ");
		Assert.assertTrue(result);
		
		result = Hfa2LodDateUtils.isCircaDate("1999");
		Assert.assertFalse(result);
	}
}
