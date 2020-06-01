package com.library.loanmanagement;

import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;
import com.library.dao.IUserItemDAO;

public class BookReturnStrategy implements IReturnItemStrategy {

	private IDAOFactory iDAOfactory;
	private IBookDAO iBookDAO;
	private IUserItemDAO iUserItemDAO;
	private SendBookingEmail bookingEmail;

	public BookReturnStrategy() {
		iDAOfactory = new DAOFactory();
		iBookDAO = iDAOfactory.makeBookDAO();
		iUserItemDAO = iDAOfactory.makeUserItemDAO();
		bookingEmail = new SendBookingEmail();
	}

	@Override
	public void increaseAvailabilty(UserItem item) {

		int itemId = item.getItemId();
		int currentAvailability = iBookDAO.getAvailability(itemId);
		int udatedAvailability = currentAvailability + 1;
		iBookDAO.updateAvailability(itemId, udatedAvailability);

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
		UserItem userOnHold;
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
