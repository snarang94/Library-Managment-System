package com.library.dao;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.library.businessModelSetter.BookSetter;
import com.library.businessModelSetter.IBookSetter;
import com.library.businessModels.Book;
import com.library.businessModels.LibraryItem;
import com.library.dbConnection.DatabaseConnection;
import com.library.search.BookSearch;

public class BookDAO implements IBookDAO {

	private PreparedStatement preparedStatement;
	private ResultSet resultSet = null;
	private String query;
	private Connection connection;
	private IBookSetter bookMapper = new BookSetter();
	private static final Logger logger = LogManager.getLogger(BookDAO.class);
	DatabaseConnection databaseConnection;

	public BookDAO() {

		try {
			databaseConnection = DatabaseConnection.getDatabaseConnectionInstance();
			this.connection = databaseConnection.getConnection();
		} catch (Exception e) {
			logger.log(Level.ALL, "Unable to connect to database", e);
		}
	}

	@Override
	public Book getBookByID(int itemID) {
		try {
			Book book = new Book();
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_GET_BOOK_BY_ID.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			List<Book> books = bookMapper.mapBook(resultSet);
			book = books.get(0);
			return book;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Book not found for the Itemid ["+itemID+"] in  books table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return null;
	}

	@Override
	public List<Book> getBookByCategory(String category) {
		try {
			List<Book> books = new ArrayList<Book>();
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_GET_BOOKS_BY_CATEGORY.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, category);
			resultSet = preparedStatement.executeQuery();
			books = bookMapper.mapBook(resultSet);
			return books;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of Book for category ["+category+"] in books table!", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return null;
	}

	@Override
	public List<String> getBookCategories() {
		List<String> categories = new ArrayList<String>();
		this.connection = databaseConnection.getConnection();
		query = BookDAOEnums.QUERY_GET_BOOK_CATEGORIES.getQuery();
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				categories.add(resultSet.getString("Category"));
			}
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of : "+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the distinct list of Book Categories from books table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return categories;
	}

	private String prepareSearchQuery(BookSearch requestDetails, String searchTerms) {

		if (0 == searchTerms.length()) {
			logger.log(Level.ERROR, "No search terms are supplied");
			return null;
		}

		String query = "SELECT DISTINCT * FROM books WHERE ";
		String[] searchterms = searchTerms.split("\\s");
		for (String term : searchterms) {
			if (requestDetails.isSearchBookAuthor()) {
				query += "Author like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchBookDescription()) {
				query += "Description like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchBookISBN()) {
				query += "ISBN like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchBookPublisher()) {
				query += "Publisher like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchBookTitle()) {
				query += "Title like \"%" + term + "%\" or ";
			}
			if (requestDetails.isSearchBookCategory()) {
				query += "Category like \"%" + term + "%\" or ";
			}
		}

		query = query.substring(0, query.length() - 4);
		return query;
	}

	@Override
	public List<LibraryItem> getBooksBySearchTerms(BookSearch requestDetails, String searchTerms) {
		List<LibraryItem> books = new LinkedList<LibraryItem>();
		List<Book> tempBooks = new ArrayList<>();
		this.connection = databaseConnection.getConnection();
		if (!requestDetails.isSearchInBooks()) {
			return books;
		}
		String query = prepareSearchQuery(requestDetails, searchTerms);

		if (null == query) {
			return books;
		}

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			tempBooks = bookMapper.mapBook(resultSet);
			books.addAll(tempBooks);
			return books;
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Failed to prepare SQL statement OR execute a query OR parse a query resultSet", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return books;
	}

	@Override
	public Boolean deleteBookByID(int itemID) {
		try {
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_DELETE_BOOK_BY_ID.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Failed to delete book with itemId ["+itemID+"] from books table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return false;
	}

	public Blob getCoverBlob(MultipartFile coverImage) {
		Blob coverBlob = null;
		try {
			byte[] bytes = coverImage.getBytes();
			coverBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
		} catch (IOException e) {

			logger.log(Level.ALL, "IO Exception while converting Multipart into Blob", e);

		} catch (SerialException e) {

			logger.log(Level.ALL, "Serial Exception while converting Multipart into Blob", e);

		} catch (SQLException e) {

			logger.log(Level.ALL, "Check the SQL syntax :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Can not fetch cover image", e);
		} finally {

			databaseConnection.closeConnection((com.mysql.jdbc.PreparedStatement) preparedStatement);

		}
		return coverBlob;
	}

	@Override
	public int createBook(Book book) {
		String category = book.getCategory();
		String title = book.getTitle();
		String author = book.getAuthor();
		int isbn = book.getIsbn();
		String publisher = book.getPublisher();
		String description = book.getDescription();
		int defaultAvailablity = 5;
		int recentlyAddedBookId = 0;
		
		try {
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_INSERT_BOOK.getQuery();
			preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, category);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, author);
			preparedStatement.setInt(4, isbn);
			preparedStatement.setString(5, publisher);
			preparedStatement.setString(6, description);
			preparedStatement.setInt(7, defaultAvailablity);
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();

			if (resultSet.next()) {
				recentlyAddedBookId = resultSet.getInt(1);
			}

			return recentlyAddedBookId;

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Can not insert Book with title ["+title+"] , author["+author+"], isbn ["+isbn+"] into database", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return recentlyAddedBookId;
	}
	
	@GetMapping("/error")
	public String customErrorPage()
	{
		return "Error";
	}
	
	@Override
	public int getAvailability(int itemID) {

		int booksAvailable = 0;
		try {
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_GET_CURRENT_AVAILABILITY_OF_BOOK.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				booksAvailable = resultSet.getInt("Availability");
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the availability of Book with itemId ["+itemID+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return booksAvailable;
	}

	public boolean checkBookDuplicacy(Book book) {
		String authorToBeAdded = book.getAuthor();
		String titleToBeAdded = book.getTitle();
		boolean isBookAvailable = false;

		query = BookDAOEnums.QUERY_IS_DUPLICATE_BOOK.getQuery();
		try {
			this.connection = databaseConnection.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, titleToBeAdded);
			preparedStatement.setString(2, authorToBeAdded);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				isBookAvailable = true;
			} else {
				isBookAvailable = false;
			}

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax : "+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error fetching the list of Books with title ["+titleToBeAdded+"] and author ["+authorToBeAdded+"]", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
		return isBookAvailable;
	}

	public Boolean increaseCount(int itemID) {
		Boolean countIncrease = false;
		try {
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_INCREASE_BOOK_COUNT.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemID);
			preparedStatement.execute();
			countIncrease = true;
		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of :"+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error increasing count of book with itemID ["+itemID+"] in books table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);

		}
		return countIncrease;
	}

	@Override
	public void updateAvailability(int itemId, int updatedAvailability) {

		try {
			this.connection = databaseConnection.getConnection();
			query = BookDAOEnums.QUERY_UPDATE_AVAILABILITY.getQuery();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, updatedAvailability);
			preparedStatement.setInt(2, itemId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.log(Level.ALL, "Check the SQL syntax of : "+query, e);
		} catch (Exception e) {
			logger.log(Level.ALL, "Error updating the availability of book with itemId["+itemId+"] in books table", e);
		} finally {
			databaseConnection.closeConnection(resultSet, preparedStatement);
		}
	}

}
