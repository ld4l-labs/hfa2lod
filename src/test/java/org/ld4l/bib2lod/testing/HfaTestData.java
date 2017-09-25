package org.ld4l.bib2lod.testing;

import org.ld4l.bib2lod.record.xml.hfa.HfaLoan;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;

/**
 * Test data for various HFA tests.
 */
public class HfaTestData {
    
	public static final String NEW_LINE = System.getProperty("line.separator");
	public static final String ENTRY_NOT_IN_CONCORDANCE = "not in concordance file";

	public static final String TITLE_TEXT = "Title";
	public static final String PREFIX_TEXT = "The";
	public static final String ITEM_NUMBER = "1234";
	public static final String ALT_ITEM_NUMBER = "V9876";
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
	public static final String YEAR_OF_RELEASE = "1999";
	public static final String COUNTRIES = COUNTRY1 + " /" + COUNTRY2 + " , " + COUNTRY3;
	public static final String CAST1 = " Person 1 ";
	public static final String CAST2 = "Person 2";
	public static final String CAST3 = "Person 3";
	public static final String CAST_MEMBERS = CAST1 + "," + CAST2 + " , " + CAST3;
	public static final String LANGUAGE = "English ";
	public static final String SYNOPSIS = "This is the synopsis.";
	public static final String OUTSIDE_BORROWER1 = "Borrower One Name";
	public static final String OUTSIDE_BORROWER2 = "Borrower Two Name";
	public static final String LOAN_DATE = "2017-02-14";
	public static final String COMPANY1_NAME = "Company One Name";
	public static final String COMPANY2_NAME = "Company Two Name";
	public static final String STATE = "Massachusetts";
	public static final String CITY = "Cambridge";
	public static final String DURATION = "123";
	public static final String ISO_8601_DURATION = "P123M";
	
	public static final String VALID_ITEM_NUMBER_FIELD =
			"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + ITEM_NUMBER + "</col>";

	public static final String VALID_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>";
	
	public static final String VALID_PREFIX_FIELD =
			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>";
	
	public static final String INVALID_TITLE_NO_TEXT_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + "</col>";
	
	public static final String FILM_DIRECTOR_HFA_FIELD = 
    		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>";
	
	public static final String PRODUCER1_HFA_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCER.getColumnAttributeText() + "'>" + PRODUCER1 + "</col>";

	public static final String CAST_MEMBERS_HFA_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.PRINCIPAL_CAST.getColumnAttributeText() + "'>" + CAST_MEMBERS + "</col>";

	public static final String YEAR_OF_RELEASE_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.YEAR_OF_RELEASE.getColumnAttributeText() + "'>" + YEAR_OF_RELEASE + "</col>";

	public static final String COUNTRY1_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.COUNTRY.getColumnAttributeText() + "'>" + COUNTRY1 + "</col>";

	public static final String LOAN1_FIELD =
			"<loan>" +
					"<col column='" + HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER.getColumnAttributeText() + "'>" + OUTSIDE_BORROWER1 + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.COMPANY_NAME.getColumnAttributeText() + "'>" + COMPANY1_NAME + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.CITY.getColumnAttributeText() + "'>" + CITY + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.STATE_PROVINCE.getColumnAttributeText() + "'>" + STATE + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.COUNTRY.getColumnAttributeText() + "'>" + COUNTRY1 + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.DATE_OF_LOAN.getColumnAttributeText() + "'>" + LOAN_DATE + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.PLAY_DATE.getColumnAttributeText() + "'>" + LOAN_DATE + "</col>" +
			"</loan>";

	public static final String LOAN2_FIELD =
			"<loan>" +
					"<col column='" + HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER.getColumnAttributeText() + "'>" + OUTSIDE_BORROWER2 + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.COUNTRY.getColumnAttributeText() + "'>" + COUNTRY1 + "</col>" +
			"</loan>";

	public static final String VALID_TITLE_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
	        			VALID_TITLE_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_HFA_RECORD_ALTERNATE_ITEM_NUMBER = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_TITLE_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + ALT_ITEM_NUMBER + "</col>" +
	        		"</row>" +
	        "</HFA-data>";

	public static final String VALID_TITLE_AND_PREFIX_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_PREFIX_FIELD +
		    			VALID_TITLE_FIELD +
		        		VALID_ITEM_NUMBER_FIELD +
	        		"</row>" +
	        "</HFA-data>";

	public static final String INVALID_PREFIX_ONLY_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_PREFIX_FIELD +
		        		VALID_ITEM_NUMBER_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FULL_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_PREFIX_FIELD +
	        			VALID_TITLE_FIELD +
		        		VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COLLECTION.getColumnAttributeText() + "'>" + COLLECTION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.EDITOR.getColumnAttributeText() + "'>" + EDITOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCER.getColumnAttributeText() + "'>" + PRODUCERS + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.SUBTITLES_LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.INTERTITLES_LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.SYNOPSIS.getColumnAttributeText() + "'>" + SYNOPSIS + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.HFA_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.RUNNING_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
		        		CAST_MEMBERS_HFA_FIELD +
		        		YEAR_OF_RELEASE_FIELD +
		        		COUNTRY1_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_GENRES = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + FICTION_GENRE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.SHORT.getColumnAttributeText() + "\">" + SHORT_GENRE + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_GENRE_WITH_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + ENTRY_NOT_IN_CONCORDANCE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.SHORT.getColumnAttributeText() + "\">" + FICTION_GENRE + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_FILM_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + TWO_LINE_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TELEVISION_GENRES = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TELEVISION_GENRE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.CLASSIFICATION.getColumnAttributeText() + "\">" + TELEVISION_SERIAL_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TELEVISION_GENRES_WITH_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TELEVISION_GENRE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.FICTION.getColumnAttributeText() + "'>" + ENTRY_NOT_IN_CONCORDANCE + "</col>" +
		        		"<col column=\"" + HfaRecord.ColumnAttributeText.CLASSIFICATION.getColumnAttributeText() + "\">" + TELEVISION_SERIAL_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TOPIC_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + HOLIDAY_TOPIC + "</col>" + // must use double quote since SHORT_GENRE value contains a single quote
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_TOPIC_GENRE = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TWO_LINE_TOPIC_GENRE + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_TWO_LINE_TOPIC_GENRE_KEYWORD = 
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.GENRE.getColumnAttributeText() + "'>" + TWO_LINE_TOPIC_GENRE_AND_KEYWORD + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_FILM_DIRECTOR_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>" +
		        		VALID_ITEM_NUMBER_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_PRODUCTION_COMPANY_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
		        		VALID_TITLE_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCTION_COMPANY.getColumnAttributeText() + "'>" + PRODUCTION_COMPANIES + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_COUNTRIES_AND_YEAR_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
		        		VALID_TITLE_FIELD +
		        		YEAR_OF_RELEASE_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COUNTRY.getColumnAttributeText() + "'>" + COUNTRIES + "</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_YEAR_ONLY_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
		        		VALID_TITLE_FIELD +
		        		YEAR_OF_RELEASE_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_COUNTRY_ONLY_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
		        		VALID_TITLE_FIELD +
		        		COUNTRY1_FIELD +
	        		"</row>" +
	        "</HFA-data>";
	
	public static final String VALID_PRICIPAL_CAST_RECORD =
	        "<HFA-data>" +
	        		"<row>" +
	        			VALID_ITEM_NUMBER_FIELD +
	        			VALID_TITLE_FIELD +
		        		CAST_MEMBERS_HFA_FIELD +
	        		"</row>" +
	        "</HFA-data>";

	public static final String VALID_ITEM_HFA_RECORD = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
	        			VALID_TITLE_FIELD +
	        			LOAN1_FIELD +
	        		"</row>" +
	        		"<col column='" + HfaRecord.ColumnAttributeText.HFA_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
	        "</HFA-data>";

	public static final String VALID_ITEM_HFA_RECORD_2_LOANS = 
	        "<HFA-data>" +
	        		"<row>" +
		        		VALID_ITEM_NUMBER_FIELD +
	        			VALID_TITLE_FIELD +
	        			LOAN1_FIELD +
	        			LOAN2_FIELD +
	        		"</row>" +
	        "</HFA-data>";
}
