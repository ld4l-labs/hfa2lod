/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import java.util.HashMap;
import java.util.Map;

import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a HFA XML record.
 */
public class HfaRecord extends BaseXmlRecord {
    
	/*
	 * These are the text values of the 'column' attribute of the column elements.
	 * (listed alphabetically)
	 */
    public enum ColumnAttributeText {
    	CLASSIFICATION("Classification"),
    	COLLECTION("Collection Title"),
    	COUNTRY("Country"),
    	DIRECTOR("Director"),
    	DP_CINEMATOGRAPHER("DP/Cinematographer"),
    	EDITOR("Editor"),
    	FICTION("Fiction"),
    	GENRE("Genre"),
    	INTERTITLES_LANGUAGE("Intertitle Language"),
    	ITEM_NUMBER("Item number"),
    	LANGUAGE("Language"),
    	NON_FICTION("Non Fiction"),
    	PREFIX("prefix"),
    	PRINCIPAL_CAST("Principal Cast"),
    	PRODUCER("Producer"),
    	PRODUCTION_COMPANY("Production Company"),
    	SCRIPT("Script"),
    	SHORT("short'"), // yes, this is the column name with the single quote
    	SUBTITLES_LANGUAGE("Subtitle Language"),
    	TITLE("Original Titles"),
    	YEAR_OF_RELEASE("Year of Release");
        
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
	 * 
	 * @param record - The top-most FGDC XML element.
	 */
	public HfaRecord(Element record) throws RecordException {
		super(record);

		columnToField = new HashMap<>();
		
		for (ColumnAttributeText attr : ColumnAttributeText.values()) {
			HfaTextField field = buildField(record, attr);
			if (field != null) {
				columnToField.put(attr, field);
			}
		}
		isValid();
	}
	
	public HfaTextField getField(ColumnAttributeText attr) {
		return columnToField.get(attr);
	}
	
	private void isValid() throws RecordFieldException {
		if (columnToField.isEmpty()) {
			throw new RecordFieldException("No Data in HfaRecord.");
		}
		if (columnToField.get(ColumnAttributeText.ITEM_NUMBER) == null ||
				columnToField.get(ColumnAttributeText.ITEM_NUMBER).getTextValue().isEmpty()) {
			throw new RecordFieldException("HfaRecord has no " + ColumnAttributeText.ITEM_NUMBER.getColumnAttributeText());
		}
	}
	
	private HfaTextField buildField(Element record, ColumnAttributeText field) throws RecordException {
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
