/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

/**
 * This bean represents one row in the material_types.csv concordance file.
 */
public class MaterialTypeConcordanceBean {
	
	private String matchingText;
	private String label;
	private String ontClass;
	
	public String getMatchingText() {
		return matchingText;
	}
	
	public void setMatchingText(String matchingText) {
		this.matchingText = matchingText;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getOntClass() {
		return ontClass;
	}
	
	public void setOntClass(String ontClass) {
		this.ontClass = ontClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [matchingText=");
		builder.append(matchingText);
		builder.append(", label=");
		builder.append(label);
		builder.append(", ontClass=");
		builder.append(ontClass);
		builder.append("]");
		return builder.toString();
	}

}
