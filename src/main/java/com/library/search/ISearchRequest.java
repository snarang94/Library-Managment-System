package com.library.search;

public interface ISearchRequest {
	public void addCategoryToSearchIn(SearchCategory categoryToSearch);
	public SearchTermsAndPage getTermsAndPage();
	public void setTermsAndPage(SearchTermsAndPage termsAndPage);
	public boolean isNewSearchTerms(ISearchRequest other);
	public ISearchResults searchInDb();
}
