/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public abstract class AbstractGenreConcordanceManager<T extends AbstractGenreConcordanceBean>
		extends AbstractConcordanceManager<T> {

	protected enum ConcordanceCsvColumn {
			
			HFA_TAG("hfaTag"),
			LOC_GENRE_FORM_1("locGenreForm1"),
			LOC_GENRE_FORM_2("locGenreForm2"),
			LOC_GENRE_FORM_3("locGenreForm3"),
			FAST_FORM_1("fastForm1"),
			FAST_FORM_2("fastForm2"),
			FAST_FORM_3("fastForm3"),
			GETTY_GENRE("gettyGenre"),
			MOVING_IMAGE_ONT_CLASS("movingImageOntClass");
			
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

	public AbstractGenreConcordanceManager(String fileName) throws IOException {
		super(fileName);
	}

	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, T> initBeanMap(HeaderColumnNameTranslateMappingStrategy<T> strat, CSVReader reader) {
		
	    CsvToBean<T> csv = new CsvToBean<>();
	    List<T> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, T> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); // could be looking up with keys in mixed case
	    for (T item : list) {
	    	map.put(item.getHfaTag(), item);
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

}