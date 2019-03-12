package org.pentaho.di.core.database;

import java.util.List;

public class QueryResultWrapper {
	private List<Object[]> resultsWithHeader;
	
	private List<String> rowIds;

	public List<Object[]> getResultsWithHeader() {
		return resultsWithHeader;
	}

	public void setResultsWithHeader(List<Object[]> resultsWithHeader) {
		this.resultsWithHeader = resultsWithHeader;
	}

	public List<String> getRowIds() {
		return rowIds;
	}

	public void setRowIds(List<String> rowIds) {
		this.rowIds = rowIds;
	}
}
