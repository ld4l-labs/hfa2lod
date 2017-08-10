/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public class FilmGenreConcordanceManager extends AbstractGenreConcordanceManager<FilmGenreConcordanceBean> {
	
	private static final String CONCORANCE_FILE_NAME = "/film_genre.csv";
	
    /**
     * Constructor which loads default CSV file.
     * 
	 * @throws FileNotFoundException - If file not found on classpath.
     */
	public FilmGenreConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	public FilmGenreConcordanceManager(String fileName) throws URISyntaxException, IOException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getBeanClass()
	 */
	@Override
	protected Class<FilmGenreConcordanceBean> getBeanClass() {
		return FilmGenreConcordanceBean.class;
	}
	
}
