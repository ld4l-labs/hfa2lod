package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum HfaActivityType implements Type {
    
    /* List in alpha order */
    FILM_DIRECTOR_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "FilmDirectorActivity", "Film Director");
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    /**
     * Constructor
     * @param label TODO
     */
    HfaActivityType(Namespace namespace, String localName, String label) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri); 
        this.label = label;
    }    

    @Override
    public String uri() {
        return uri;
    }
    
    public String label() {
    	return label;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    }
}
