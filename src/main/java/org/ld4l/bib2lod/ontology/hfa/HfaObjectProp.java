package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

/**
 * Enumerates the object properties used in the HFA BIBFRAME 2 extension and
 * application profile.
 */
public enum HfaObjectProp implements ObjectProp {
    
    /* List in alpha order */
	HAS_INTERTITLE_LANGUAGE(HfaNamespace.MOVING_IMAGE, "intertitleLanguage"),
	HAS_PART(Ld4lNamespace.BIBFRAME, "hasPart"),
    HAS_PUBLIC_IDENTITY(HfaNamespace.ISNI, "hasPublicIdentity"),
	HAS_SUBTITLE_LANGUAGE(HfaNamespace.MOVING_IMAGE, "subtitleLanguage"),
    HAS_WEB_PAGE(Ld4lNamespace.FOAF, "page"),
	IS_PART_OF(Ld4lNamespace.BIBFRAME, "isPartOf");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    HfaObjectProp(Namespace namespace, String localName) {
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
