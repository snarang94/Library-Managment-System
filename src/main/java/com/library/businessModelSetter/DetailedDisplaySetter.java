package com.library.businessModelSetter;

import com.library.businessModels.Book;
import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public class DetailedDisplaySetter implements IDetailedDisplaySetter {

	private IDetailsSetter detailsSetter;
	private DisplayDetailed displayDetailed;
	private static String bookItem = "Book";
	private static String musicItem = "Music";
	private static String movieItem = "Movie";
	
	public DetailedDisplaySetter() {
		detailsSetter = new DetailsSetter();
		displayDetailed = new DisplayDetailed();
	}

	@Override
	public DisplayDetailed makeDetailedBook(Book book) {
		displayDetailed.setItemID(book.getItemID());
		displayDetailed.setTitle(book.getTitle());
		String details = detailsSetter.getBookDetails(book);
		displayDetailed.setDetails(details);
		displayDetailed.setItemType(bookItem);
		return displayDetailed;
	}

	@Override
	public DisplayDetailed makeDetailedMovie(Movie movie) {
		displayDetailed.setItemID(movie.getItemID());
		displayDetailed.setTitle(movie.getTitle());
		String details = detailsSetter.getMovieDetails(movie);
		displayDetailed.setDetails(details);
		displayDetailed.setItemType(movieItem);
		return displayDetailed;
	}

	@Override
	public DisplayDetailed makeDetailedMusic(Music music) {
		displayDetailed.setItemID(music.getItemID());
		displayDetailed.setTitle(music.getTitle());
		String details = detailsSetter.getMusicDetails(music);
		displayDetailed.setDetails(details);
		displayDetailed.setItemType(musicItem);
		return displayDetailed;
	}

}
