/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class NamesConcordanceManager extends AbstractConcordanceManager<NamesConcordanceBean> {
	
	private enum ConcordanceCsvColumn {
		
		NAME("name"),
		ISNI("isni"),
		NAME_VARIANTS("nameVariants"),
		WEB_PAGE("webPage"),
		IDENTITY_1_NAME("identity1Name"),
		IDENTITY_1_ISNI("identity1Isni"),
		IDENTITY_2_NAME("identity2Name"),
		IDENTITY_2_ISNI("identity2Isni"),
		IDENTITY_3_NAME("identity3Name"),
		IDENTITY_3_ISNI("identity3Isni"),
		NOTES("notes");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		/**
		 * Must match getters/setters for JavaBean reflection used by OpenCSV.
		 */
		public String toString() {
			return columnName;
		}
		
	}
	
	private static final String CONCORANCE_FILE_NAME = "/names.csv";
	
    /**
     * Constructor which loads default CSV file.
     * 
	 * @throws FileNotFoundException - If file not found on classpath.
     */
	public NamesConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	public NamesConcordanceManager(String fileName) throws URISyntaxException, IOException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, NamesConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<NamesConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<NamesConcordanceBean> csv = new CsvToBean<>();
	    List<NamesConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, NamesConcordanceBean> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); // could be looking up with keys in mixed case
	    for (NamesConcordanceBean item : list) {
	    	map.put(item.getName(), item);
	    }
	    return map;
	}

	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getCsvColumnEnums()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List<Enum> getCsvColumnEnums() {
		List<Enum> list = new ArrayList<>();
		for (ConcordanceCsvColumn val : ConcordanceCsvColumn.values()) {
			list.add(val);
		}
		return list;
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getBeanClass()
	 */
	@Override
	protected Class<NamesConcordanceBean> getBeanClass() {
		return NamesConcordanceBean.class;
	}
	
}
