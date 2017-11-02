package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum HfaHistoryType implements Type {
    
    /* List in alpha order */
    CUSTODIAL_HISTORY(HfaNamespace.BIB_PROV, "CustodialHistory", "Custodial History");
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    /**
     * Constructor
     */
    HfaHistoryType(Namespace namespace, String localName, String label) {
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
		return CUSTODIAL_HISTORY;
	}
}
