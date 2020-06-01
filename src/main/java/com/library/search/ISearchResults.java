package com.library.search;

import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.LibraryItem;

public interface ISearchResults {
	public ArrayList<List<LibraryItem>> getSearchResultsPerCategory();
	public SearchResults getResultSetForPageNumber(int requestedPageNumber);
	public void addSearchResultsForCategory(List<LibraryItem> resultsPerCategory);
	public List<LibraryItem> getAllFoundItems();
	public boolean isNotEmpty();

}
