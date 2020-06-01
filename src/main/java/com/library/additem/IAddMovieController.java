package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.businessModels.Movie;

public interface IAddMovieController {

	public AddItemMessagesEnum addMovieRecordInDatabase(Movie movie, MultipartFile coverImage);

	public List<String> getMovieCategories();
	
}
