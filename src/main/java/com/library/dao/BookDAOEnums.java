package com.library.dao;

public enum BookDAOEnums {

	QUERY_GET_BOOK_BY_ID("SELECT * FROM books WHERE Item_ID = ?"),
	QUERY_GET_BOOKS_BY_CATEGORY("SELECT * FROM books WHERE Category=?"),
	QUERY_GET_BOOK_CATEGORIES("SELECT Distinct Category from books"),
	QUERY_DELETE_BOOK_BY_ID("Delete FROM books WHERE Item_ID = ?"),
	QUERY_INSERT_BOOK("Insert into books (Category,Title,Author,ISBN,Publisher,Description,Availability) Values (?,?,?,?,?,?,?)"),
	QUERY_IS_DUPLICATE_BOOK("SELECT * FROM books where Title=? and Author=?"),
	QUERY_INCREASE_BOOK_COUNT("update books set count = count + 1 where Item_ID = ?"),
	QUERY_UPDATE_AVAILABILITY("update books set Availability = ? where Item_ID = ?"),
	QUERY_GET_CURRENT_AVAILABILITY_OF_BOOK("Select Availability from books where Item_ID = ?");
	
	String query;

	private BookDAOEnums(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

}
