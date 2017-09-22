package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum HfaCollectionType implements Type {
    
    /* List in alpha order */
    COLLECTION(Ld4lNamespace.BIBFRAME, "Collection");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    HfaCollectionType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri); 
    }    

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    }

	@Override
	public Type superclass() {
		return COLLECTION;
	}
}
