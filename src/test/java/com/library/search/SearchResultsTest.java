package com.library.search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.library.businessModels.BusinessModelsFactory;
import com.library.businessModels.LibraryItem;

public class SearchResultsTest {
	private SearchResults sr = null;
	private List<LibraryItem> addedItems = null;
	private static BusinessModelsFactory bmf = BusinessModelsFactory.instance();

	@Before
	public void setUp() throws Exception {
		sr = SearchFactory.instance().makeSearchResults();
		addedItems = new LinkedList<>();
	}

	@Test
	public void isNotEmptyPassesWhenOneBookIsAdded() {
		List<LibraryItem> books = new LinkedList<LibraryItem>();
		books.add(bmf.makeBook());
		sr.addSearchResultsForCategory(books);
		assertTrue(sr.isNotEmpty());
	}

	@Test
	public void isNotEmptyPassesWhenOneMovieIsAdded() {
		List<LibraryItem> movies = new LinkedList<LibraryItem>();
		movies.add(bmf.makeMovie());
		sr.addSearchResultsForCategory(movies);
		assertTrue(sr.isNotEmpty());
	}

	@Test
	public void isNotEmptyPassesWhenOneMusicIsAdded() {
		List<LibraryItem> musics = new LinkedList<LibraryItem>();
		musics.add(bmf.makeMusic());
		sr.addSearchResultsForCategory(musics);
		assertTrue(sr.isNotEmpty());
	}

	@Test
	public void canGetAllFoundItems() {
		SearchResultsPopulator.populateSearchResults(addedItems, sr, new MockNumOfItemsInResult(12, 13, 17));
		List<LibraryItem> items = sr.getAllFoundItems();
		items.containsAll(addedItems);
		assertTrue(items.size() == addedItems.size());
	}

	@Test
	public void canGetSearchResultsPerCategory() {
		SearchResultsPopulator.populateSearchResults(addedItems, sr, new MockNumOfItemsInResult(8, 11, 23));
		ArrayList<List<LibraryItem>> resultsPerCategories = sr.getSearchResultsPerCategory();
		for (List<LibraryItem> resultsPerCategory : resultsPerCategories) {
			addedItems.containsAll(resultsPerCategory);
			int size = resultsPerCategory.size();
			assertTrue(size == 8 || size == 11 || size == 23);
		}
	}

	@Test
	public void canGetResultSetForPageNumber() {
		SearchResultsPopulator.populateSearchResults(addedItems, sr, new MockNumOfItemsInResult(15, 17, 19));
		SearchResults sr = this.sr.getResultSetForPageNumber(1);
		ArrayList<List<LibraryItem>> resultsPerCategories = sr.getSearchResultsPerCategory();
		for (List<LibraryItem> resultsPerCategory : resultsPerCategories) {
			addedItems.containsAll(resultsPerCategory);
			assertTrue(resultsPerCategory.size() == 10);
		}
		sr = this.sr.getResultSetForPageNumber(2);
		resultsPerCategories = sr.getSearchResultsPerCategory();
		assertTrue(resultsPerCategories.size() == 3);
		assertTrue(resultsPerCategories.get(0).size() == 5);
		assertTrue(resultsPerCategories.get(1).size() == 7);
		assertTrue(resultsPerCategories.get(2).size() == 9);
	}
}
