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

import com.library.businessModelSetter.IMusicSetter;
import com.library.businessModelSetter.MusicSetter;
import com.library.businessModels.LibraryItem;
import com.library.businessModels.Music;
import com.library.dbConnection.DatabaseConnection;
import com.library.search.MusicSearch;

public class MusicDAO implements IMusicDAO {

	private PreparedStatement preparedStatement;
	String query;
	Connection connection;
	IMusicSetter musicSetter = new MusicSetter();
	private static final Logger logger = LogManager.getLogger(MusicDAO.class);
	ResultSet resultSet;
	DatabaseConnection databaseConnection;

	public MusicDAO() {

		try {
			databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
			this.connection = databaseConnection.getConnection();

		} catch (Exception e) {
			logger.log(Level.ALL, "Unable to connect to database", e);
		}
	}

	@Override
	public Music getMusicById(int itemID) {

		Music music = new Music();
		this.connection = databaseConnection.getConnection();
		List<Music> musics = new ArrayList<>();
		query = MusicDAOEnums.QUERY_GET_MUSIC_BY_ID.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			musics = musicSetter.mapMusic(resultSet);
			music = musics.get(0);
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not fetch music with itemId["+itemID+"] from music table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return music;
	}

	@Override
	public List<Music> getMusicByCategory(String category) {

		this.connection = databaseConnection.getConnection();
		query = MusicDAOEnums.QUERY_GET_MUSICS_BY_CATEGORY.getQuery();
		List<Music> musicsByCategory = new ArrayList<Music>();

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "%" + category + "%");
			resultSet = preparedStatement.executeQuery();
			musicsByCategory = musicSetter.mapMusic(resultSet);
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :" +query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not fetch the list of music with category ["+category+"] from music table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return musicsByCategory;

	}

	@Override
	public int createMusic(Music music) {

		this.connection = databaseConnection.getConnection();
		String musicCategory = music.getCategory();
		String musicTitle = music.getTitle();
		String musicArtist = music.getArtist();
		String musicRecordLabel = music.getRecordLabel();
		int recentlyAddedMusicId = 0;
		int defaultAvailablity = 5;

		try {
			query = MusicDAOEnums.QUERY_INSERT_MUSIC.getQuery();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, musicCategory);
			preparedStatement.setString(2, musicTitle);
			preparedStatement.setString(3, musicArtist);
			preparedStatement.setString(4, musicRecordLabel);
			preparedStatement.setInt(5, defaultAvailablity);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				recentlyAddedMusicId = resultSet.getInt(1);
			}

			return recentlyAddedMusicId;

		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not create music with category["+musicCategory+"], title["+musicTitle+"] and artist["+musicArtist+"] entry in music table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return recentlyAddedMusicId;
	}


	@Override
	public Boolean deleteMusic(int itemID) {

		this.connection = databaseConnection.getConnection();
		
		try {
			query = MusicDAOEnums.QUERY_DELETE_MUSIC_BY_ID.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not delete music with itemId["+itemID+"] from music table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return false;
	}

	private String prepareSearchQuery(MusicSearch requestDetails, String searchTerms) {

		if (0 == searchTerms.length()) {
			logger.log(Level.ERROR, "No search terms are supplied");
			return null;
		}

		String query = "SELECT DISTINCT * FROM music WHERE ";
		String[] searchterms = searchTerms.split("\\s");
		for (String term : searchterms) {
			if (requestDetails.isSearchMusicAlbumName()) {
				query += "Title like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchMusicArtist()) {
				query += "Artist like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchMusicRecordLabel()) {
				query += "Record_Label like \"%" + term + "%\" or ";
			}
		}

		query = query.substring(0, query.length() - 4);
		return query;
	}

	@Override
	public List<LibraryItem> getMusicBySearchTerms(MusicSearch requestDetails, String searchTerms) {

		this.connection = databaseConnection.getConnection();
		List<LibraryItem> musics = new LinkedList<LibraryItem>();
		List<Music> tempMusics = new ArrayList<>();
		if (!requestDetails.isSearchInMusic()) {
			return musics;
		}
		String query = prepareSearchQuery(requestDetails, searchTerms);

		if (null == query) {
			return musics;
		}

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			tempMusics = musicSetter.mapMusic(resultSet);
			musics.addAll(tempMusics);
			return musics;
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to prepare SQL statement OR execute a query OR parse a query resultSet :"+query, e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return musics;
	}

	@Override
	public List<String> getMusicCategories() {
		this.connection = databaseConnection.getConnection();
		List<String> categories = new ArrayList<String>();
		query = MusicDAOEnums.QUERY_GET_MUSIC_CATEGORIES.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				categories.add(resultSet.getString("Category"));
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of Music Categories", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return categories;
	}

	@Override
	public int getAvailability(int itemID) {

		int musicsAvailable = 0;
		try {
			this.connection = databaseConnection.getConnection();
			query = MusicDAOEnums.QUERY_GET_CURRENT_AVAILABILITY_OF_MUSIC.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				musicsAvailable = resultSet.getInt("Availability");
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the availability of Music with itemId["+itemID+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return musicsAvailable;
	}

	public boolean checkMusicDuplicacy(Music music) {

		this.connection = databaseConnection.getConnection();
		String artistToBeAdded = music.getArtist();
		String titleToBeAdded = music.getTitle();
		boolean isMusicAvailable = false;

		query = MusicDAOEnums.QUERY_IS_DUPLICATE_MUSIC.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, titleToBeAdded);
			preparedStatement.setString(2, artistToBeAdded);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				isMusicAvailable = true;
			} else {
				isMusicAvailable = false;
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of Musics with artist ["+artistToBeAdded+"] and title ["+titleToBeAdded+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return isMusicAvailable;
	}

	@Override
	public Boolean increaseCount(int itemID) {
		Boolean countIncrease = false;
		try {
			this.connection = databaseConnection.getConnection();
			query = MusicDAOEnums.QUERY_INCREASE_MUSIC_COUNT.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.execute();
			countIncrease = true;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error increasing count of Music with itemId ["+itemID+"]", e);
		} finally {

			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return countIncrease;
	}

	@Override
	public void updateAvailability(int itemId, int udatedAvailability) {
		
		try {
			this.connection = databaseConnection.getConnection();
			query = MusicDAOEnums.QUERY_UPDATE_AVAILABILITY.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, udatedAvailability);
			preparedStatement.setInt(2, itemId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error updating availability of music with itemId ["+itemId+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		
	}
}
