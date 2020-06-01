package com.library.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.demo.LibraryApplication;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

import javax.inject.Inject;

@ActiveProfiles("DBSearchControllerTest")
@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = { LibraryApplication.class, DBSearchControllerTestConfiguration.class })
public class DBSearchControllerTest {
	private final int DESPLAY_ROW_SIZE = 10;

	@Inject
	private DBSearchController dbSearchController;
	@Inject
	private SearchResultCoverImgProxy searchResultCoverImgProxyMock;
	private MockHttpSession mockHttpSession;
	private MockSearchRequest mockSearchRequest;
	private SearchResults results;

	@Before
	public void setUp() throws Exception {
		results = null;
		mockHttpSession = new MockHttpSession();
		mockSearchRequest = new MockSearchRequest();
		SearchTermsAndPage termsAndPage = SearchFactory.instance().makeSearchTermsAndPage();
		termsAndPage.setRequestedResultsPageNumber(1);
		termsAndPage.setSearchTerms("asd");
		mockSearchRequest.setTermsAndPage(termsAndPage);
		SearchCategory bookSearch = SearchFactory.instance().makeBookSearch();
		mockSearchRequest.addCategoryToSearchIn(bookSearch);
		SearchCategory movieSearch = SearchFactory.instance().makeMovieSearech();
		mockSearchRequest.addCategoryToSearchIn(movieSearch);
		SearchCategory musicSearch = SearchFactory.instance().makeMusicSearch();
		mockSearchRequest.addCategoryToSearchIn(musicSearch);
		doNothing().when(searchResultCoverImgProxyMock).loadCoverImages(any(), any(), any());
	}

	@Test
	public void orderOfResultsTypeConformsTheOrderOfCategoriesToSearch() {
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().size() == 3);
		assertTrue(results.getSearchResultsPerCategory().get(0).get(0) instanceof Book);
		assertTrue(results.getSearchResultsPerCategory().get(1).get(0) instanceof Movie);
		assertTrue(results.getSearchResultsPerCategory().get(2).get(0) instanceof Music);
	}

	@Test
	public void returnsResultsForTheNextrequestedPage() {
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().get(0).size() == DESPLAY_ROW_SIZE);
		assertTrue(results.getSearchResultsPerCategory().get(1).size() == DESPLAY_ROW_SIZE);
		assertTrue(results.getSearchResultsPerCategory().get(2).size() == DESPLAY_ROW_SIZE);
		mockSearchRequest.getTermsAndPage().setRequestedResultsPageNumber(2);
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().get(0).size() == 5);
		assertTrue(results.getSearchResultsPerCategory().get(1).size() == 7);
		assertTrue(results.getSearchResultsPerCategory().get(2).size() == 9);
	}

	@Test
	public void executesNewSearchIfSearchTermsAreChanged() {
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().get(0).size() == DESPLAY_ROW_SIZE);
		mockSearchRequest.getTermsAndPage().setSearchTerms("asdasd");
		mockSearchRequest.getTermsAndPage().setRequestedResultsPageNumber(2);
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().get(0).size() == 5);
		SearchTermsAndPage termsAndPage = SearchFactory.instance().makeSearchTermsAndPage();
		termsAndPage.setRequestedResultsPageNumber(1);
		termsAndPage.setSearchTerms("asdasd");
		mockSearchRequest.setTermsAndPage(termsAndPage);
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(results.getSearchResultsPerCategory().get(0).size() == DESPLAY_ROW_SIZE);
	}

	@Test(expected = IllegalStateException.class)
	public void removesUserSearchDataOnUserLogOutIfUserWasLoggedIn() {
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(dbSearchController.notifyUserSignOut(mockHttpSession));
		dbSearchController.notifyUserSignOut(mockHttpSession);
	}

	@Test
	public void clearsSearch() {
		assertFalse(dbSearchController.clearSearch(mockHttpSession));
		results = dbSearchController.search(mockSearchRequest, mockHttpSession);
		assertTrue(dbSearchController.clearSearch(mockHttpSession));
		assertFalse(dbSearchController.clearSearch(mockHttpSession));
	}
}
