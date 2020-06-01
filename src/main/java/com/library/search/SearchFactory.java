package com.library.search;

public class SearchFactory {
	static private SearchFactory instance = null;
	
	static public SearchFactory instance() {
		if(null == instance) {
			instance = new SearchFactory();
		}
		return instance;
	}

	public BookSearch makeBookSearch() {
		return new BookSearch();
	}

	public MusicSearch makeMusicSearch() {
		return new MusicSearch();
	}

	public MovieSearch makeMovieSearech() {
		return new MovieSearch();
	}

	public SearchTermsAndPage makeSearchTermsAndPage() {
		return new SearchTermsAndPage();
	}

	public SearchResults makeSearchResults() {
		return new SearchResults();
	}
	
	public SearchRequest makeSearchRequest() {
		return new SearchRequest();
	}
}
