package com.library.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BookSearchTest {
	private BookSearch bs;

	@Before
	public void setUp() throws Exception {
		bs = new BookSearch();
	}

	@Test
	public void searchReturnsList() {
		assertTrue(bs.search("") instanceof List<?>);
	}

	@Test
	public void allDataMembersDefaultValuesAreTrue() {
		assertTrue(bs.isSearchBookAuthor());
		assertTrue(bs.isSearchBookCategory());
		assertTrue(bs.isSearchBookDescription());
		assertTrue(bs.isSearchBookISBN());
		assertTrue(bs.isSearchBookPublisher());
		assertTrue(bs.isSearchBookTitle());
		assertTrue(bs.isSearchInBooks());
	}

	@Test
	public void canSetAndGetAllDataMembers() {
		bs.setSearchBookAuthor(false);
		assertFalse(bs.isSearchBookAuthor());
		bs.setSearchBookCategory(false);
		assertFalse(bs.isSearchBookCategory());
		bs.setSearchBookDescription(false);
		assertFalse(bs.isSearchBookDescription());
		bs.setSearchBookISBN(false);
		assertFalse(bs.isSearchBookISBN());
		bs.setSearchBookPublisher(false);
		assertFalse(bs.isSearchBookPublisher());
		bs.setSearchBookTitle(false);
		assertFalse(bs.isSearchBookTitle());
		bs.setSearchInBooks(false);
		assertFalse(bs.isSearchInBooks());
	}
}
