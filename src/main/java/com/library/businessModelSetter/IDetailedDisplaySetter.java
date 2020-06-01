package com.library.businessModelSetter;

import com.library.businessModels.Book;
import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public interface IDetailedDisplaySetter {
	public DisplayDetailed makeDetailedBook(Book book);

	public DisplayDetailed makeDetailedMovie(Movie movie);

	public DisplayDetailed makeDetailedMusic(Music music);
}
