package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum HfaActivityType implements Type {
    
    /* List in alpha order */
	BORROWER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "BorrowerActivity", "Borrower"),
	CINEMATOGRAPHER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "CinematographerActivity", "Cinematographer"),
    DIRECTOR_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "FilmDirectorActivity", "Film Director"),
    EDITOR_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "FilmEditorActivity", "Film Editor"),
    LENDER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "LenderActivity", "Lender"),
    MUSICIAN_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "MusicianActivity", "Musician"),
    PRODUCER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "ProducerActivity", "Producer"),
    // FIXME: The following namespace might not be correct.
    PRODUCTION_COMPANY_ACTIVITY(HfaNamespace.MOVING_IMAGE, "ProductionCompanyActivity", "Production"),
    PROVIDER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "ProviderActivity", "Release"),
    SCREENWRITER_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "ScreenwriterActivity", "Screenwriter");
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    /**
     * Constructor
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
    
    @Override
    public Type superclass() {
        return Ld4lActivityType.defaultType();
    }
}
