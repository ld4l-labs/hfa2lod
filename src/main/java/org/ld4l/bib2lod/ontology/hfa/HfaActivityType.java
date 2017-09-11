package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum HfaActivityType implements Type {
    
    /* List in alpha order */
	CINEMATOGRAPHER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "CinematographerActivity", "Cinematographer"),
    DIRECTOR_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "FilmDirectorActivity", "Film Director"),
    EDITOR_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "FilmEditorActivity", "Film Editor"),
    PRODUCER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "ProducerActivity", "Producer"),
    // FIXME: The following namespace might not be correct.
    PRODUCTION_COMPANY_ACTIVITY(HarvardNamespace.MOVING_IMAGE, "ProductionCompanyActivity", "Production"),
    SCREENWRITER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "ScreenwriterActivity", "Screenwriter");
    
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
