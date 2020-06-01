package com.library.businessModelSetter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.Book;

public class BookSetter implements IBookSetter {

	public List<Book> mapBook(ResultSet resultSet) 
	{
		List<Book> books = new ArrayList<Book>();
		
		try {
			while(resultSet.next())
			{
				Book book = new Book();
				book.setIsbn(resultSet.getInt("ISBN"));
				book.setItemID(resultSet.getInt("Item_ID"));
				book.setTitle(resultSet.getString("Title"));
				book.setAuthor(resultSet.getString("Author"));
				book.setCategory(resultSet.getString("Category"));
				book.setDescription(resultSet.getString("Description"));
				book.setPublisher(resultSet.getString("Publisher"));
				book.setAvailability(resultSet.getInt("Availability"));
				books.add(book);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
}
