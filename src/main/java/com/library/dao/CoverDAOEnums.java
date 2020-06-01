package com.library.dao;

public enum CoverDAOEnums {

	QUERY_SELECT_COVER_BY_ITEM_ID("SELECT * FROM covers WHERE Item_ID = ?"),
	QUERY_INSERT_COVER_BY_ITEM_ID("INSERT INTO covers (Item_ID,Cover_Blob,File_Extension) Values (?,?,?)"),
	QUERY_DELETE_COVER_BY_ITEM_ID("DELETE FROM covers WHERE Item_ID = ?");
	
	String query;

	private CoverDAOEnums(String query) {
		
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
}
