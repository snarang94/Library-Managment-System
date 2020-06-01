package com.library.search;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.businessModels.LibraryItem;
import com.library.demo.LibraryApplication;

@ActiveProfiles("CoverImgProxyTest")
@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = { LibraryApplication.class, SearchResultCoverImgProxyTestConfiguration.class })
public class SearchResultCoverImgProxyTest {
	private static final String SEP = File.separator;
	@Inject
	private SearchResultCoverImgProxy srcip;
	private static final String PATH_TO_DYNAMIC_CONTENT_DIR = System.getProperty("user.dir") + SEP
			+ "dynamicContent" + SEP;

	private SearchResults srForPageXSession1 = null;
	private SearchResults srForPageYSession1 = null;
	private SearchResults srForPageXSession2 = null;
	private List<LibraryItem> notUsedHere = null;
	private MockHttpSession mockHttpSession1;
	private MockHttpSession mockHttpSession2;

	@Before
	public void setUp() throws Exception {
		srForPageXSession1 = SearchFactory.instance().makeSearchResults();
		srForPageYSession1 = SearchFactory.instance().makeSearchResults();
		srForPageXSession2 = SearchFactory.instance().makeSearchResults();
		notUsedHere = new LinkedList<>();
		mockHttpSession1 = new MockHttpSession();
		mockHttpSession2 = new MockHttpSession();
	}

	@Test
	public void coverImageURLsAreAsExpectedForDifferentResultPageNumberAndDifferentSessions() {
		int nBooks1Page1 = 10;
		int nMovies1Page1 = 10;
		int nMusic1Page1 = 10;
		String session1PageNumber1 = "1";

		int nBooks1Page4 = 3;
		int nMovies1Page4 = 5;
		int nMusic1Page4 = 7;
		String session1PageNumber4 = "4";

		int nBooks2Page1 = 9;
		int nMovies2Page1 = 6;
		int nMusic2Page1 = 1;
		String session2PageNumber1 = "1";

		SearchResultsPopulator.populateSearchResults(notUsedHere, srForPageXSession1,
				new MockNumOfItemsInResult(nBooks1Page1, nMovies1Page1, nMusic1Page1));
		srcip.loadCoverImages(srForPageXSession1, session1PageNumber1, mockHttpSession1);
		SearchResultsPopulator.populateSearchResults(notUsedHere, srForPageYSession1,
				new MockNumOfItemsInResult(nBooks1Page4, nMovies1Page4, nMusic1Page4));
		srcip.loadCoverImages(srForPageYSession1, session1PageNumber4, mockHttpSession1);
		SearchResultsPopulator.populateSearchResults(notUsedHere, srForPageXSession2,
				new MockNumOfItemsInResult(nBooks2Page1, nMovies2Page1, nMusic2Page1));
		srcip.loadCoverImages(srForPageXSession2, session2PageNumber1, mockHttpSession2);

		assertExpectedPaths(mockHttpSession1, srForPageXSession1, session1PageNumber1);
		assertExpectedPaths(mockHttpSession1, srForPageYSession1, session1PageNumber4);
		assertExpectedPaths(mockHttpSession2, srForPageXSession2, session2PageNumber1);
	}

	private void assertExpectedPaths(MockHttpSession session, SearchResults sr, String pageNumber) {
		for (List<LibraryItem> resultsPerCategory : sr.getSearchResultsPerCategory()) {
			List<String> urlsLoaded = new LinkedList<>();
			List<String> urlsExpected = new LinkedList<>();
			for (LibraryItem item : resultsPerCategory) {
				urlsLoaded.add(item.getCoverImageUrl());
				urlsExpected.add(PATH_TO_DYNAMIC_CONTENT_DIR + "searchResults" + SEP + session.getId()
						+ SEP + pageNumber + SEP + item.getItemID());
			}
			assertTrue(urlsLoaded.size() == urlsExpected.size());
			for (int i = 0; i < urlsLoaded.size(); ++i) {
				assertTrue(urlsLoaded.get(i).equals(urlsExpected.get(i)));
			}
		}
	}
}
