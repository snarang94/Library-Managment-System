package com.library.browsePage;

import java.util.List;

import com.library.businessModelSetter.DisplaySetter;
import com.library.businessModelSetter.IDisplaySetter;
import com.library.businessModels.Book;
import com.library.businessModels.Display;
import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;

public class BrowseBooks implements IBrowseDisplayObjects{

	private IBookDAO bookDAO;
	private String itemType;
	private static String book = "Book";
	
	public BrowseBooks()
	{
		IDAOFactory factory = new DAOFactory();
		bookDAO = factory.makeBookDAO();
		itemType = book;
	}
	
	@Override
	public List<Display> itemsByCategory(String category) {
		IDisplaySetter displaySetter = new DisplaySetter();
		List<Book> books = bookDAO.getBookByCategory(category);
		List<Display> displayBooks = displaySetter.getBookDisplayObjects(books);
		return displayBooks;
	}

	@Override
	public List<String> getCategories() {
		List<String> categories = bookDAO.getBookCategories();
		return categories;
	}

	@Override
	public String getItemType() {
		return itemType;
	}

}
