package org.ld4l.bib2lod.testing;

import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;

/**
 * Test data for various HFA tests.
 */
public class HfaTestData {
    
	public static final String TITLE_TEXT = "Title";
	public static final String PREFIX_TEXT = "The";
	public static final String HFA_NUMBER = "1234";

	public static final String VALID_TITLE = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>";
	
	public static final String INVALID_TITLE_NO_TEXT = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + "</col>";

	
	public static final String VALID_TITLE_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";

	public static final String VALID_PREFIX = 
			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>";

	public static final String VALID_TITLE_AND_PREFIX_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		    			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";

	public static final String INVALID_PREFIX_ONLY_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		    			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FULL_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		    			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
}
