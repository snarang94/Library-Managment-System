package com.library.loanmanagement;

import java.util.List;

import com.library.businessModels.UserItem;

public interface ILoanManagementController {

	public List<UserItem> getAllBorrowedItems();
	public void removeUserItems(List<UserItem> userItems);
	
}
