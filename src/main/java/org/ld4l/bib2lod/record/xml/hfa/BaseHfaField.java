/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.hfa;

import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.w3c.dom.Element;

/**
 * Represents a field in a HFA input record.
 */
public abstract class BaseHfaField extends BaseXmlElement {

	/**
	 * Constructor
	 * 
	 * @param element
	 */
	public BaseHfaField(Element element) {
		super(element);
	}

}
