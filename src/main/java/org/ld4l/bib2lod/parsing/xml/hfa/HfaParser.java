/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml.hfa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.w3c.dom.Element;

/**
 * Parses a HFA XML record.
 */
public class HfaParser extends XmlParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
              
    private static final String RECORD_TAG_NAME = "row";   

	public HfaParser() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.parsing.xml.XmlParser#getRecordTagName()
	 */
	@Override
	protected String getRecordTagName() {
		return RECORD_TAG_NAME;
	}

	@Override
	protected XmlRecord createRecord(Element recordElement) throws RecordException {
		return new HfaRecord(recordElement);
	}

}
