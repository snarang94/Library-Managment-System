package com.library.dao;

public enum UserItemDAOEnums {

	QUERY_SELECT_ALL_BORROWED_ITEMS("SELECT * FROM user_item"),
	QUERY_REMOVE_USER_ITEM("DELETE from user_item WHERE Item_ID=? and Email=?"),
	QUERY_IS_ITEM_BORROWED("SELECT * from user_item WHERE Email=? and Title=?"),
	QUERY_ADD_USER_ITEM_ON_HOLD("INSERT INTO holds (Email,Title,Category,Item_ID) VALUES (?,?, ?,?)"),
	QUERY_IS_ITEM_ON_HOLD("SELECT * from holds WHERE Item_ID=?"),
	QUERY_ADD_USER_ITEM("INSERT INTO user_item (Item_ID,Email,Category,Title) VALUES (?,?, ?, ?)"),
	QUERY_GET_NEXT_USER_HOLD("SELECT * FROM holds WHERE Item_ID=? ORDER BY EntryDateTime LIMIT 1"),
	QUERY_REMOVE_USER_FROM_HOLD("DELETE from holds WHERE Item_ID=? and Email=?");
	
	
	String query;
	
	private UserItemDAOEnums(String query) {
	
		this.query = query;
		
	}
	
	public String getQuery()
	{
		return query;
	}
}
