package com.library.search;

import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.LibraryItem;

public class SearchRequest implements ISearchRequest {
	private SearchTermsAndPage termsAndPage = null;
	private List<SearchCategory> categoriesToSearch = new ArrayList<>();

	@Override
	public ISearchResults searchInDb() {
		SearchResults searchResults = SearchFactory.instance().makeSearchResults();
		for(SearchCategory category : categoriesToSearch) {
			List<LibraryItem> items = category.search(termsAndPage.getSearchTerms());
			searchResults.addSearchResultsForCategory(items);
		}	
		return searchResults;
	}
	
	@Override
	public boolean isNewSearchTerms(ISearchRequest other) {
		String thisTerms = termsAndPage.getSearchTerms();
		String otherTerms = other.getTermsAndPage().getSearchTerms();
		return !thisTerms.equals(otherTerms);
	}
	
	@Override
	public void addCategoryToSearchIn(SearchCategory categoryToSearch) {
		categoriesToSearch.add(categoryToSearch);
	}
	
	@Override
	public SearchTermsAndPage getTermsAndPage() {
		return termsAndPage;
	}
	
	@Override
	public void setTermsAndPage(SearchTermsAndPage termsAndPage) {
		this.termsAndPage = termsAndPage;
	}
}
