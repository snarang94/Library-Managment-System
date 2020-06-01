package com.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.dbConnection.DatabaseConnection;
import com.library.mockDB.WelcomePageMocked;
import com.library.routes.ILibraryFactory;
import com.library.routes.LibraryFactorySingleton;
import com.library.welcomePage.IWelcomeController;
import com.library.welcomePage.UserSessionDetail;

@RunWith(SpringRunner.class)
public class WelcomePageTest {
	private static WelcomePageMocked welcomePageMocked;
	private static ILibraryFactory factory = null;
	private static IWelcomeController wlcmCntrl = null;

	@BeforeClass
	public static void initializer() {

		welcomePageMocked = new WelcomePageMocked();
		factory = LibraryFactorySingleton.instance().getFactory();
		wlcmCntrl = factory.welcomePage();
	}

	@Test
	public void testAdminAvailable() {
		welcomePageMocked.adminInitiated();
		boolean isAdmin = UserSessionDetail.getAdminAvailable();
		assertEquals(isAdmin, true);
	}

	@Test
	public void testgetBookItems() {
		List<Book> book = welcomePageMocked.initiateLatestBookMock();
		assertNotNull(book);
		try {
			List<Book> listOfBooks = wlcmCntrl.getBookItems();
			for (int i = 0; i < wlcmCntrl.getBookItems().size(); i++) {
				assertEquals(listOfBooks.get(i).getTitle(), book.get(i).getTitle());
				assertEquals(listOfBooks.get(i).getAuthor(), book.get(i).getAuthor());
				assertEquals(listOfBooks.get(i).getCategory(), book.get(i).getCategory());
				assertEquals(listOfBooks.get(i).getDescription(), book.get(i).getDescription());
				assertEquals(listOfBooks.get(i).getItemID(), book.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}

	@Test
	public void testgetMoviesItems() {
		List<Movie> movies = welcomePageMocked.initiateLatestMovieMock();
		assertNotNull(movies);
		try {
			List<Movie> listOfMovies = wlcmCntrl.getMovieItems();
			for (int i = 0; i < listOfMovies.size(); i++) {
				assertEquals(listOfMovies.get(i).getTitle(), movies.get(i).getTitle());
				assertEquals(listOfMovies.get(i).getCategory(), movies.get(i).getCategory());
				assertEquals(listOfMovies.get(i).getDescription(), movies.get(i).getDescription());
				assertEquals(listOfMovies.get(i).getItemID(), movies.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}

	@Test
	public void testgetMusicItems() {
		List<Music> music = welcomePageMocked.initiateLatestMusicMock();
		assertNotNull(music);
		try {
			List<Music> listOfMovies = wlcmCntrl.getMusicItems();
			for (int i = 0; i < listOfMovies.size(); i++) {
				assertEquals(listOfMovies.get(i).getTitle(), music.get(i).getTitle());
				assertEquals(listOfMovies.get(i).getCategory(), music.get(i).getCategory());
				assertEquals(listOfMovies.get(i).getDescription(), music.get(i).getDescription());
				assertEquals(listOfMovies.get(i).getItemID(), music.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}

	@Test
	public void testgetFavBookItems() {
		List<Book> book = welcomePageMocked.initiateFavBookMock();
		assertNotNull(book);
		try {
			List<Book> listOfBooks = wlcmCntrl.getFavouriteBooks();
			for (int i = 0; i < wlcmCntrl.getBookItems().size(); i++) {
				assertEquals(listOfBooks.get(i).getTitle(), book.get(i).getTitle());
				assertEquals(listOfBooks.get(i).getAuthor(), book.get(i).getAuthor());
				assertEquals(listOfBooks.get(i).getCategory(), book.get(i).getCategory());
				assertEquals(listOfBooks.get(i).getDescription(), book.get(i).getDescription());
				assertEquals(listOfBooks.get(i).getItemID(), book.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}

	@Test
	public void testgetFavMovieItems() {
		List<Movie> movies = welcomePageMocked.initiateFavMoviesMock();
		assertNotNull(movies);
		try {
			List<Movie> listOfMovies = wlcmCntrl.getFavouriteMovies();
			for (int i = 0; i < listOfMovies.size(); i++) {
				assertEquals(listOfMovies.get(i).getTitle(), movies.get(i).getTitle());
				assertEquals(listOfMovies.get(i).getCategory(), movies.get(i).getCategory());
				assertEquals(listOfMovies.get(i).getDescription(), movies.get(i).getDescription());
				assertEquals(listOfMovies.get(i).getItemID(), movies.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}

	@Test
	public void testgetFavMusicItems() {
		List<Music> music = welcomePageMocked.initiatefavMusicMock();
		assertNotNull(music);
		try {
			List<Music> listOfMovies = wlcmCntrl.getFavouriteMusic();
			for (int i = 0; i < listOfMovies.size(); i++) {
				assertEquals(listOfMovies.get(i).getTitle(), music.get(i).getTitle());
				assertEquals(listOfMovies.get(i).getCategory(), music.get(i).getCategory());
				assertEquals(listOfMovies.get(i).getDescription(), music.get(i).getDescription());
				assertEquals(listOfMovies.get(i).getItemID(), music.get(i).getItemID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection databaseConnection = new DatabaseConnection();
			databaseConnection.closeConnection();
		}
		assertNotNull(wlcmCntrl);
	}
}
