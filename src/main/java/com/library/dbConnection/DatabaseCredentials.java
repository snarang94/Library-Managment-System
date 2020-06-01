package com.library.dbConnection;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseCredentials { 
	
	private String username;
	private String password;
	private String databaseName;
	private String serverName;
	private String port;
	
	public DatabaseCredentials() {
		
		try {
			Properties DatabaseProperties = new Properties();
			String fileName = "DatabaseConfig.properties";
			InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
			DatabaseProperties.load(input);
			username = DatabaseProperties.getProperty("UserName");
			password = DatabaseProperties.getProperty("Password");
			databaseName = DatabaseProperties.getProperty("DatabaseName");
			serverName = DatabaseProperties.getProperty("ServerName");
			port = DatabaseProperties.getProperty("Port");
		} catch (IOException e)
		{
			System.out.print("Unable to access database Configaration file");
		}
	}
	
	public String getUserName()
	{
		return this.username;
	}
	public String getPassword()
	{
		return this.password;
	}
	public String getServerName()
	{
		return this.serverName;
	}
	public String getDatabaseName()
	{
		return this.databaseName;
	}
	public String getPort()
	{
		return this.port;
	}
}
