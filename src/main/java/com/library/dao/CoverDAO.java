package com.library.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModelSetter.CoverSetter;
import com.library.businessModels.Cover;
import com.library.dbConnection.DatabaseConnection;

public class CoverDAO implements ICoverDAO {

	private static final Logger logger = LogManager.getLogger(CoverDAO.class);
	private PreparedStatement preparedStatement;
	private Connection dbConnection;
	private CoverSetter coverMapper = new CoverSetter();
	DatabaseConnection databaseConnection;
	ResultSet resultSet;
	String query;

	public CoverDAO() {
		databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
	}

	@Override
	public Cover getCoverByID(int itemID) {
		dbConnection = databaseConnection.getConnection();
		String query = CoverDAOEnums.QUERY_SELECT_COVER_BY_ITEM_ID.getQuery();
		try {
			preparedStatement = dbConnection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return coverMapper.setCover(resultSet);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Check the SQL syntax of :" + query, e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return null;
	}

	@Override
	public boolean createCoverByID(int itemID, Blob coverBlob, String fileExtension) {

		dbConnection = databaseConnection.getConnection();
		query = CoverDAOEnums.QUERY_INSERT_COVER_BY_ITEM_ID.getQuery();
		try {
			preparedStatement = dbConnection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.setBlob(2, coverBlob);
			preparedStatement.setString(3, fileExtension);
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :" + query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Cover can not be created for itemID [" + itemID + "] ", e);

		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return false;
	}

	@Override
	public boolean deleteBlobByID(int itemID) {

		dbConnection = databaseConnection.getConnection();
		query = CoverDAOEnums.QUERY_DELETE_COVER_BY_ITEM_ID.getQuery();
		try {
			preparedStatement = dbConnection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.executeUpdate();

			return true;
		} catch (SQLException e) {

			logger.log(Level.ERROR, "Check the SQL syntax of:" + query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Cover can not be deleted for itemID [" + itemID + "] ", e);

		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}

		return false;
	}
}
