package com.library.search;

import javax.servlet.http.HttpSession;

public interface IDBSearchController {
	public SearchResults search(ISearchRequest searchRequestDetails, HttpSession httpSession);
	boolean clearSearch(HttpSession httpSession);
}
