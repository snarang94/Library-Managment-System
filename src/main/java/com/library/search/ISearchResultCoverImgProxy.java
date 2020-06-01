package com.library.search;

import javax.servlet.http.HttpSession;

public interface ISearchResultCoverImgProxy {
	void deleteCoverImagesForSearchResults(HttpSession httpSession);
	void loadCoverImages(SearchResults searchResults, String requestedPageNumber, HttpSession httpSession);
}
