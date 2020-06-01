package com.library.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.library.businessModels.LibraryItem;

public class SearchResults implements ISearchResults{
	private final int DESPLAY_ROW_SIZE = 10;
	private ArrayList<List<LibraryItem>> searchResultsPerCategory = new ArrayList<List<LibraryItem>>();

	@Override
	public boolean isNotEmpty() {
		for(List<LibraryItem> categoryResult : searchResultsPerCategory) {
			if(!categoryResult.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<LibraryItem> getAllFoundItems() {
		List<LibraryItem> allItems = new LinkedList<LibraryItem>();
		for(List<LibraryItem> categoryResult : searchResultsPerCategory) {
			allItems.addAll(categoryResult);
		}
		return allItems;
	}

	@Override
	public void addSearchResultsForCategory(List<LibraryItem> resultsPerCategory) {
		searchResultsPerCategory.add(resultsPerCategory);
	}

	@Override
	public SearchResults getResultSetForPageNumber(int requestedPageNumber) {
		SearchResults results = SearchFactory.instance().makeSearchResults();
		for(List<LibraryItem> categoryResult : searchResultsPerCategory) {
			List<LibraryItem> resultsForCurrentCategory = new LinkedList<LibraryItem>();
			Iterator<LibraryItem> iterator = categoryResult.iterator();
			for(int i=0; i < (requestedPageNumber -1)*DESPLAY_ROW_SIZE && iterator.hasNext(); i++) {
				iterator.next();
			}
			for(int i=0; i < DESPLAY_ROW_SIZE && iterator.hasNext(); i++) {
				resultsForCurrentCategory.add(iterator.next()); 
			}
			results.addSearchResultsForCategory(resultsForCurrentCategory);
		}
		return results;
	}
	
	@Override
	public ArrayList<List<LibraryItem>> getSearchResultsPerCategory() {
		return searchResultsPerCategory;
	}
}
