package com.library.loanmanagement;

import com.library.businessModels.UserItem;

public class LoanManagementContext {

	private IReturnItemStrategy iReturnItemStrategy;
	private UserItem userOnHold;

	public LoanManagementContext(IReturnItemStrategy iReturnItemStrategy) {

		this.iReturnItemStrategy = iReturnItemStrategy;

	}

	public void executeReturnItemStrategy(UserItem item) {

		boolean isItemOnHold = false;
		int itemId = item.getItemId();
		isItemOnHold = iReturnItemStrategy.isItemOnHold(itemId);
		if (isItemOnHold) {
			userOnHold = new UserItem();
			userOnHold = iReturnItemStrategy.getTheNextUserInLine(itemId);
			iReturnItemStrategy.sendEmail(userOnHold);
			iReturnItemStrategy.removeUserFromHold(userOnHold);
			iReturnItemStrategy.addUserItem(userOnHold);
			
		} else {
			iReturnItemStrategy.increaseAvailabilty(item);
		}

	}

}
