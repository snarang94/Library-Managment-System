package com.library.search;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MusicSearchTest {
	private MusicSearch ms;

	@Before
	public void setUp() throws Exception {
		ms = SearchFactory.instance().makeMusicSearch();
	}

	@Test
	public void searchReturnsList() {
		assertTrue(ms.search("") instanceof List<?>);
	}

	@Test
	public void allDataMembersDefaultValuesAreTrue() {
		assertTrue(ms.isSearchInMusic());
		assertTrue(ms.isSearchMusicAlbumName());
		assertTrue(ms.isSearchMusicArtist());
		assertTrue(ms.isSearchMusicRecordLabel());
	}

	@Test
	public void canSetAndGetAllDataMembers() {
		ms.setSearchInMusic(false);
		assertFalse(ms.isSearchInMusic());
		ms.setSearchMusicAlbumName(false);
		assertFalse(ms.isSearchMusicAlbumName());
		ms.setSearchMusicArtist(false);
		assertFalse(ms.isSearchMusicArtist());
		ms.setSearchMusicRecordLabel(false);
		assertFalse(ms.isSearchMusicRecordLabel());
	}
}
