package com.library.dao;

import java.sql.SQLException;
import java.util.List;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public interface ILibraryItemDAO {
	public List<Book> getLatestBooks() throws SQLException;

	public List<Movie> getLatestMovies() throws SQLException;

	public List<Music> getLatestMusic() throws SQLException;

	public List<Book> getFavouriteBooks() throws SQLException;

	public List<Movie> getFavouriteMovies() throws SQLException;

	public List<Music> getFavouriteMusic() throws SQLException;
}
