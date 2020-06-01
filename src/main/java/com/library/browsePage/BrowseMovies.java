package com.library.browsePage;

import java.util.List;

import com.library.businessModelSetter.DisplaySetter;
import com.library.businessModelSetter.IDisplaySetter;
import com.library.businessModels.Display;
import com.library.businessModels.Movie;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;

public class BrowseMovies implements IBrowseDisplayObjects{
	
	private IMovieDAO movieDAO;
	private String itemType;
	private static String movie = "Movie";
	
	public BrowseMovies()
	{
		IDAOFactory factory = new DAOFactory();
		movieDAO = factory.makeMovieDAO();	
		itemType = movie;
	}

	@Override
	public List<Display> itemsByCategory(String category) {
		IDisplaySetter displaySetter = new DisplaySetter();
		List<Movie> movies = movieDAO.getMoviesByCategory(category);
		List<Display> displayMovies = displaySetter.getMovieDisplayObjects(movies);
		return displayMovies;
	}

	@Override
	public List<String> getCategories() {
		List<String> categories = movieDAO.getMovieCategories();
		return categories;
	}

	@Override
	public String getItemType() {
		return itemType;
	}

}
