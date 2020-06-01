package com.library.loanmanagement;

import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMusicDAO;
import com.library.dao.IUserItemDAO;

public class MusicReturnStrategy implements IReturnItemStrategy {

	private IDAOFactory iDAOfactory;
	private IMusicDAO iMusicDAO;
	private IUserItemDAO iUserItemDAO;
	private UserItem userOnHold;
	private SendBookingEmail bookingEmail;

	public MusicReturnStrategy() {

		iDAOfactory = new DAOFactory();
		iMusicDAO = iDAOfactory.makeMusicDAO();
		bookingEmail = new SendBookingEmail();
		iUserItemDAO = iDAOfactory.makeUserItemDAO();

	}

	@Override
	public void increaseAvailabilty(UserItem item) {

		int itemId = item.getItemId();
		int currentAvailability = iMusicDAO.getAvailability(itemId);
		int udatedAvailability = currentAvailability + 1;
		iMusicDAO.updateAvailability(itemId, udatedAvailability);

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
