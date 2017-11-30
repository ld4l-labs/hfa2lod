/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.externalbuilders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.csv.hfa.CharacteristicsConcordanceBean;
import org.ld4l.bib2lod.csv.hfa.CharacteristicsConcordanceManager;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.OwlThingType;
import org.ld4l.bib2lod.ontology.hfa.HfaGeneratedNamedIndividual;
import org.ld4l.bib2lod.ontology.hfa.HfaNamespace;
import org.ld4l.bib2lod.ontology.hfa.HfaObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord.ColumnAttributeText;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Adds external URI's pulled from a concordance file to the parent Entity.
 * If no match is found there will be no modification to the parent Entity.
 */
public class HfaToCharacteristicsConcordanceBuilder implements ConcordanceReferenceBuilder {

    private CharacteristicsConcordanceManager characteristicsConcordanceManager;
    
	private static final Logger LOGGER = LogManager.getLogger();

	public HfaToCharacteristicsConcordanceBuilder() throws EntityBuilderException {
    	try {
			this.characteristicsConcordanceManager = new CharacteristicsConcordanceManager();
		} catch ( URISyntaxException | IOException e) {
			throw new EntityBuilderException("Could not instantiate CharacteristicsConcordanceManager", e);
		}
	}

    @Override
    public void build(BuildParams params) throws EntityBuilderException {
        
        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
        	throw new EntityBuilderException("A parent Entity is required to build a title.");
        }
        
        HfaRecord record = (HfaRecord)params.getRecord();
        if (record == null) {
        	throw new EntityBuilderException("A HfaRecord is required to build a title.");
        }
        
        
        // Color (part of characteristics) -- only applies to bf:Work (bf:MovingImage)
        if (Ld4lWorkType.MOVING_IMAGE.equals(parentEntity.getType())) {
        	HfaTextField hfaField = record.getField(ColumnAttributeText.COLOR);
        	if (hfaField != null) {
        		String color = hfaField.getTextValue().trim();
        		CharacteristicsConcordanceBean characteristicBean = characteristicsConcordanceManager.getConcordanceEntry(color);
        		if (characteristicBean != null) {
        			String ni = characteristicBean.getNamedIndividual();
        			HfaGeneratedNamedIndividual namedIndividual = null;
        			try {
        				String[] parts = ni.split(":");
        				HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
        				namedIndividual = new HfaGeneratedNamedIndividual(ns, parts[1]);
        			} catch (Exception e) {
        				throw new EntityBuilderException("Could not parse HfaGeneratedNamedIndividual from concordance value: " + ni);
        			}
        			Entity colorEntity = new Entity(OwlThingType.THING);
        			colorEntity.addAttribute(Ld4lDatatypeProp.LABEL, color);
        			colorEntity.buildResource(namedIndividual.uri());
        			parentEntity.addRelationship(HfaObjectProp.HAS_COLOR_CONTENT, colorEntity);
        		} else {
        			LOGGER.warn("No concordance match for [{}]", hfaField.getTextValue());
        		}
        	}
        }
        
        // Characteristics -- only applies to bf:Item
        if (Ld4lItemType.ITEM.equals(parentEntity.getType())) {
        	List<HfaTextField> hfaTextFields = new ArrayList<>();
        	HfaTextField hfaField = record.getField(HfaRecord.ColumnAttributeText.CONDITION_DEFECTS);
        	if (hfaField != null) {
        		hfaTextFields.add(hfaField);
        	}
        	hfaField = record.getField(HfaRecord.ColumnAttributeText.CAUTIONS);
        	if (hfaField != null) {
        		hfaTextFields.add(hfaField);
        	}
        	hfaField = record.getField(HfaRecord.ColumnAttributeText.PRINT_CONDITION);
        	if (hfaField != null) {
        		hfaTextFields.add(hfaField);
        	}
        	
        	// go through all fields and tokenize each by newline character then loop through each token
        	for (HfaTextField field : hfaTextFields) {
        		String characteristic = field.getTextValue().trim();
        		CharacteristicsConcordanceBean characteristicBean = characteristicsConcordanceManager.getConcordanceEntry(characteristic);
        		if (characteristicBean != null) {
        			String ni = characteristicBean.getNamedIndividual();
        			HfaGeneratedNamedIndividual namedIndividual = null;
        			try {
        				String[] parts = ni.split(":");
        				HfaNamespace ns = HfaNamespace.getHfaNamespaceByPrefix(parts[0]);
        				namedIndividual = new HfaGeneratedNamedIndividual(ns, parts[1]);
        			} catch (Exception e) {
        				throw new EntityBuilderException("Could not parse HfaGeneratedNamedIndividual from concordance value: " + ni);
        			}
        			Entity characteristicEntity = new Entity(OwlThingType.THING);
        			characteristicEntity.addAttribute(Ld4lDatatypeProp.LABEL, characteristic);
        			characteristicEntity.buildResource(namedIndividual.uri());
        			parentEntity.addRelationship(HfaObjectProp.HAS_CHARACTERISTIC, characteristicEntity);
        		} else {
        			LOGGER.warn("No concordance match for [{}]", field.getTextValue());
        		}
        	}
        }
    }

    /**
     * Used for testing to override the concordance managers set in the constructor.
     */
    protected void setCharacteristicsConcordanceManager(CharacteristicsConcordanceManager concordanceManager) {
		this.characteristicsConcordanceManager = concordanceManager;
    }
}
