package com.library.controllers;
//
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.library.businessModels.LibraryItem;
import com.library.demo.LibraryApplication;
import com.library.search.IDBSearchController;
import com.library.search.SearchFactory;
import com.library.search.SearchResults;
import com.library.signIn.AuthenticatedUsers;


@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes={LibraryApplication.class})
public class LibraryControllerTest {

    @Inject
    private MockMvc mockMvc;

    @MockBean
    private IDBSearchController dbSearchControllerMock;
    
    private MockHttpSession mockHttpSessionAuthenticated;
    private MockHttpSession mockHttpSessionNotAuthenticated;
    
    @Before
    public void setUp() {
        Mockito.reset(dbSearchControllerMock);
        mockHttpSessionNotAuthenticated = new MockHttpSession();
        mockHttpSessionAuthenticated = new MockHttpSession();
        AuthenticatedUsers.instance().addAuthenticatedUser(mockHttpSessionAuthenticated, "asd@mail.com");
     }

//    @SuppressWarnings("unchecked")
	@Test
    public void authenticatedUserBowseAdvancedSearchPageWithPOST() throws Exception {
    	SearchResults results = SearchFactory.instance().makeSearchResults();
    	
    	List<LibraryItem> booksFoundInSearch = new LinkedList<LibraryItem>();
    	results.addSearchResultsForCategory(booksFoundInSearch);
    	List<LibraryItem> moviesFoundInSearch = new LinkedList<LibraryItem>();
    	results.addSearchResultsForCategory(moviesFoundInSearch);
    	List<LibraryItem> musicFoundInSearch = new LinkedList<LibraryItem>();
    	results.addSearchResultsForCategory(musicFoundInSearch);
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/advancedSearch");
        request.session(mockHttpSessionAuthenticated);
        
        when(dbSearchControllerMock.search(any(), eq(mockHttpSessionAuthenticated))).thenReturn(results);
       
        
		this.mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(view().name("AdvancedSearchResultsPage"))
			.andExpect(model().attribute("searchResults", hasProperty("searchResultsPerCategory", hasSize(3))));
    }
	
	@Test
    public void authenticatedUserBrowseAdvancedSearchPageWithGET() throws Exception {
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/advancedSearch");
        request.session(mockHttpSessionAuthenticated);
        
		this.mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(view().name("AdvancedSearchPage"))
			;
    }   
    
    @Ignore // remove @Ignore once we will remove the dummy authenticated user in LibraryController 
	@Test
    public void notAuthenticatedUserBrowseAdvancedSearchPageWithGET() throws Exception {
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/advancedSearch");
        request.session(mockHttpSessionNotAuthenticated);
        
		this.mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(view().name("NoAccessToNonAuthenticated"))
			;
    }
    
    @Test
    public void anyUserBrowseBasicSearchPagePOST() throws Exception {       
    	SearchResults searchResult = SearchFactory.instance().makeSearchResults();
    	
    	List<LibraryItem> booksFoundInSearch = new LinkedList<LibraryItem>();
    	searchResult.addSearchResultsForCategory(booksFoundInSearch);
    	List<LibraryItem> moviesFoundInSearch = new LinkedList<LibraryItem>();
    	searchResult.addSearchResultsForCategory(moviesFoundInSearch);
    	List<LibraryItem> musicFoundInSearch = new LinkedList<LibraryItem>();
    	searchResult.addSearchResultsForCategory(musicFoundInSearch);
        
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/basicSearch");
        request.session(mockHttpSessionAuthenticated);
        
        when(dbSearchControllerMock.search(any(), eq(mockHttpSessionAuthenticated))).thenReturn(searchResult);
        
		this.mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(view().name("BasicSearchResultsPage"))
			;
    }
    
}

