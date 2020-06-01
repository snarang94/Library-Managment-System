package com.library.welcomePage;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.library.businessModelSetter.DisplaySetter;
import com.library.businessModels.Book;
import com.library.businessModels.Display;
import com.library.businessModels.LibraryItem;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.dao.DAOFactory;
import com.library.dao.ILibraryItemDAO;
import com.library.messages.Messages;

public class WelcomePageController implements IWelcomeController {
	private ILibraryItemDAO libraryFactory;

	enum typeEntity {
		latestBooks, latestMovies, latestMusic, favouriteBooks, favouriteMovies, favouriteMusic
	}

	public WelcomePageController() {
		DAOFactory factory = new DAOFactory();
		libraryFactory = factory.makeLibraryItemDAO();
	}

	public boolean isAdminAvailable() {
		return UserSessionDetail.getAdminAvailable();
	}

	public List<Book> getBookItems() throws SQLException {
		List<Book> books = null;
		books = libraryFactory.getLatestBooks();
		books = mapImagesIntoList(books, typeEntity.latestBooks);
		return books;
	}

	public List<Movie> getMovieItems() throws SQLException {
		List<Movie> movie = null;
		movie = libraryFactory.getLatestMovies();
		movie = mapImagesIntoList(movie, typeEntity.latestMovies);
		return movie;
	}

	public List<Music> getMusicItems() throws SQLException {
		List<Music> music = null;
		music = libraryFactory.getLatestMusic();
		music = mapImagesIntoList(music, typeEntity.latestMusic);
		return music;
	}

	public List<Book> getFavouriteBooks() throws SQLException {
		List<Book> favBooks = null;
		favBooks = libraryFactory.getFavouriteBooks();
		favBooks = mapImagesIntoList(favBooks, typeEntity.favouriteBooks);
		return favBooks;
	}

	@Override
	public List<Movie> getFavouriteMovies() throws SQLException {
		List<Movie> favMovies = null;
		favMovies = libraryFactory.getFavouriteMovies();
		favMovies = mapImagesIntoList(favMovies, typeEntity.favouriteMovies);
		return favMovies;
	}

	@Override
	public List<Music> getFavouriteMusic() throws SQLException {
		List<Music> favMusic = null;
		favMusic = libraryFactory.getFavouriteMusic();
		favMusic = mapImagesIntoList(favMusic, typeEntity.favouriteMusic);
		return favMusic;
	}

	private List mapImagesIntoList(List entity, typeEntity entityValue) {
		DisplaySetter displaySetter = new DisplaySetter();
		List<Display> displayList = null;
		if (entityValue.equals(typeEntity.latestBooks) || entityValue.equals(typeEntity.favouriteBooks)) {
			displayList = displaySetter.getBookDisplayObjects(entity);
		} else if (entityValue.equals(typeEntity.latestMovies) || entityValue.equals(typeEntity.favouriteMovies)) {
			displayList = displaySetter.getMovieDisplayObjects(entity);
		} else {
			displayList = displaySetter.getMusicDisplayObjects(entity);
		}
		for (int i = 0; i < entity.size(); i++) {
			((LibraryItem) entity.get(i)).setCoverImageUrl(displayList.get(i).getImage());
		}
		return entity;
	}

	public boolean getValFromRequestParam(HttpServletRequest request) {

		java.util.Enumeration<String> reqEnum = request.getParameterNames();
		while (reqEnum.hasMoreElements()) {
			String s = reqEnum.nextElement();
			if (s.equals("LoggedOut") && request.getParameter(s).equals("true")) {
				return true;
			}
		}
		return false;
	}

}
