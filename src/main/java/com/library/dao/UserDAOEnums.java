package com.library.dao;

public enum UserDAOEnums {

	QUERY_CHECK_PASSWORD("SELECT Password from user_info WHERE Email = ?"),
	QUERY_GET_EMAIL_RELATED_PASSWORD("SELECT Password from user_info WHERE Email = ?"),
	QUERY_CHANGE_PASSWORD("UPDATE user_info SET Password = ? WHERE Email = ?"),
	QUERY_REGISTER_USER("INSERT INTO user_info (User_name,Phone_Number,Email,Password) VALUES (?,?,?,?)"),
	QUERY_DOES_EMAILID_EXISTS("SELECT Email from user_info WHERE Email = ? LIMIT 1");
	
	String query;

	private UserDAOEnums(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
	
}
