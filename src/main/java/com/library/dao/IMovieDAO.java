package com.library.dao;

import java.util.List;

import com.library.businessModels.LibraryItem;
import com.library.businessModels.Movie;
import com.library.search.MovieSearch;

public interface IMovieDAO {
	public Movie getMovieById(int itemID);
	public List<Movie> getMoviesByCategory(String category);
	public int createMovie(Movie movie);
	public Boolean deleteMovie(int itemID);
	public List<LibraryItem> getMoviesBySearchTerms(MovieSearch requestDetails, String searchTerms);
	public List<String> getMovieCategories();
	public int getAvailability(int itemID); 
	public boolean checkMovieDuplicacy(Movie movie);
	public Boolean increaseCount(int itemID);
	public void updateAvailability(int itemId, int udatedAvailability);
}
