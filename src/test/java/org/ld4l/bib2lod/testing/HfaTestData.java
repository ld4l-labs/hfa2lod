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
	public static final String ALT_TITLE_TEXT = "Alt Title";
	public static final String ALSO_KNOWN_AS_TITLE_TEXT = "Also Known Title";
	public static final String AKA_TITLE_TEXT = "AKA Title";
	public static final String ORIGINAL_TITLE_TEXT = "Original Title";
	public static final String ENGLISH_TITLE_TEXT = "English Title";
	public static final String TITLE_ON_PRINT_TEXT = "Title on print";
	public static final String ANOTHER_TITLE_TEXT = "Title";
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
	public static final String COUNTRY1 = "Costa Rica";
	public static final String COUNTRY2 = "Moldova";
	public static final String COUNTRY3 = "Bulgaria";
	public static final String COUNTRY4 = "Peoples Republic of Cambridge";
	public static final String YEAR_OF_RELEASE = "1999";
	public static final String COUNTRIES = COUNTRY1 + " /" + COUNTRY2 + " , " + COUNTRY3 + " | " + COUNTRY4;
	public static final String CAST1 = " Person 1 "; // yes, wanted to pad with spaces
	public static final String CAST2 = "Person 2";
	public static final String CAST3 = "Person 3";
	public static final String CAST_MEMBERS = CAST1 + "," + CAST2 + " , " + CAST3;
	public static final String LANGUAGE = "English ";
	public static final String LANGUAGE_UNKNOWN = "[unknown]";
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
	public static final String COLOR_BW = "B/W";
	public static final String DEFECT = "Poor sound";
	public static final String CAUTION = "incomplete print";
	public static final String PRINT_CONDITION = "Excellent"; // intentionally capitalized whereas lower case in concordance file
	public static final String MUSIC = "John Q. Composer";
	public static final String DONATED_BY = " Mary O. Benefactor "; // yes, wanted to pad with spaces
	public static final String ASPECT_RATIO = "1.66:1";
	public static final String SOUND_ASPECT = "Dolby A";
	public static final String SOUNDTRACK_TYPE = "Sound";
	public static final String HFA_FORMAT = "Betamax";
	public static final String ELEMENT = "Outtakes";
	public static final String ORIGINAL_FORMAT = "70mm.";

	public static final String VALID_ITEM_NUMBER_FIELD =
			"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>" + ITEM_NUMBER + "</col>";

	public static final String VALID_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + TITLE_TEXT + "</col>";
	
	public static final String VALID_PREFIX_FIELD =
			"<col column='" + HfaRecord.ColumnAttributeText.PREFIX.getColumnAttributeText() + "'>" + PREFIX_TEXT + "</col>";
	
	public static final String INVALID_TITLE_NO_TEXT_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + "</col>";

	public static final String ALT_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.ALTERNATE_TITLE.getColumnAttributeText() + "'>" + ALT_TITLE_TEXT + "</col>";

	public static final String ALSO_KNOWN_AS_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.ALSO_KNOWN_AS_TITLE.getColumnAttributeText() + "'>" + ALSO_KNOWN_AS_TITLE_TEXT + "</col>";

	public static final String AKA_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.AKA_TITLE.getColumnAttributeText() + "'>" + AKA_TITLE_TEXT + "</col>";

	public static final String ORIGINAL_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.ORIGINAL_TITLE.getColumnAttributeText() + "'>" + ORIGINAL_TITLE_TEXT + "</col>";

	public static final String ENGLISH_TITLE_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.ENGLISH_TITLE.getColumnAttributeText() + "'>" + ENGLISH_TITLE_TEXT + "</col>";

	public static final String TITLE_ON_PRINT_FIELD = 
			"<col column='" + HfaRecord.ColumnAttributeText.TITLE_ON_PRINT.getColumnAttributeText() + "'>" + TITLE_ON_PRINT_TEXT + "</col>";
	
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

	public static final String HFA_FORMAT_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.HFA_FORMAT.getColumnAttributeText() + "'>" + HFA_FORMAT + "</col>";

	public static final String ELEMENT_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.ELEMENT.getColumnAttributeText() + "'>" + ELEMENT + "</col>";

	public static final String ORIGINAL_FORMAT_FIELD =
    		"<col column='" + HfaRecord.ColumnAttributeText.ORIGINAL_FORMAT.getColumnAttributeText() + "'>" + ORIGINAL_FORMAT + "</col>";

	public static final String LOAN1_FIELD =
			"<loan>" +
					"<col column='" + HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER.getColumnAttributeText() + "'>" + OUTSIDE_BORROWER1 + "</col>" +
					"<col column='" + HfaLoan.ColumnAttributeText.COMPANY_NAME.getColumnAttributeText() + "'>" + COMPANY1_NAME + "</col>" +
//					"<col column='" + HfaLoan.ColumnAttributeText.CITY.getColumnAttributeText() + "'>" + CITY + "</col>" +
//					"<col column='" + HfaLoan.ColumnAttributeText.STATE_PROVINCE.getColumnAttributeText() + "'>" + STATE + "</col>" +
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
	        			AKA_TITLE_FIELD + 
	        			ALSO_KNOWN_AS_TITLE_FIELD +
	        			ENGLISH_TITLE_FIELD +
	        			ALT_TITLE_FIELD +
	        			ORIGINAL_TITLE_FIELD +
	        			TITLE_ON_PRINT_FIELD +
		        		VALID_ITEM_NUMBER_FIELD +
		        		ORIGINAL_FORMAT_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DIRECTOR.getColumnAttributeText() + "'>" + FILM_DIRECTOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COLLECTION.getColumnAttributeText() + "'>" + COLLECTION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.EDITOR.getColumnAttributeText() + "'>" + EDITOR + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.MUSIC.getColumnAttributeText() + "'>" + MUSIC + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRODUCER.getColumnAttributeText() + "'>" + PRODUCERS + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.SUBTITLES_LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.INTERTITLES_LANGUAGE.getColumnAttributeText() + "'>" + LANGUAGE + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.SYNOPSIS.getColumnAttributeText() + "'>" + SYNOPSIS + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.HFA_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.RUNNING_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COLOR.getColumnAttributeText() + "'>" + COLOR_BW + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.ASPECT_RATIO.getColumnAttributeText() + "'>" + ASPECT_RATIO + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.SOUND_ASPECTS.getColumnAttributeText() + "'>" + SOUND_ASPECT + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.SOUNDTRACK_TYPE.getColumnAttributeText() + "'>" + SOUNDTRACK_TYPE + "</col>" +		        		
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
	        			VALID_PREFIX_FIELD +
	        			VALID_TITLE_FIELD +
	        			LOAN1_FIELD +
	        			HFA_FORMAT_FIELD +
	        			ELEMENT_FIELD +
		        		"<col column='" + HfaRecord.ColumnAttributeText.COLOR.getColumnAttributeText() + "'>" + COLOR_BW + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.CONDITION_DEFECTS.getColumnAttributeText() + "'>" + DEFECT + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.CAUTIONS.getColumnAttributeText() + "'>" + CAUTION + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.PRINT_CONDITION.getColumnAttributeText() + "'>" + PRINT_CONDITION + "</col>" +		        		
		        		"<col column='" + HfaRecord.ColumnAttributeText.HFA_TIME.getColumnAttributeText() + "'>" + DURATION + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DONATED_BY.getColumnAttributeText() + "'>" + DONATED_BY + "</col>" +
	        		"</row>" +
	        "</HFA-data>";

	public static final String VALID_SECOND_ITEM_HFA_RECORD_SAME_DONOR = 
	        "<HFA-data>" +
	        		"<row>" +
	        			"<col column='" + HfaRecord.ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText() + "'>5678</col>" +
	        			"<col column='" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "'>" + ANOTHER_TITLE_TEXT + "</col>" +
		        		"<col column='" + HfaRecord.ColumnAttributeText.DONATED_BY.getColumnAttributeText() + "'>" + DONATED_BY + "</col>" +
	        		"</row>" +
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
