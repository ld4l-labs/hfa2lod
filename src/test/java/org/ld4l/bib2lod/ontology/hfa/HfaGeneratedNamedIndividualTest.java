/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.hfa;

import org.junit.Assert;
import org.junit.Test;

public class HfaGeneratedNamedIndividualTest {

	@Test
	public void namespaceForPrefix_Valid() {
		
		String localName = "localName";
		HfaGeneratedNamedIndividual namedIndividual = new HfaGeneratedNamedIndividual(HfaNamespace.MOVING_IMAGE, localName);
		Assert.assertNotNull(namedIndividual);
		Assert.assertEquals(HfaNamespace.MOVING_IMAGE.uri() + localName, namedIndividual.uri());
		Assert.assertNotNull(namedIndividual.resource());
		Assert.assertEquals(HfaNamespace.MOVING_IMAGE.uri() + localName, namedIndividual.resource().toString());
		Assert.assertNull(namedIndividual.type());
	}
}
