package com.library.loanmanagement;

import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;
import com.library.dao.IUserItemDAO;

public class MovieReturnStrategy implements IReturnItemStrategy {

	private IDAOFactory iDAOfactory;
	private IMovieDAO iMovieDAO;
	private IUserItemDAO iUserItemDAO;
	private UserItem userOnHold;
	private SendBookingEmail bookingEmail;

	public MovieReturnStrategy() {

		iDAOfactory = new DAOFactory();
		iMovieDAO = iDAOfactory.makeMovieDAO();
		bookingEmail = new SendBookingEmail();
		iUserItemDAO = iDAOfactory.makeUserItemDAO();

	}

	@Override
	public void increaseAvailabilty(UserItem item) {

		int itemId = item.getItemId();
		int currentAvailability = iMovieDAO.getAvailability(itemId);
		int udatedAvailability = currentAvailability + 1;
		iMovieDAO.updateAvailability(itemId, udatedAvailability);

	}

	@Override
	public void sendEmail(UserItem item) {

		bookingEmail.sendBookingItemEmail(item);

	}

	@Override
	public boolean isItemOnHold(int itemId) {
		boolean isItemOnHold = false;

		isItemOnHold = iUserItemDAO.isItemOnHold(itemId);
		if (isItemOnHold) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserItem getTheNextUserInLine(int itemId) {
		userOnHold = new UserItem();
		userOnHold = iUserItemDAO.getTheNextUserInLine(itemId);
		return userOnHold;
	}

	@Override
	public void removeUserFromHold(UserItem userOnHold) {

		iUserItemDAO.removeUserFromHold(userOnHold);

	}

	@Override
	public void addUserItem(UserItem userItem) {

		iUserItemDAO.addItem(userItem);

	}

}
