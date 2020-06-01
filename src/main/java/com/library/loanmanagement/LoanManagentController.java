package com.library.loanmanagement;

import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IUserItemDAO;

public class LoanManagentController implements ILoanManagementController {

	private IDAOFactory iDAOfactory;
	private IUserItemDAO itemDAO;
	private List<UserItem> items;
	private LoanManagementContext context;

	public LoanManagentController() {

		iDAOfactory = new DAOFactory();
		itemDAO = iDAOfactory.makeUserItemDAO();
		items = new ArrayList<UserItem>();
	}

	@Override
	public List<UserItem> getAllBorrowedItems() {

		items = itemDAO.getAllBorrowedItems();
		return items;
	}

	@Override
	public void removeUserItems(List<UserItem> userItems) {

		for (UserItem item : userItems) {
			itemDAO.removeItem(item);
			returnProcess(item);
		}
	}

	private void returnProcess(UserItem item) {
		String category = item.getCategory();
		IReturnItemStrategy iReturnItemStrategy = null;
		if (category.equalsIgnoreCase(CategoryEnum.BOOK.getText())) {
			iReturnItemStrategy = new BookReturnStrategy();
		} else if (category.equalsIgnoreCase(CategoryEnum.MOVIE.getText())) {
			iReturnItemStrategy = new MovieReturnStrategy();
		} else if (category.equalsIgnoreCase(CategoryEnum.MUSIC.getText())) {
			iReturnItemStrategy = new MusicReturnStrategy();
		}
		context = new LoanManagementContext(iReturnItemStrategy);
		context.executeReturnItemStrategy(item);

	}

}
