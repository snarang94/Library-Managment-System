package com.library.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MovieSearchTest {
	private MovieSearch ms;

	@Before
	public void setUp() throws Exception {
		ms = SearchFactory.instance().makeMovieSearech();
	}

	@Test
	public void searchReturnsList() {
		assertTrue(ms.search("") instanceof List<?>);
	}

	@Test
	public void allDataMembersDefaultValuesAreTrue() {
		assertTrue(ms.isSearchInMovies());
		assertTrue(ms.isSearchMovieDescription());
		assertTrue(ms.isSearchMovieDirector());
		assertTrue(ms.isSearchMovieTitle());
	}

	@Test
	public void canSetAndGetAllDataMembers() {
		ms.setSearchInMovies(false);
		assertFalse(ms.isSearchInMovies());
		ms.setSearchMovieDescription(false);
		assertFalse(ms.isSearchMovieDescription());
		ms.setSearchMovieDirector(false);
		assertFalse(ms.isSearchMovieDirector());
		ms.setSearchMovieTitle(false);
		assertFalse(ms.isSearchMovieTitle());
	}
}
