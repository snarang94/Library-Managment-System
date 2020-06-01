package com.library.search;

public class SearchTermsAndPage {
	private String searchTerms = null;
	private int requestedResultsPageNumber = 1;
	
	public String getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
	}
	public int getRequestedResultsPageNumber() {
		return requestedResultsPageNumber;
	}
	public void setRequestedResultsPageNumber(int requestedResultsPageNumber) {
		this.requestedResultsPageNumber = requestedResultsPageNumber;
	}
}
