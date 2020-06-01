package com.library.businessModelSetter;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public class DetailsSetter implements IDetailsSetter {
	
	public String getBookDetails(Book book)
	{
		String details = "";
		details = details + "Author: " + book.getAuthor() + "<br />";
		details = details + "Publisher: " + book.getPublisher() + "<br />";
		details = details + "Description: " + book.getDescription();
		return details;
	}
	
	public String getMovieDetails(Movie movie)
	{
		String details = "";
		details = details + "Director: " + movie.getDirector() + "<br />";
		details = details + "Description: " + movie.getDescription() + "<br />";
		return details;
	}
	
	public String getMusicDetails(Music music)
	{
		String details = "";
		details = details + "Artist: " + music.getArtist() + "<br />";
		details = details + "Record Label: " + music.getRecordLabel() + "<br />";
		return details;
	}
	
}

