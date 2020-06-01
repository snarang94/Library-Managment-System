package com.library.businessModelSetter;

import java.util.List;

import com.library.businessModels.Book;
import com.library.businessModels.Display;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public interface IDisplaySetter {
	public List<Display> getBookDisplayObjects(List<Book> books);

	public List<Display> getMovieDisplayObjects(List<Movie> movie);

	public List<Display> getMusicDisplayObjects(List<Music> music);
}
