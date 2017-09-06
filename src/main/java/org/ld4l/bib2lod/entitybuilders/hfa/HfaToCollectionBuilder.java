/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.caching.CachingService;
import org.ld4l.bib2lod.caching.CachingService.CachingServiceException;
import org.ld4l.bib2lod.caching.CachingService.MapType;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.hfa.HfaCollectionType;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Builds a Collection Entity.
 */
public class HfaToCollectionBuilder extends HfaToLd4lEntityBuilder {
	
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	CachingService cachingService = CachingService.instance();
    	
    	HfaRecord record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a collection.");
        }

        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build a collection.");
        }
        
        HfaTextField hfaCollection = record.getField(HfaRecord.ColumnAttributeText.COLLECTION);
        if (hfaCollection == null) {
        	LOGGER.debug("No field for [{}]", HfaRecord.ColumnAttributeText.COLLECTION.getColumnAttributeText());
        	return null;
        }
        
        String collectionName = hfaCollection.getTextValue().trim();
        
        Map<String, String> mapToUri = cachingService.getMap(MapType.NAMES_TO_URI);
        String cachedCollectionUri = mapToUri.get(collectionName);
        
        LOGGER.debug("Lookup collectionName: [{}] -- URI: [{}]", collectionName, cachedCollectionUri);

        Entity collectionEntity = null;
        if (cachedCollectionUri == null) {
        	collectionEntity = new Entity(HfaCollectionType.COLLECTION);
        	collectionEntity.addAttribute(Ld4lDatatypeProp.LABEL, collectionName);
        	String collectionUri = UriService.getUri(collectionEntity);
        	
        	collectionEntity.buildResource(collectionUri);
        	try {
				cachingService.putUri(MapType.NAMES_TO_URI, collectionName, collectionUri);
			} catch (CachingServiceException e) {
				throw new EntityBuilderException(e);
			}
        	parentEntity.addRelationship(HfaObjectProp.IS_PART_OF, collectionEntity);
        } else {

        	collectionEntity = new Entity(HfaCollectionType.COLLECTION);
        	collectionEntity.addAttribute(Ld4lDatatypeProp.LABEL, collectionName);
        	
        	collectionEntity.buildResource(cachedCollectionUri);
        	parentEntity.addRelationship(HfaObjectProp.IS_PART_OF, collectionEntity);
        }
        
        return collectionEntity;
    }
    
}
