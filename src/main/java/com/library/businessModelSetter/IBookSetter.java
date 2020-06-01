package com.library.businessModelSetter;

import java.sql.ResultSet;
import java.util.List;

import com.library.businessModels.Book;

public interface IBookSetter {
	public List<Book> mapBook(ResultSet resultSet);
}
