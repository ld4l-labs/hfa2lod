/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

/**
 * This bean represents one row in the aspect_ratio.csv concordance file.
 */
public class AspectRatioConcordanceBean {
	
	private String matchingText;
	private String namedIndividual;
	
	public String getMatchingText() {
		return matchingText;
	}
	
	public void setMatchingText(String matchingText) {
		this.matchingText = matchingText;
	}
	
	public String getNamedIndividual() {
		return namedIndividual;
	}
	
	public void setNamedIndividual(String label) {
		this.namedIndividual = label;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [matchingText=");
		builder.append(matchingText);
		builder.append(", namedIndividual=");
		builder.append(namedIndividual);
		builder.append("]");
		return builder.toString();
	}

}
