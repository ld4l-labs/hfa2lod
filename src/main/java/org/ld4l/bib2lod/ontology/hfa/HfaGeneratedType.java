/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * These are types, rather than being pre-defined enums, are generated from values
 * pulled externally, perhaps from concordances.
 */
public class HfaGeneratedType implements Type {

    private String uri;
    private Resource ontClass;

    /**
	 * Constructor
	 */
	public HfaGeneratedType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.ontology.Type#uri()
	 */
	@Override
	public String uri() {
        return uri;
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.ontology.Type#ontClass()
	 */
	@Override
	public Resource ontClass() {
        return ontClass;
	}

	/* (non-Javadoc)
	 * @see org.ld4l.bib2lod.ontology.Type#superclass()
	 */
	@Override
	public Type superclass() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HfaGeneratedType other = (HfaGeneratedType) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
