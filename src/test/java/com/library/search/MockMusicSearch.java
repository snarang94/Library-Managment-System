package com.library.search;

import java.util.LinkedList;
import java.util.List;

import com.library.businessModels.BusinessModelsFactory;
import com.library.businessModels.LibraryItem;

public class MockMusicSearch extends MusicSearch {
	private List<LibraryItem> musics = new LinkedList<>();

	public MockMusicSearch(int numOfItems) {
		for (int i = 0; i < numOfItems; ++i) {
			musics.add(BusinessModelsFactory.instance().makeMusic());
		}
	}

	@Override
	public List<LibraryItem> search(String searchterms) {
		return musics;
	}
}
