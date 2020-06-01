package com.library.dao;

public enum MusicDAOEnums {

	QUERY_GET_MUSIC_BY_ID("SELECT * from music WHERE Item_ID = ?"),
	QUERY_GET_MUSICS_BY_CATEGORY("SELECT * from music WHERE Category LIKE ?"),
	QUERY_GET_MUSIC_CATEGORIES("SELECT Distinct Category from music"),
	QUERY_DELETE_MUSIC_BY_ID("DELETE from music WHERE Item_ID = ?"),
	QUERY_INSERT_MUSIC("INSERT INTO music (Category,Title,Artist,Record_Label,Availability) VALUES ( ?, ?, ?, ?, ?)"),
	QUERY_IS_DUPLICATE_MUSIC("SELECT * FROM music where Title=? and Artist=?"),
	QUERY_INCREASE_MUSIC_COUNT("update music set count = count + 1 where Item_ID = ?"),
	QUERY_UPDATE_AVAILABILITY("update music set Availability =? where Item_ID = ?"),
	QUERY_GET_CURRENT_AVAILABILITY_OF_MUSIC("Select Availability from music where Item_ID = ?");
	
	String query;

	private MusicDAOEnums(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
	
}
