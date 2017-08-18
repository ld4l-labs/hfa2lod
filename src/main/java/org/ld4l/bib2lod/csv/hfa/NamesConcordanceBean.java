/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

/**
 * This bean represents one row in the names.csv concordance file.
 */
public class NamesConcordanceBean {
	
	private String name;
	private String isni;
	private String nameVariants;
	private String webPage;
	private String identity1Name;
	private String identity1Isni;
	private String identity2Name;
	private String identity2Isni;
	private String identity3Name;
	private String identity3Isni;
	private String notes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsni() {
		return isni;
	}

	public void setIsni(String isni) {
		this.isni = isni;
	}

	public String getNameVariants() {
		return nameVariants;
	}

	public void setNameVariants(String nameVariants) {
		this.nameVariants = nameVariants;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getIdentity1Name() {
		return identity1Name;
	}

	public void setIdentity1Name(String identity1Name) {
		this.identity1Name = identity1Name;
	}

	public String getIdentity1Isni() {
		return identity1Isni;
	}

	public void setIdentity1Isni(String identity1Isni) {
		this.identity1Isni = identity1Isni;
	}

	public String getIdentity2Name() {
		return identity2Name;
	}

	public void setIdentity2Name(String identity2Name) {
		this.identity2Name = identity2Name;
	}

	public String getIdentity2Isni() {
		return identity2Isni;
	}

	public void setIdentity2Isni(String identity2Isni) {
		this.identity2Isni = identity2Isni;
	}

	public String getIdentity3Name() {
		return identity3Name;
	}

	public void setIdentity3Name(String identity3Name) {
		this.identity3Name = identity3Name;
	}

	public String getIdentity3Isni() {
		return identity3Isni;
	}

	public void setIdentity3Isni(String identity3Isni) {
		this.identity3Isni = identity3Isni;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [name=");
		builder.append(name);
		builder.append(", isni=");
		builder.append(isni);
		builder.append(", nameVariants=");
		builder.append(nameVariants);
		builder.append(", webPage=");
		builder.append(webPage);
		builder.append(", identity1Name=");
		builder.append(identity1Name);
		builder.append(", identity1Isni=");
		builder.append(identity1Isni);
		builder.append(", identity2Name=");
		builder.append(identity2Name);
		builder.append(", identity2Isni=");
		builder.append(identity2Isni);
		builder.append(", identity3Name=");
		builder.append(identity3Name);
		builder.append(", identity3Isni=");
		builder.append(identity3Isni);
		builder.append(", notes=");
		builder.append(notes);
		builder.append("]");
		return builder.toString();
	}

}
