package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.browsePage.BrowseMovies;
import com.library.businessModels.Movie;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;
import com.library.routes.ILibraryFactory;
import com.library.routes.LibraryFactorySingleton;

public class AddMovieController implements IAddMovieController {

	private IDAOFactory factory;
	private int itemIdOfMovie;
	private IMovieDAO iMovieDAO;
	private ILibraryFactory iLibraryfactory;
	private LibraryFactorySingleton factorySingleton;
	private IItemCoverSetter coverSetter;
	private boolean isMovieCoverCreated, isDuplicateMovie;

	public AddMovieController() {

		factory = new DAOFactory();
		iMovieDAO = factory.makeMovieDAO();
		factorySingleton = LibraryFactorySingleton.instance();
		iLibraryfactory = factorySingleton.getFactory();

	}

	public AddItemMessagesEnum addMovieRecordInDatabase(Movie movie, MultipartFile movieCoverImage) {

		isDuplicateMovie = iMovieDAO.checkMovieDuplicacy(movie);
		if (isDuplicateMovie) {
			return AddItemMessagesEnum.ERROR_DUPLICATE_MOVIE;
		}

		itemIdOfMovie = iMovieDAO.createMovie(movie);
		if (itemIdOfMovie == 0) {

			return AddItemMessagesEnum.ERROR_MOVIE_CAN_NOT_BE_CREATED;

		} else {
			coverSetter = iLibraryfactory.makeItemCoverSetter();
			isMovieCoverCreated = coverSetter.isCoverAddedToDatabase(itemIdOfMovie, movieCoverImage);
			if(isMovieCoverCreated)
			{
				return AddItemMessagesEnum.SUCCESS_MOVIE;
			}
			else
			{
				iMovieDAO.deleteMovie(itemIdOfMovie);
				return AddItemMessagesEnum.ERROR_MOVIE_CAN_NOT_BE_CREATED;
			}

		}
	
	}

	@Override
	public List<String> getMovieCategories() {
		
		BrowseMovies browseMovies = new BrowseMovies();
		List<String> movieCategories = browseMovies.getCategories();
		
		return movieCategories;
	}

}
