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
	BIB_PROV("http://cho.bibliotek-o.org/0.1/ontology/", "cho"),
	ISNI("http://isni.org/isni/", "isni"),
	MOVING_IMAGE("http://ontology.library.harvard.edu/film/", "mi"),
	SOUND_ASPECT("http://www.rdaregistry.info/termList/AspectRatio/", "rdaspc"),
	SOUND_CONTENT("http://www.rdaregistry.info/termList/soundCont/", "rdasco");
    
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
