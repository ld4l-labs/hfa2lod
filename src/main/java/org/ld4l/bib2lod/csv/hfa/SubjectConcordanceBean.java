/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.util.ArrayList;
import java.util.List;

/**
 * This bean represents one row in the subject.csv concordance file.
 */
public class SubjectConcordanceBean implements ExternalUriBean {
	
	private String hfaTag;
	private String lcsh;
	private String fast;
	private String getty;
	
	public String getHfaTag() {
		return hfaTag;
	}
	
	public void setHfaTag(String hfaTag) {
		this.hfaTag = hfaTag;
	}

	public String getLcsh() {
		return lcsh;
	}

	public void setLcsh(String lcsh) {
		this.lcsh = lcsh;
	}
	
	public String getFast() {
		return fast;
	}
	
	public void setFast(String fast) {
		this.fast = fast;
	}

	public String getGetty() {
		return getty;
	}

	public void setGetty(String getty) {
		this.getty = getty;
	}

	@Override
	public List<String> getExternalUris() {
		List<String> uris = new ArrayList<>();
		if (lcsh != null && !lcsh.isEmpty()) {
			uris.add(lcsh);
		}
		if (fast != null && !fast.isEmpty()) {
			uris.add(fast);
		}
		if (getty != null && !getty.isEmpty()) {
			uris.add(getty);
		}
		
		return uris;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [hfaTag=");
		builder.append(hfaTag);
		builder.append(", lcsh=");
		builder.append(lcsh);
		builder.append(", fast=");
		builder.append(fast);
		builder.append(", getty=");
		builder.append(getty);
		builder.append("]");
		return builder.toString();
	}

}
