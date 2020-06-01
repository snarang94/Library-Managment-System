package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModelSetter.IMovieSetter;
import com.library.businessModelSetter.MovieSetter;
import com.library.businessModels.LibraryItem;
import com.library.businessModels.Movie;
import com.library.dbConnection.DatabaseConnection;
import com.library.search.MovieSearch;

public class MovieDAO implements IMovieDAO {

	private PreparedStatement preparedStatement;
	String query;
	Connection connection;
	IMovieSetter movieSetter = new MovieSetter();
	private static final Logger logger = LogManager.getLogger(MovieDAO.class);
	DatabaseConnection databaseConnection;
	ResultSet resultSet;

	public MovieDAO() {

		try {
			databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
			this.connection = databaseConnection.getConnection();
		} catch (Exception e) {

			logger.log(Level.ALL, "Unable to connect to database", e);
		}
	}

	@Override
	public Movie getMovieById(int itemID) {
		
		this.connection = databaseConnection.getConnection();
		List<Movie> movies = new ArrayList<Movie>();
		Movie movie = new Movie();
		query = MovieDAOEnums.QUERY_GET_MOVIE_BY_ID.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			movies = movieSetter.mapMovie(resultSet);
			movie = movies.get(0);
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {

			logger.log(Level.ALL, "Movie with itemId ["+itemID+"] not found in movie table", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return movie;
	}

	@Override
	public List<Movie> getMoviesByCategory(String category) {

		this.connection = databaseConnection.getConnection();
		query = MovieDAOEnums.QUERY_GET_MOVIES_BY_CATEGORY.getQuery();
		List<Movie> moviesByCategory = new ArrayList<Movie>();

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + category + "%");
			resultSet = preparedStatement.executeQuery();
			moviesByCategory = movieSetter.mapMovie(resultSet);

		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of movies with category["+category+"] in movie table", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return moviesByCategory;
	}

	@Override
	public int createMovie(Movie movie) {
		
		this.connection = databaseConnection.getConnection();
		int recentlyAddedMovieId = 0;
		String movieCategory = movie.getCategory();
		String movieTitle = movie.getTitle();
		String movieDirector = movie.getDirector();
		String movieDescription = movie.getDescription();
		int defaultAvailablity = 5;

		try {
			query = MovieDAOEnums.QUERY_INSERT_MOVIE.getQuery();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, movieCategory);
			preparedStatement.setString(2, movieTitle);
			preparedStatement.setString(3, movieDirector);
			preparedStatement.setString(4, movieDescription);
			preparedStatement.setInt(5, defaultAvailablity);
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				recentlyAddedMovieId = resultSet.getInt(1);
			}

			return recentlyAddedMovieId;

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not insert movie with title ["+movieTitle+"] , director["+movieDirector+"] into database", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return recentlyAddedMovieId;
	}

	@Override
	public Boolean deleteMovie(int itemID) {

		this.connection = databaseConnection.getConnection();
		

		try {
			query = MovieDAOEnums.QUERY_DELETE_MOVIE_BY_ID.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.executeUpdate();
			return true;

		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not delete movie with itemId ["+itemID+"]", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return false;
	}

	private String prepareSearchQuery(MovieSearch requestDetails, String searchTerms) {

		this.connection = databaseConnection.getConnection();
		if (0 == searchTerms.length()) {
			logger.log(Level.ERROR, "No search terms are supplied");
			return null;
		}

		String query = "SELECT DISTINCT * FROM movie WHERE ";
		String[] searchterms = searchTerms.split("\\s");
		for (String term : searchterms) {
			if (requestDetails.isSearchMovieTitle()) {
				query += "Title like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchMovieDirector()) {
				query += "Director like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchMovieDescription()) {
				query += "Description like \"%" + term + "%\" or ";
			}
		}
		
		query = query.substring(0, query.length() - 4);
		return query;
	}

	@Override
	public List<LibraryItem> getMoviesBySearchTerms(MovieSearch requestDetails, String searchTerms) {
		
		this.connection = databaseConnection.getConnection();
		List<Movie> tempMovie = new ArrayList<>();
		List<LibraryItem> movies = new LinkedList<LibraryItem>();
		if(!requestDetails.isSearchInMovies()) {
			return movies;
		}
		String query = prepareSearchQuery(requestDetails, searchTerms);
		
		if(null == query) {
			return movies;
		}

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			tempMovie = movieSetter.mapMovie(resultSet);
			movies.addAll(tempMovie);
			return movies;
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to prepare SQL statement OR execute a query OR parse a query resultSet", e);
		}
		finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return movies;
	}

	
	@Override
	public List<String> getMovieCategories()
	{
		this.connection = databaseConnection.getConnection();
		List<String> categories = new ArrayList<String>();
		query = MovieDAOEnums.QUERY_GET_MOVIE_CATEGORIES.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				categories.add(resultSet.getString("Category"));
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of:"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of Movie Categories from movie table", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return categories;
	}
	
	@Override
	public int getAvailability(int itemID) {

		int moviesAvailable = 0;
		try {
			this.connection = databaseConnection.getConnection();
			query = MovieDAOEnums.QUERY_GET_CURRENT_AVAILABILITY_OF_MOVIE.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) 
			{
				moviesAvailable = resultSet.getInt("Availability");
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of : "+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the availability of Moviewith itemId : "+itemID, e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}

		return moviesAvailable;
	}

	public boolean checkMovieDuplicacy(Movie movie) {
		
		this.connection = databaseConnection.getConnection();
		String directorToBeAdded = movie.getDirector();
		String titleToBeAdded = movie.getTitle();
		boolean isMovieAvailable = false;

		query = MovieDAOEnums.QUERY_IS_DUPLICATE_MOVIE.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, titleToBeAdded);
			preparedStatement.setString(2, directorToBeAdded);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				isMovieAvailable = true;
			} else {
				isMovieAvailable = false;
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of : "+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Failed to check movie duplication in movie table for movie title ["+titleToBeAdded+"] and director ["+directorToBeAdded+"]", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return isMovieAvailable;
	}

	@Override
	public Boolean increaseCount(int itemID) {
		
		Boolean countIncrease = false;
		try {
			this.connection = databaseConnection.getConnection();
			query = MovieDAOEnums.QUERY_INCREASE_MOVIE_COUNT.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.execute();
			countIncrease = true;
		}
		catch (SQLException e)
		{
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		}
		catch (Exception e)
		{
			logger.log(Level.ALL, "Error increasing count of Movie with itemId ["+itemID+"]", e);
		}
		finally
		{
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return countIncrease;
	}

	public void updateAvailability(int itemId, int udatedAvailability) {

		try {
			this.connection = databaseConnection.getConnection();
			query = MovieDAOEnums.QUERY_UPDATE_AVAILABILITY.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, udatedAvailability);
			preparedStatement.setInt(2, itemId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error updating availability of movie with itemId ["+itemId+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}

	}

}
