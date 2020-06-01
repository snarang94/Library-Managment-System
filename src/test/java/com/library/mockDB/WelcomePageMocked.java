package com.library.mockDB;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.dao.DAOFactory;
import com.library.dao.ILibraryItemDAO;
import com.library.welcomePage.UserSessionDetail;

public class WelcomePageMocked {
	private DAOFactory factory = null;
	private ILibraryItemDAO libraryFactory = null;
	private static final Logger logger = LogManager.getLogger(WelcomePageMocked.class);

	public WelcomePageMocked() {
		factory = new DAOFactory();
		DAOFactory factory = new DAOFactory();
		libraryFactory = factory.makeLibraryItemDAO();
	}

	public List<Movie> initiateLatestMovieMock() {
		List<Movie> movie = null;
		try {
			movie = libraryFactory.getLatestMovies();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest movies.",e);
		}
		return movie;
	}

	public List<Book> initiateLatestBookMock() {
		List<Book> book = null;
		try {
			book = libraryFactory.getLatestBooks();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest books.");
		}
		return book;
	}

	public List<Music> initiateLatestMusicMock() {
		List<Music> music = null;
		try {
			music = libraryFactory.getLatestMusic();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest music.");
		}
		return music;
	}

	public List<Book> initiateFavBookMock() {
		List<Book> book = null;
		try {
			book = libraryFactory.getFavouriteBooks();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest books.");
		}
		return book;
	}

	public List<Movie> initiateFavMoviesMock() {
		List<Movie> movie = null;
		try {
			movie = libraryFactory.getFavouriteMovies();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest movies.");
		}
		return movie;
	}

	public List<Music> initiatefavMusicMock() {
		List<Music> music = null;
		try {
			music = libraryFactory.getFavouriteMusic();
		} catch (SQLException e) {
			logger.log(Level.ALL, "Mock data got an error in getting latest music.");
		}
		return music;
	}

	public void adminInitiated() {
		UserSessionDetail.setAvailableAdmin(true);
	}
}
