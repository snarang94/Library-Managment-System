package com.library.demo;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.library.localStorage.CoverImageLoaderBean;
import com.library.routes.LibraryRoutes;
import com.library.search.DBSeachControllerBean;
import com.library.search.SearchResultCoverImgProxyBean;

@SpringBootApplication
@ComponentScan(basePackageClasses = {LibraryRoutes.class, 
										DynamicResourcesConfiguration.class, 
										SearchResultCoverImgProxyBean.class,
										DBSeachControllerBean.class,
										CoverImageLoaderBean.class})
public class LibraryApplication {
	// source for this idea: https://stackoverflow.com/questions/36288643/serve-dynamically-changing-static-content-with-spring-boot
	public static String IMAGE_DIR;
	
	public static void main(String[] args) throws IOException {
		IMAGE_DIR = System.getProperty("user.dir") + File.separator + "dynamicContent" + File.separator;
		SpringApplication.run(LibraryApplication.class, args);
	}
}
