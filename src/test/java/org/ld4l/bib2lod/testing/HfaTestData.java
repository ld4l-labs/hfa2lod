package org.ld4l.bib2lod.testing;

import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;

/**
 * Test data for various HFA tests.
 */
public class HfaTestData {
    
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String ENTRY_NOT_IN_CONCORDANCE = "not in concordance file";

	public static final String TITLE_TEXT = "Title";
	public static final String PREFIX_TEXT = "The";
	public static final String HFA_NUMBER = "1234";
	public static final String ALT_HFA_NUMBER = "V9876";
	public static final String FICTION_GENRE = "Fiction";
	public static final String SHORT_GENRE = "Short";
	public static final String TELEVISION_PROGRAM_GENRE = "Television program";
	public static final String TWO_LINE_GENRE = "Non fiction" + NEW_LINE + "Feature";
	public static final String TELEVISION_GENRE = "Television programs";
	public static final String TELEVISION_SERIAL_GENRE = "Serial";
	public static final String HOLIDAY_TOPIC = "Holiday";
	public static final String TWO_LINE_TOPIC_GENRE = "Holiday" + NEW_LINE + "Religion";
	public static final String TWO_LINE_TOPIC_GENRE_AND_KEYWORD = "Holiday" + NEW_LINE + ENTRY_NOT_IN_CONCORDANCE;
	public static final String FILM_DIRECTOR = "Tony Conrad";
	public static final String COLLECTION = "Test Collection";
	public static final String EDITOR = "Test Editor";
	public static final String PRODUCER1 = "Producer 1 ";
	public static final String PRODUCER2 = "Producer2";
	public static final String PRODUCERS = PRODUCER1 + ", " + PRODUCER2;
	public static final String PRODUCTION_COMPANY1 = "Production Company 1";
	public static final String PRODUCTION_COMPANY2 = "Production Company 2";
	public static final String PRODUCTION_COMPANY3 = "Production Company 3";
	public static final String PRODUCTION_COMPANIES = PRODUCTION_COMPANY1 + " /" +
							   PRODUCTION_COMPANY2 + " , " +
							   PRODUCTION_COMPANY3;
	public static final String COUNTRY1 = "Upper Volta";
	public static final String COUNTRY2 = "Moldova";
	public static final String COUNTRY3 = "Boreo";
	public static final String COUNTRIES = COUNTRY1 + " /" + COUNTRY2 + " , " + COUNTRY3;
	public static final String CAST1 = " Person 1 ";
	public static final String CAST2 = "Person 2";
	public static final String CAST3 = "Person 3";
	public static final String CAST_MEMBERS = CAST1 + "," + CAST2 + " , " + CAST3;
	

	public static final String VALID_TITLE = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>";
	
	public static final String INVALID_TITLE_NO_TEXT = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + "</col>";
	
	public static final String FILM_DIRECTOR_HFA_RECORD = 
    		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>";
	
	public static final String PRODUCERS_HFA_RECORD =
    		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCER.getColumnAttributeText() + "'>" + PRODUCERS + "</col>";

	public static final String CAST_MEMBERS_HFA_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.PRINCIPAL_CAST.getColumnAttributeText() + "'>" + CAST_MEMBERS + "</col>";

	public static final String VALID_TITLE_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_HFA_RECORD_ALTERNATE_ITEM_NUMBER = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + ALT_HFA_NUMBER + "</col>" +
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
		        		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COLLECTION.getColumnAttributeText() + "'>" + COLLECTION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.EDITOR.getColumnAttributeText() + "'>" + EDITOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCER.getColumnAttributeText() + "'>" + PRODUCERS + "</col>" +
		        		CAST_MEMBERS_HFA_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_GENRES = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + FICTION_GENRE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.SHORT.getColumnAttributeText() + "\">" + SHORT_GENRE + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_GENRE_WITH_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + ENTRY_NOT_IN_CONCORDANCE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.SHORT.getColumnAttributeText() + "\">" + FICTION_GENRE + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_FILM_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + TWO_LINE_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TELEVISION_GENRES = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TELEVISION_GENRE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.CLASSIFICATION.getColumnAttributeText() + "\">" + TELEVISION_SERIAL_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TELEVISION_GENRES_WITH_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TELEVISION_GENRE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + ENTRY_NOT_IN_CONCORDANCE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.CLASSIFICATION.getColumnAttributeText() + "\">" + TELEVISION_SERIAL_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TOPIC_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + HOLIDAY_TOPIC + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_TOPIC_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TWO_LINE_TOPIC_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_TOPIC_GENRE_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TWO_LINE_TOPIC_GENRE_AND_KEYWORD + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_DIRECTOR_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_PRODUCTION_COMPANY_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCTION_COMPANY.getColumnAttributeText() + "'>" + PRODUCTION_COMPANIES + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COUNTRY.getColumnAttributeText() + "'>" + COUNTRIES + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_PRICIPAL_CAST_RECORD =
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + HFA_NUMBER + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>" +
		        		CAST_MEMBERS_HFA_FIELD +
	        		"</row>" +
	        "</HFA-data>";
}
