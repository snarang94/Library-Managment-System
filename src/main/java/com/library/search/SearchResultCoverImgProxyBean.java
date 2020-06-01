package com.library.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchResultCoverImgProxyBean {
	@Bean
	public ISearchResultCoverImgProxy getCoverImageProxyInstance() {
		return new SearchResultCoverImgProxy();
	}
}
