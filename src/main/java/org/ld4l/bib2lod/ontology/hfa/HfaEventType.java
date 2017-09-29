package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum HfaEventType implements Type {
    
    /* List in alpha order */
    // FIXME: The following namespace might not be correct.
    GIFT_EVENT(HfaNamespace.MOVING_IMAGE, "GiftEvent", "Gift Event"),
    ITEM_EVENT(HfaNamespace.MOVING_IMAGE, "ItemEvent", "Item Event"),
    LOAN_EVENT(HfaNamespace.MOVING_IMAGE, "LoanEvent", "Loan"),
    // FIXME: The following namespace might not be correct.
    EXHIBITION_EVENT(HfaNamespace.MOVING_IMAGE, "ExhibitionEvent", "Screening");
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    /**
     * Constructor
     */
    HfaEventType(Namespace namespace, String localName, String label) {
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
		return LOAN_EVENT;
	}
}
