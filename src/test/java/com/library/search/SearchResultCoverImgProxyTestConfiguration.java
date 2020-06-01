package com.library.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.library.localStorage.CoverImageLoader;

@Profile("CoverImgProxyTest")
public class SearchResultCoverImgProxyTestConfiguration {
	@Bean
	@Primary
	public CoverImageLoader getCoverImageLoader() {
		return new MockCoverImageLoader();
	}
}
