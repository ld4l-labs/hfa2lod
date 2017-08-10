/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;


/**
 * Builds an external reference from a concordance file entry.
 */
public interface ConcordanceReferenceBuilder {
    
    /**
     * Signals a problem when building an external reference
     */
    public static class ConcordanceReferenceException extends Exception {
        private static final long serialVersionUID = 1L;

        public ConcordanceReferenceException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConcordanceReferenceException(String message) {
            super(message);
        }

        public ConcordanceReferenceException(Throwable cause) {
            super(cause);
        }
    } 
    
    /**
     * Builds an Entity, including its dependent Entities (e.g., Identifiers
     * and Titles of Works and Instances).
     * @throws EntityBuilderException 
     */
    public void build(BuildParams params) throws RecordConversionException;

}
