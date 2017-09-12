package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lMotivationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates named individuals used in HFA data model.
 */
public enum HfaNamedIndividual implements NamedIndividual {
    
    /* Web Annotation Motivation 
     */
	LISTING_CREDITS(Ld4lNamespace.BIBFRAME, "listingCredits", Ld4lMotivationType.MOTIVATION);
    
    private String uri;
    private Resource resource;
    private Type type;
    
    /**
     * Constructor
     */
    HfaNamedIndividual(Namespace namespace, String localName, Type type) {
        this.uri = namespace.uri() + localName;
        this.resource = ResourceFactory.createResource(uri);
        this.type = type;
    }
    
    HfaNamedIndividual(Namespace namespace, String localName) {
        this(namespace,  localName, null);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource resource() {
        return resource;
    } 
    
    @Override
    public Type type() {
        return type;
    }

}
