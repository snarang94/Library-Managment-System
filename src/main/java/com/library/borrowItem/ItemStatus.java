package com.library.borrowItem;

import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;
import com.library.dao.IMusicDAO;
import com.library.dao.IUserItemDAO;
import com.library.loanmanagement.CategoryEnum;

public class ItemStatus {

	private IUserItemDAO userItemDAO;

	private UserItem userItem;
	private int itemID;

	public ItemStatus(DisplayDetailed displayDetailed, String userEmail) {
		userItem = new UserItem();
		userItem.setTitle(displayDetailed.getTitle());
		userItem.setCategory(displayDetailed.getItemType());
		userItem.setEmail(userEmail);
		itemID = displayDetailed.getItemID();
		IDAOFactory factory = new DAOFactory();
		userItemDAO = factory.makeUserItemDAO();
	}

	private Boolean isItemAvailable() {
		IDAOFactory factory = new DAOFactory();
		Boolean availability = false;
		if (userItem.getCategory().equals(CategoryEnum.BOOK.getText())) {
			IBookDAO bookDAO = factory.makeBookDAO();
			int booksAvailable = bookDAO.getAvailability(itemID);
			if (booksAvailable > 0) {
				availability = true;
			}
		} else if (userItem.getCategory().equals(CategoryEnum.MOVIE.getText())) {
			IMovieDAO movieDAO = factory.makeMovieDAO();
			int moviesAvailable = movieDAO.getAvailability(itemID);
			if (moviesAvailable > 0) {
				availability = true;
			}
		} else if (userItem.getCategory().equals(CategoryEnum.MUSIC.getText())) {
			IMusicDAO musicDAO = factory.makeMusicDAO();
			int musicAvailable = musicDAO.getAvailability(itemID);
			if (musicAvailable > 0) {
				availability = true;
			}
		}
		return availability;
	}

	private Boolean isItemAlreadyBooked() {
		return userItemDAO.isItemBorrowed(userItem);
	}

	private Boolean isItemAlreadyOnHold() {
		return userItemDAO.isItemOnHold(itemID);
	}

	public String getItemStatus() {
		if (isItemAlreadyBooked()) {
			return StatusEnum.BORROWED.getStatus();
		} else if (isItemAlreadyOnHold()) {
			return StatusEnum.ONHOLD.getStatus();
		} else if (isItemAvailable()) {
			return StatusEnum.AVAILABLE.getStatus();
		} else {
			return StatusEnum.RESERVE.getStatus();
		}
	}
}
