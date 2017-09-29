/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    	AKA_TITLE("aka Title"),
    	ALTERNATE_TITLE("Alternate Title"),
    	ALSO_KNOWN_AS_TITLE("Also known as Title"),
    	ASPECT_RATIO("Aspect Ratio"),
    	CAUTIONS("Cautions:"),
    	CLASSIFICATION("Classification"),
    	COLLECTION("Collection Title"),
    	COLOR("B/W-Color"),
    	CONDITION_DEFECTS("Condition Defects"),
    	COUNTRY("Country"),
    	DIRECTOR("Director"),
    	DONATED_BY("Donated By:"),
    	DP_CINEMATOGRAPHER("DP/Cinematographer"),
    	EDITOR("Editor"),
    	ELEMENT("Element"),
    	ENGLISH_TITLE("English Titles"),
    	FICTION("Fiction"),
    	GENRE("Genre"),
    	HFA_FORMAT("HFA Format(s)"),
    	HFA_TIME("HFA Time"),
    	INTERTITLES_LANGUAGE("Intertitle Language"),
    	ITEM_NUMBER("Item number"),
    	LANGUAGE("Language"),
    	MUSIC("Music"),
    	NON_FICTION("Non Fiction"),
    	ORIGINAL_FORMAT("Original Format"),
    	ORIGINAL_TITLE("Original Title"),
    	PREFIX("prefix"),
    	PRINCIPAL_CAST("Principal Cast"),
    	PRINT_CONDITION("Print Condition"),
    	PRODUCER("Producer"),
    	PRODUCTION_COMPANY("Production Company"),
    	RUNNING_TIME("Running Time {Length}"),
    	SCRIPT("Script"),
    	SHORT("short'"), // yes, this is the column name with the single quote
    	SOUND_ASPECTS("Sound Aspect(s)"),
    	SOUNDTRACK_TYPE("Soundtrack Type"),
    	SUBTITLES_LANGUAGE("Subtitle Language"),
    	SYNOPSIS("Synopsis"),
    	TITLE("Original Titles"),
    	TITLE_ON_PRINT("Title on Print"),
    	YEAR_OF_RELEASE("Year of Release");
        
        private final String attrText;
        
        private ColumnAttributeText(String tagName) {
            this.attrText = tagName;
        }
        
        public String getColumnAttributeText() {
        	return attrText;
        }
    }
    
    private enum Field {
    	
        LOAN("loan");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }
    
    private Map<ColumnAttributeText, HfaTextField> columnToField;
    private List<HfaLoan> loanFields;
    
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
		
		loanFields = buildLoanFields(record);
		
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
    
    /**
     * Builds this Record's loan fields from the HFA input.
     * Returns an empty List if there are no control fields.
     * @throws RecordException 
     */
    private final List<HfaLoan> buildLoanFields(Element record) 
            throws RecordException {
        
        List<HfaLoan> loanFields = 
                new ArrayList<>();

        NodeList loanNodes = record.getElementsByTagName(Field.LOAN.tagName);

        for (int i = 0; i < loanNodes.getLength(); i++) {
            Element field = (Element) loanNodes.item(i);
            loanFields.add(new HfaLoan(field));
        }  
        
        return loanFields;
    }
    
    /**
     * Returns HfaLoan fields or empty list if none.
     */
    public List<HfaLoan> getHfaLoanFields() {
    	return loanFields;
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
		
		if ( !loanFields.isEmpty()) {
			builder.append(loanFields);
		}
		builder.append("]");
		return builder.toString();
	}

}
