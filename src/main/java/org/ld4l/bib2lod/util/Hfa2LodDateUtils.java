package org.ld4l.bib2lod.util;

import org.apache.commons.lang3.StringUtils;

public final class Hfa2LodDateUtils {
	
	private static final String UNCERTAIN_AND_APPROXIMATE_MARKER = "ca.";
	protected static final char ISO_UNCERTAIN_AND_APPROXIMATE_CHAR = '%';
    
	/**
	 * Check to see if the date contains the value "ca.".
	 * 
	 * @param s The value to check.
	 * @return <code>true</code> if the value contains "ca."; <code>false</code> otherwise.
	 */
	public static boolean isCircaDate(String s) {
		return (s != null && s.contains(UNCERTAIN_AND_APPROXIMATE_MARKER));
	}
	
	/**
	 * Remove the "ca.", trim whitespace and add ISO marker character indicating 
	 * uncertain and approximate.
	 * 
	 * @param s The value to be evaluated.
	 * @return The value stripped of "ca.", trimmed and with the ISO character appended if
	 * 		   the value contains "ca."; otherwise return the original value.
	 */
    public static String convertCircaDateToISOStandard(String s) {
    	String result = null;
    	if (s != null && s.contains(UNCERTAIN_AND_APPROXIMATE_MARKER)) {
    		result = StringUtils.remove(s, UNCERTAIN_AND_APPROXIMATE_MARKER).trim() +
    				ISO_UNCERTAIN_AND_APPROXIMATE_CHAR;
    	} else {
    		result = s;
    	}
    	
        return result;
    }
}
