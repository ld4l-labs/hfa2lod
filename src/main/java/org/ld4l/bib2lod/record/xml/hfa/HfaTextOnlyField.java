/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.w3c.dom.Element;

/**
 * Represents a generic text-only field in a HFA record.
 */
public class HfaTextOnlyField extends HfaField {
	
	private String fieldName = "";

	/**
	 * Constructor without giving this generic field a specific name.
	 */
	public HfaTextOnlyField(Element element) throws RecordException {
		super(element);
		isValid();
	}

	/**
	 * Constructor giving this generic field a specific name.
	 */
	public HfaTextOnlyField(Element element, String fieldName) throws RecordException {
		super(element);
		if (fieldName != null && !fieldName.isEmpty()) {
			this.fieldName = fieldName;
		}
		isValid();
	}

	/**
	 * Represents the input XML element name creating this record.
	 * 
	 * @return The element name used for creating the text value of this record.
	 */
	public String getFieldName() {
		return fieldName;
	}

	private void isValid() throws RecordFieldException {
        if (textValue == null) {
            throw new RecordFieldException("text value is null");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("text value is empty");
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		if (fieldName.isEmpty()) {
			builder.append(" [textValue=");
		} else {
			builder.append(" [fieldName=");
			builder.append(fieldName);
			builder.append(", textValue=");
		}
		builder.append(textValue);
		builder.append("]");
		return builder.toString();
	}
	
}
