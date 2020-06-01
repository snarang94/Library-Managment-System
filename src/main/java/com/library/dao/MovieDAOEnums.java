package com.library.dao;

public enum MovieDAOEnums {
	
	QUERY_GET_MOVIE_BY_ID("SELECT * from movie WHERE Item_ID = ?"),
	QUERY_GET_MOVIES_BY_CATEGORY("SELECT * from movie WHERE Category LIKE ?"),
	QUERY_GET_MOVIE_CATEGORIES("SELECT Distinct Category from movie"),
	QUERY_DELETE_MOVIE_BY_ID("DELETE from movie WHERE Item_ID = ?"),
	QUERY_INSERT_MOVIE("INSERT INTO movie (Category,Title,Director,Description,Availability) VALUES (?, ?, ?, ?, ?)"),
	QUERY_IS_DUPLICATE_MOVIE("SELECT * FROM movie where Title=? and Director=?"),
	QUERY_INCREASE_MOVIE_COUNT("update movie set count = count + 1 where Item_ID = ?"),
	QUERY_UPDATE_AVAILABILITY("update movie set Availability =? where Item_ID = ?"),
	QUERY_GET_CURRENT_AVAILABILITY_OF_MOVIE("Select Availability from movie where Item_ID = ?");
	
	String query;

	private MovieDAOEnums(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

}
