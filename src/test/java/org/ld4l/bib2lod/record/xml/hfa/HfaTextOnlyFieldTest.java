/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.HfaTestData;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests the HfaTextOnlyField.
 */
public class HfaTextOnlyFieldTest extends AbstractTestClass {

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void validTitleField() throws Exception {
    	HfaTextField hfaField = buildHfaFieldFromString(HfaTestData.VALID_TITLE, HfaRecord.ColumnAttributeText.TITLE);
    	String text = hfaField.getTextValue();
    	Assert.assertNotNull(text);
    	Assert.assertEquals(HfaTestData.TITLE_TEXT, text);
    	String attrVal = hfaField.getFieldName();
    	Assert.assertNotNull(attrVal);
    	Assert.assertEquals(HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText(), attrVal);
    }
    
    @Test
    public void missingText_invalid() throws Exception {
       	expectException(RecordFieldException.class, "text value is null");
       	buildHfaFieldFromString(HfaTestData.INVALID_TITLE_NO_TEXT, HfaRecord.ColumnAttributeText.TITLE);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private HfaTextField buildHfaFieldFromString(String xmlString, HfaRecord.ColumnAttributeText attrVal) 
            throws RecordException {
    	
    	Element element = XmlTestUtils.buildElementFromString(xmlString);
    	return new HfaTextField(element, attrVal.getColumnAttributeText());
    }
}
