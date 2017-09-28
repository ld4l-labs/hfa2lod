/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.hfa;

import org.junit.Assert;
import org.junit.Test;

public class HfaNamespaceTest {

	@Test
	public void namespaceForValidPrefix() {
		HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(HfaNamespace.MOVING_IMAGE.prefix());
		Assert.assertNotNull(ns);
		Assert.assertEquals(HfaNamespace.MOVING_IMAGE.prefix(), ns.prefix());
	}

	@Test
	public void nullForInvalidPrefix() {
		HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix("bad");
		Assert.assertNull(ns);
	}

}
