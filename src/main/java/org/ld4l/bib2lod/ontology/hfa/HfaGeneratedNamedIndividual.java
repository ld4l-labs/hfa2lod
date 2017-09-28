/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Generates named individuals used in HFA data model.
 */
public class HfaGeneratedNamedIndividual implements NamedIndividual {
        
    private String uri;
    private Resource resource;
    
    /**
     * Constructor
     * @throws java.lang.NullPointerException if namespace is <code>null</code>.
     */
    public HfaGeneratedNamedIndividual(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.resource = ResourceFactory.createResource(uri);
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
        return null;
    }

}
