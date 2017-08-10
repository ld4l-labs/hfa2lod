package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates the datatype properties used in the HFA BIBFRAME 2 extension and
 * application profile.
 */
public enum HfaDatatypeProp implements DatatypeProp {
    
    /* List in alpha order */
    KEYWORDS(Ld4lNamespace.SCHEMA, "keywords");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    HfaDatatypeProp(Ld4lNamespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }
    
    @Override
    public Property property() {
        return property;
    }

}
