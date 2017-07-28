/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

/**
 * This bean represents one row in the television_genre.csv concordance file.
 */
public class TelevisionGenreConcordanceBean {
	
	private String hfaTag;
	private String locGenre;
	private String combinedAdditionalLocGenre;
	private String fastForm;
	private String combinedAdditionalFastForm;
	private String movingImageOntClass;
	
	public String getHfaTag() {
		return hfaTag;
	}
	
	public void setHfaTag(String hfaTag) {
		this.hfaTag = hfaTag;
	}
	
	public String getLocGenre() {
		return locGenre;
	}
	
	public void setLocGenre(String locGenre) {
		this.locGenre = locGenre;
	}
	
	public String getCombinedAdditionalLocGenre() {
		return combinedAdditionalLocGenre;
	}
	
	public void setCombinedAdditionalLocGenre(String combinedAdditionalLocGenre) {
		this.combinedAdditionalLocGenre = combinedAdditionalLocGenre;
	}

	public String getFastForm() {
		return fastForm;
	}

	public void setFastForm(String fastForm) {
		this.fastForm = fastForm;
	}

	public String getCombinedAdditionalFastForm() {
		return combinedAdditionalFastForm;
	}

	public void setCombinedAdditionalFastForm(String combinedAdditionalFastForm) {
		this.combinedAdditionalFastForm = combinedAdditionalFastForm;
	}

	public String getMovingImageOntClass() {
		return movingImageOntClass;
	}

	public void setMovingImageOntClass(String movingImageOntClass) {
		this.movingImageOntClass = movingImageOntClass;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [hfaTag=");
		builder.append(hfaTag);
		builder.append(", locGenre=");
		builder.append(locGenre);
		builder.append(", combinedAdditionalLocGenre=");
		builder.append(combinedAdditionalLocGenre);
		builder.append(", fastForm=");
		builder.append(fastForm);
		builder.append(", combinedAdditionalFastForm=");
		builder.append(combinedAdditionalFastForm);
		builder.append(", movingImageOntClass=");
		builder.append(movingImageOntClass);
		builder.append("]");
		return builder.toString();
	}

}
