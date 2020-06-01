package com.library.search;

import java.util.LinkedList;
import java.util.List;

import com.library.businessModels.BusinessModelsFactory;
import com.library.businessModels.LibraryItem;

public class SearchResultsPopulator {
	private static BusinessModelsFactory bmf = BusinessModelsFactory.instance();

	public static void populateSearchResults(List<LibraryItem> addedItems, SearchResults sr,
			MockNumOfItemsInResult numOfItems) {
		List<LibraryItem> books = new LinkedList<LibraryItem>();
		int numOfBooks = numOfItems.getNumOfBooks();
		for (int i = 0; i < numOfBooks; ++i) {
			LibraryItem book = bmf.makeBook();
			book.setItemID(i);
			books.add(book);
		}
		sr.addSearchResultsForCategory(books);
		addedItems.addAll(books);

		List<LibraryItem> movies = new LinkedList<LibraryItem>();
		int numOfMovies = numOfItems.getNumOfMovies();
		for (int i = numOfBooks; i < numOfBooks + numOfMovies; ++i) {
			LibraryItem movie = bmf.makeMovie();
			movie.setItemID(i);
			movies.add(movie);
		}
		sr.addSearchResultsForCategory(movies);
		addedItems.addAll(movies);

		List<LibraryItem> musics = new LinkedList<LibraryItem>();
		int numOfMusic = numOfItems.getNumOfMusic();
		for (int i = numOfBooks + numOfMovies; i < numOfBooks + numOfMovies + numOfMusic; ++i) {
			LibraryItem music = bmf.makeMusic();
			music.setItemID(i);
			musics.add(music);
		}
		sr.addSearchResultsForCategory(musics);
		addedItems.addAll(musics);
	}
}
