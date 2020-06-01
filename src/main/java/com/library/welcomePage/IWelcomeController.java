package com.library.welcomePage;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public interface IWelcomeController {
	public List<Book> getBookItems() throws SQLException;

	public List<Movie> getMovieItems() throws SQLException;

	public List<Music> getMusicItems() throws SQLException;

	public List<Book> getFavouriteBooks() throws SQLException;

	public List<Movie> getFavouriteMovies() throws SQLException;

	public List<Music> getFavouriteMusic() throws SQLException;

	public boolean isAdminAvailable();

	public boolean getValFromRequestParam(HttpServletRequest request);
}
