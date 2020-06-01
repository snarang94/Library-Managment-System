package com.library.dbCredentialTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.library.dbConnection.DatabaseCredentials;


public class DatabaseCredentialsTest {

	DatabaseCredentials databaseCredential = new DatabaseCredentials();
	
	@Test
	public void checkUserName() {
		
		String userName = databaseCredential.getUserName();
		assertEquals("CSCI5308_2_DEVINT_USER", userName);
	}
	
	@Test
	public void checkPassword()
	{
		String password = databaseCredential.getPassword();
		assertEquals("CSCI5308_2_DEVINT_2009",password);
	}
	
	@Test
	public void checkDatabaseName() {
		String databaseName = databaseCredential.getDatabaseName();
		assertEquals("CSCI5308_2_DEVINT",databaseName);
	}
	
	@Test
	public void checkServerName() {
		String serverName = databaseCredential.getServerName();
		assertEquals("db-5308.cs.dal.ca",serverName);
	}
	
	@Test
	public void checkPort()
	{
		String port = databaseCredential.getPort();
		assertEquals("3306",port);
	}
}
