package com.library.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class DatabaseConnection {
	private static DatabaseConnection databaseConnectionInstance = null;
	private Connection connection = null;
	private String databaseName;
	private String username;
	private String password;
	private String serverName;
	private String databaseURL;
	
	public DatabaseConnection() {
		DatabaseCredentials databaseCredentials = new DatabaseCredentials();
		databaseName = databaseCredentials.getDatabaseName();
		username = databaseCredentials.getUserName();
		password = databaseCredentials.getPassword();
		serverName = databaseCredentials.getServerName();
		databaseURL = "jdbc:mysql://" + serverName + "/" + databaseName;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(databaseURL, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			if(connection.isClosed())
			{
				Class.forName("com.mysql.jdbc.Driver");
				this.connection = DriverManager.getConnection(databaseURL, username, password);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return this.connection;
	}

	public static DatabaseConnection getDatabaseConnectionInstance() {
		if (null == databaseConnectionInstance) {
			databaseConnectionInstance = new DatabaseConnection();
		}
		return databaseConnectionInstance;
	}
	
	public static Connection getConn()
	{
		return getDatabaseConnectionInstance().getConnection();
	}
	
	public void closeConnection(ResultSet resultSet, java.sql.PreparedStatement preparedStatement)
	{
		if(null != resultSet) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				
			}
		}
		if(null != preparedStatement) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				
			}
		}
		try {
			if(!connection.isClosed())
			{
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection()
	{
		try {
			if(!connection.isClosed())
			{
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnection(PreparedStatement preparedStatement)
	{
		if(null != preparedStatement) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				
			}
		}
	}
}
