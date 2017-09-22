package org.ld4l.bib2lod.record.xml.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;

/**
 * Tests class HfaRecord.
 */
public class HfaRecordTest extends AbstractHfaTest {


    private static final String NO_VALUE = "<HFA-data/>";
    
    private static final String NO_ROW = 
            "<HFA-data></HFA-data>";
    
    private static final String NO_COLUMN = 
            "<HFA-data>" +
            		"<row />" +
            "</HFA-data>";
    
	private static final String NO_VALID_ATTR_TEXT = 
	        "<HFA-data>" +
	        		"<row>" +
	        			"<col column='attrText'>Some text</col>" +
	        		"</row>" +
	        "</HFA-data>";
	
	private static final String NO_ELEMENT_TEXT = 
			"<HFA-data>" +
					"<row>" +
					"<col column='prefix' />" +
					"</row>" +
					"</HFA-data>";
    
	private static final String NO_HFA_NUMBER = 
	        "<HFA-data>" +
	        		"<row>" +
	        			"<col column='Original Titles'>Some title</col>" +
	        		"</row>" +
	        "</HFA-data>";
    
	private static final String VALID_record = 
	        "<HFA-data>" +
	        		"<row>" +
	        			"<col column='Original Titles'>Some title</col>" +
	        			"<col column='Item number'>123</col>" +
	        		"</row>" +
	        "</HFA-data>";
    
	private static final String VALID_record_with_loans = 
	        "<HFA-data>" +
	        		"<row>" +
	        			"<col column='Original Titles'>Some title</col>" +
	        			"<col column='Item number'>123</col>" +
	        			"<loan>" +
	        				"<col column='Outside borrower'>The Borrower</col>" +
	        				"<col column='State / Province'>Massachusetts</col>" +
	        				"<col column='Country'>USA</col>" +
	        			"</loan>" +
	        			"<loan>" +
	        				"<col column='Outside borrower'>Another Borrower</col>" +
	        				"<col column='State / Province'>Maine</col>" +
	        			"</loan>" +
	        		"</row>" +
	        "</HFA-data>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noData_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "No Data in HfaRecord.");
    	buildHfaRecordFromString(NO_VALUE);
    }
    
    @Test
    public void noRow_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "No Data in HfaRecord.");
    	buildHfaRecordFromString(NO_ROW);
    }
    
    @Test
    public void noColumn_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "No Data in HfaRecord.");
    	buildHfaRecordFromString(NO_COLUMN);
    }
    
    @Test
    public void noExpectedAttributeText_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "No Data in HfaRecord.");
    	buildHfaRecordFromString(NO_VALID_ATTR_TEXT);
    }
    
    @Test
    public void noElementText_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "No Data in HfaRecord.");
    	buildHfaRecordFromString(NO_ELEMENT_TEXT);
    }
    
    @Test
    public void noExpectedHfaRecord_Invalid() throws Exception {
    	expectException(RecordFieldException.class, "HfaRecord has no " + ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText());
    	buildHfaRecordFromString(NO_HFA_NUMBER);
    }
    
    @Test
    public void validRecord_Valid() throws Exception {
        // No exception
    	HfaRecord record = buildHfaRecordFromString(VALID_record);
    	
    	HfaTextField hfaNumber = record.getField(ColumnAttributeText.ITEM_NUMBER);
    	Assert.assertNotNull(hfaNumber);
    	Assert.assertEquals("123", hfaNumber.getTextValue());

    	HfaTextField field = record.getField(ColumnAttributeText.TITLE);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("Some title", field.getTextValue());
    	
    	field = record.getField(ColumnAttributeText.PREFIX);
    	Assert.assertNull(field);
    }
    
    @Test
    public void validRecordWithLoan_Valid() throws Exception {
    	HfaRecord record = buildHfaRecordFromString(VALID_record_with_loans);
    	
    	List<HfaLoan> loans = record.getHfaLoanFields();
    	Assert.assertNotNull(loans);
    	Assert.assertEquals(2, loans.size());
    	
    	HfaLoan loan = loans.get(0);
    	HfaTextField field = loan.getField(HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("The Borrower", field.getTextValue());
    	field = loan.getField(HfaLoan.ColumnAttributeText.STATE_PROVINCE);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("Massachusetts", field.getTextValue());
    	field = loan.getField(HfaLoan.ColumnAttributeText.COUNTRY);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("USA", field.getTextValue());
    	
    	loan = loans.get(1);
    	field = loan.getField(HfaLoan.ColumnAttributeText.OUTSIDE_BORROWER);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("Another Borrower", field.getTextValue());
    	field = loan.getField(HfaLoan.ColumnAttributeText.STATE_PROVINCE);
    	Assert.assertNotNull(field);
    	Assert.assertEquals("Maine", field.getTextValue());
    	field = loan.getField(HfaLoan.ColumnAttributeText.COUNTRY);
    	Assert.assertNull(field);
    }
}
