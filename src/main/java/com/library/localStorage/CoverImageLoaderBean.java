package com.library.localStorage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoverImageLoaderBean {
	@Bean
	public ICoverImageLoader getCoverImageLoaderInstance() {
		return new CoverImageLoader();
	}
}
