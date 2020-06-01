package com.library.search;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.LibraryItem;
import com.library.localStorage.CoverImageLoader;
import com.library.localStorage.ICoverImageLoader;

public class SearchResultCoverImgProxy implements ISearchResultCoverImgProxy {
	
	private static final String SEP = File.separator;
	private static final Logger logger = LogManager.getLogger(CoverImageLoader.class);
	
	@Inject
	private ICoverImageLoader imageLoader;
	private HashMap<HttpSession, LinkedList<String>> sessionToListOfRequestedPages = 
															new HashMap<HttpSession, LinkedList<String>>();
	
	@Override
	public void loadCoverImages(SearchResults resultsForRequestedPage, String requestedPageNumber, HttpSession httpSession) {
		String sessionResultsPath = "searchResults" + SEP + httpSession.getId() + SEP;
		String pathToRequestedPageNumberImagesDir = sessionResultsPath + requestedPageNumber;

		LinkedList<String> resultPagesForSession = sessionToListOfRequestedPages.get(httpSession);
		if(null == resultPagesForSession) {
			resultPagesForSession = new LinkedList<String>();
			sessionToListOfRequestedPages.put(httpSession, resultPagesForSession);
		} else {
			for(String previouslyRequestedPageNumber : resultPagesForSession) {
				if(requestedPageNumber.equals(previouslyRequestedPageNumber)) {;
					return;
				}
			}
		}
		resultPagesForSession.add(requestedPageNumber);
		String message = "Loading cover images for search results page number " + resultsForRequestedPage + 
																			" by user with session ID" + httpSession.getId();
		logger.log(Level.INFO, message);
		List<LibraryItem> items = resultsForRequestedPage.getAllFoundItems();
		for(LibraryItem item : items) {
			String imageUrl = imageLoader.loadCoverImageByItemIdToDisk(item.getItemID(), pathToRequestedPageNumberImagesDir);
			item.setCoverImageUrl(imageUrl);
		}
	}

	@Override
	public void deleteCoverImagesForSearchResults(HttpSession httpSession) {
		logger.log(Level.INFO, "Deleting cover images loaded for session ID " + httpSession.getId());
		String sessionResultsDir = "searchResults" + SEP + httpSession.getId() + SEP;
		sessionToListOfRequestedPages.remove(httpSession);
		imageLoader.deleteDynamicContent(sessionResultsDir);	
	}
}
