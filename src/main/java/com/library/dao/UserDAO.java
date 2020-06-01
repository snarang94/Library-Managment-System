package com.library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.User;
import com.library.dbConnection.DatabaseConnection;

public class UserDAO implements IUserDAO {

	Connection connection;
	private PreparedStatement preparedStatement;
	String query;
	private static final Logger logger = LogManager.getLogger(UserDAO.class);
	DatabaseConnection databaseConnection;
	ResultSet resultSet;

	public UserDAO() {
		try {
			databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
			this.connection = databaseConnection.getConnection();
		} catch (Exception e) {
			logger.log(Level.ALL, "Unable to connect to database", e);
		}
	}

	@Override
	public Boolean checkPassword(String emailAddress, String Password) {
		query = UserDAOEnums.QUERY_CHECK_PASSWORD.getQuery();
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emailAddress);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			String databasePassword = resultSet.getString("Password");
			Boolean doesPasswordMatch = databasePassword.equals(Password);
			return doesPasswordMatch;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not fetch password from user info for email address ["+emailAddress+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return false;
	}

	@Override
	public String getEmailRelatedPassword(String emailAddress) {
		query = UserDAOEnums.QUERY_GET_EMAIL_RELATED_PASSWORD.getQuery();
		String databasePassword = "";
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emailAddress);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			databasePassword = resultSet.getString("Password");
			return databasePassword;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not fetch password from user info with email ["+emailAddress+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return databasePassword;
	}

	@Override
	public Boolean changePassword(String emailAddress, String password) {
		query = UserDAOEnums.QUERY_CHANGE_PASSWORD.getQuery();
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, emailAddress);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of:"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not update password for the specific user emailid with ["+emailAddress+"]", e);
		} finally {
			databaseConnection.closeConnection((com.mysql.jdbc.PreparedStatement) preparedStatement);
		}
		return false;
	}

	@Override
	public Boolean registerUser(User user) {
		query = UserDAOEnums.QUERY_REGISTER_USER.getQuery();
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getFullName());
			preparedStatement.setString(2, user.getPhoneNumber());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax of:"+query, e);

		} catch (Exception e) {
			logger.log(Level.ALL, "Can not insert a record with email address["+user.getEmail()+"] in user info table", e);
		} finally {
			databaseConnection.closeConnection((com.mysql.jdbc.PreparedStatement) preparedStatement);
		}
		return false;
	}

	@Override
	public Boolean checkEmailIdExist(String emailID) {
		boolean result = false;
		query = UserDAOEnums.QUERY_DOES_EMAILID_EXISTS.getQuery();
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, emailID);
			resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet);
			if (!resultSet.next()) {
				result = false;
				System.out.println("no data");
			} else {
				result = true;
			}

		} catch (SQLException e) {
			result = false;
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);

		} catch (Exception e) {
			result = false;
			logger.log(Level.ALL, "Can not fetch a record with email["+emailID+"] from user info table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return result;
	}
}
