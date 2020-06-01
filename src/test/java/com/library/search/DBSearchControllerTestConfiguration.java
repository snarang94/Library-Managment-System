package com.library.search;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("DBSearchControllerTest")
public class DBSearchControllerTestConfiguration {
	@Bean
	@Primary
	public SearchResultCoverImgProxy dbSearchController() {
		return Mockito.mock(SearchResultCoverImgProxy.class);
	}
}
