package com.library.businessModelSetter;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public interface IDetailsSetter {

	public String getBookDetails(Book book);

	public String getMovieDetails(Movie movie);

	public String getMusicDetails(Music music);
}
