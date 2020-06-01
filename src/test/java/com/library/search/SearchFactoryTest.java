package com.library.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchFactoryTest {

	@Test
	public void test() {
		SearchFactory sf = SearchFactory.instance();
		assertTrue(sf.makeBookSearch() instanceof BookSearch);
		assertTrue(sf.makeMovieSearech() instanceof MovieSearch);
		assertTrue(sf.makeMusicSearch() instanceof MusicSearch);
		assertTrue(sf.makeSearchResults() instanceof SearchResults);
		assertTrue(sf.makeSearchTermsAndPage() instanceof SearchTermsAndPage);
	}
}
