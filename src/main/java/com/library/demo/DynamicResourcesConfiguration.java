package com.library.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// source for this idea: https://stackoverflow.com/questions/36288643/serve-dynamically-changing-static-content-with-spring-boot
@Configuration
public class DynamicResourcesConfiguration implements WebMvcConfigurer  {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dynamicContent/**").addResourceLocations("file:" + LibraryApplication.IMAGE_DIR);
    }
}

