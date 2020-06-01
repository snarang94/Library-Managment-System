package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.browsePage.BrowseBooks;
import com.library.businessModels.Book;
import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;
import com.library.routes.ILibraryFactory;
import com.library.routes.LibraryFactorySingleton;

public class AddBookController implements IAddBookController {
	
	private IDAOFactory iDAOfactory;
	private int itemIdOfBook;
	private IBookDAO bookDAO;
	private ILibraryFactory iLibraryfactory;
	private LibraryFactorySingleton factorySingleton;
	private IItemCoverSetter coverSetter;
	private boolean isBookCoverCreated, isDuplicateBook;

	public AddBookController() {
		iDAOfactory = new DAOFactory();
		bookDAO = iDAOfactory.makeBookDAO();
		factorySingleton = LibraryFactorySingleton.instance();
		iLibraryfactory = factorySingleton.getFactory();

	}

	public AddItemMessagesEnum addBookRecordInDatabase(Book book, MultipartFile bookCoverImage) {

		isDuplicateBook = bookDAO.checkBookDuplicacy(book);

		if (isDuplicateBook) {
			return AddItemMessagesEnum.ERROR_DUPLICATE_BOOK;
		}

		itemIdOfBook = bookDAO.createBook(book);

		if (itemIdOfBook == 0) {

			return AddItemMessagesEnum.ERROR_BOOK_CAN_NOT_BE_CREATED;

		} else {

			coverSetter = iLibraryfactory.makeItemCoverSetter();
			isBookCoverCreated = coverSetter.isCoverAddedToDatabase(itemIdOfBook, bookCoverImage);
			if (isBookCoverCreated) {
				return AddItemMessagesEnum.SUCCESS_BOOK;
			} else {
				bookDAO.deleteBookByID(itemIdOfBook);
				return AddItemMessagesEnum.ERROR_BOOK_CAN_NOT_BE_CREATED;
			}
		}

	}

	@Override
	public List<String> getBookCategories() {
		
		BrowseBooks browseBooks = new BrowseBooks();
		List<String> bookCategories = browseBooks.getCategories();
		return bookCategories;
	}

}
