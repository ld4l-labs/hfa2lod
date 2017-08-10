package org.ld4l.bib2lod.csv.hfa;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractGenreConcordanceBean implements ExternalUriBean {

	private String hfaTag;
	private String locGenreForm1;
	private String locGenreForm2;
	private String locGenreForm3;
	private String fastForm1;
	private String fastForm2;
	private String fastForm3;
	private String gettyGenre;
	private String movingImageOntClass;

	public AbstractGenreConcordanceBean() {
		super();
	}

	public String getHfaTag() {
		return hfaTag;
	}

	public void setHfaTag(String hfaTag) {
		this.hfaTag = hfaTag;
	}

	public String getLocGenreForm1() {
		return locGenreForm1;
	}

	public void setLocGenreForm1(String locGenreForm1) {
		this.locGenreForm1 = locGenreForm1;
	}

	public String getLocGenreForm2() {
		return locGenreForm2;
	}

	public void setLocGenreForm2(String locGenreForm2) {
		this.locGenreForm2 = locGenreForm2;
	}

	public String getLocGenreForm3() {
		return locGenreForm3;
	}

	public void setLocGenreForm3(String locGenreForm3) {
		this.locGenreForm3 = locGenreForm3;
	}

	public String getFastForm1() {
		return fastForm1;
	}

	public void setFastForm1(String fastForm1) {
		this.fastForm1 = fastForm1;
	}

	public String getFastForm2() {
		return fastForm2;
	}

	public void setFastForm2(String fastForm2) {
		this.fastForm2 = fastForm2;
	}

	public String getFastForm3() {
		return fastForm3;
	}

	public void setFastForm3(String fastForm3) {
		this.fastForm3 = fastForm3;
	}

	public String getGettyGenre() {
		return gettyGenre;
	}

	public void setGettyGenre(String gettyGenre) {
		this.gettyGenre = gettyGenre;
	}

	public String getMovingImageOntClass() {
		return movingImageOntClass;
	}

	public void setMovingImageOntClass(String movingImageOntClass) {
		this.movingImageOntClass = movingImageOntClass;
	}

	@Override
	public List<String> getExternalUris() {
		List<String> uris = new ArrayList<>();
		if (locGenreForm1 != null && !locGenreForm1.isEmpty()) {
			uris.add(locGenreForm1);
		}
		if (locGenreForm2 != null && !locGenreForm2.isEmpty()) {
			uris.add(locGenreForm2);
		}
		if (locGenreForm3 != null && !locGenreForm3.isEmpty()) {
			uris.add(locGenreForm3);
		}
		if (fastForm1 != null && !fastForm1.isEmpty()) {
			uris.add(fastForm1);
		}
		if (fastForm2 != null && !fastForm2.isEmpty()) {
			uris.add(fastForm2);
		}
		if (fastForm3 != null && !fastForm3.isEmpty()) {
			uris.add(fastForm3);
		}
		if (gettyGenre != null && !gettyGenre.isEmpty()) {
			uris.add(gettyGenre);
		}
		
		return uris;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" [hfaTag=");
		builder.append(hfaTag);
		builder.append(", locGenreForm1=");
		builder.append(locGenreForm1);
		builder.append(", locGenreForm2=");
		builder.append(locGenreForm2);
		builder.append(", locGenreForm3=");
		builder.append(locGenreForm3);
		builder.append(", fastForm1=");
		builder.append(fastForm1);
		builder.append(", fastForm2=");
		builder.append(fastForm2);
		builder.append(", fastForm3=");
		builder.append(fastForm3);
		builder.append(", gettyGenre=");
		builder.append(gettyGenre);
		builder.append(", movingImageOntClass=");
		builder.append(movingImageOntClass);
		builder.append("]");
		return builder.toString();
	}

}