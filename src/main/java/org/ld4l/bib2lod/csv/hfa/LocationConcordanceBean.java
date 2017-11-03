/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

/**
 * This bean represents one row in the location.csv concordance file.
 */
public class LocationConcordanceBean {
	
	private String matchingText;
	private String label;
	private String uri;
	
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
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [matchingText=");
		builder.append(matchingText);
		builder.append(", label=");
		builder.append(label);
		builder.append(", uri=");
		builder.append(uri);
		builder.append("]");
		return builder.toString();
	}

}
