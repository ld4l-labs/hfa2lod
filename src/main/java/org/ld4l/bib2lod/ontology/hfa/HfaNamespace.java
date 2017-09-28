/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.hfa;

import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Enumerates the ontology namespaces used in the Harvard Film Archive converter extension and
 * application profile.
 */
public enum HfaNamespace implements Namespace {

    /* Datasets/controlled vocabularies */
    /* List in alpha order */
	// FIXME: The following namespace is just a placeholder
	FIX_ME("http://localhost/fixme/", "ex"),
	ISNI("http://isni.org/isni/", "isni"),
	// FIXME: The following namespace URI is just a placeholder
	MOVING_IMAGE("http://localhost/movingimage/", "mi");
    
    private final String uri;
    private final String prefix;
    
    HfaNamespace(String uri, String prefix) {
        this.uri = uri;
        this.prefix = prefix;
    }
    
    HfaNamespace(String uri) {
        this(uri, null);
    }
    
    public String uri() {
        return this.uri;
    }
    
    public String prefix() {
        return this.prefix;
    }
    
    public static HfaNamespace getHfaNamespaceByPrefix(String prefix) {
    	
    	HfaNamespace value = null;
    	for (HfaNamespace ns : HfaNamespace.values()) {
    		if (ns.prefix != null && ns.prefix.equals(prefix)) {
    			value = ns;
    		}
    	}
    	return value;
    }
}
