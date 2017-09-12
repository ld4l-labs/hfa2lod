/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.testing;

import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

abstract public class AbstractHfaTest extends AbstractTestClass {


    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
	
    protected HfaRecord buildHfaRecordFromString(String s) 
            throws RecordException {
    	Element element = XmlTestUtils.buildElementFromString(s);
    	return new HfaRecord(element);
    }

}
