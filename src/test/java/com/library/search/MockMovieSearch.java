package com.library.search;

import java.util.LinkedList;
import java.util.List;

import com.library.businessModels.BusinessModelsFactory;
import com.library.businessModels.LibraryItem;

public class MockMovieSearch extends MovieSearch {
	private List<LibraryItem> movies = new LinkedList<>();

	public MockMovieSearch(int numOfItems) {
		for (int i = 0; i < numOfItems; ++i) {
			movies.add(BusinessModelsFactory.instance().makeMovie());
		}
	}

	@Override
	public List<LibraryItem> search(String searchterms) {
		return movies;
	}
}
