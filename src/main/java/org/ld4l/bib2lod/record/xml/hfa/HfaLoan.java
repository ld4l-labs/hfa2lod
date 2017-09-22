/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import java.util.HashMap;
import java.util.Map;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents row in the FilmLoanHFA FileMaker Pro database.
 */
public class HfaLoan extends BaseHfaField implements RecordField {
    
	/*
	 * These are the text values of the 'column' attribute of the column elements.
	 * (listed alphabetically)
	 */
    public enum ColumnAttributeText {
    	CITY("City"),
    	COMPANY_NAME("Company Name"),
    	COUNTRY("Country"),
    	DATE_OF_LOAN("Date Sent"),
    	OUTSIDE_BORROWER("Outside borrower"),
    	PLAY_DATE("Play date"),
    	STATE_PROVINCE("State / Province");
        
        private final String attrText;
        
        private ColumnAttributeText(String tagName) {
            this.attrText = tagName;
        }
        
        public String getColumnAttributeText() {
        	return attrText;
        }
    }
    
    private Map<ColumnAttributeText, HfaTextField> columnToField;
    
    private static final String COLUMN_ELEMENT_NAME = "col";
    private static final String COLUMN_ATTRIBUTE_NAME = "column";

    /**
     * Constructor
     */
    public HfaLoan(Element element) throws RecordException {
        super(element);

		columnToField = new HashMap<>();
		for (ColumnAttributeText attr : ColumnAttributeText.values()) {
			HfaTextField field = buildField(element, attr);
			if (field != null) {
				columnToField.put(attr, field);
			}
		}
    }
	
	public HfaTextField getField(ColumnAttributeText attr) {
		return columnToField.get(attr);
	}
	
	private final HfaTextField buildField(Element record, ColumnAttributeText field) throws RecordException {
		NodeList columnNodes = 
				record.getElementsByTagName(COLUMN_ELEMENT_NAME);
        if (columnNodes.getLength() == 0) {
            return null;
        }
        
        for (int i = 0; i < columnNodes.getLength(); i++) {
        	Element columnElement = (Element) columnNodes.item(i);
        	String attrVal = columnElement.getAttribute(COLUMN_ATTRIBUTE_NAME);
        	if (field.getColumnAttributeText().equals(attrVal)) {
        		// found matching attribute text but if no text in element then return null
                Node firstChild = columnElement.getFirstChild();
                if (firstChild == null) {
                    return null;
                }
                if (firstChild.getNodeType() == Node.TEXT_NODE || firstChild.getNodeType() == Node.CDATA_SECTION_NODE) {
                	return new HfaTextField(columnElement, field.getColumnAttributeText());
                }
        	}
        }
        return null; // no match found
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append(" [");
		
		boolean first = true;
		for (ColumnAttributeText column : columnToField.keySet()) {
			if (!first) {
				builder.append(',');
			} else {
				first = false;
			}
			builder.append(column.getColumnAttributeText());
			builder.append('=');
			builder.append(columnToField.get(column).getTextValue());
		}
		builder.append("]");
		return builder.toString();
	}

}
