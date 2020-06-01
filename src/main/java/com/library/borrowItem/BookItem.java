package com.library.borrowItem;

import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.UserItem;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IUserItemDAO;

public class BookItem {
	
	private UserItem userItem;
	private IUserItemDAO userItemDAO;
	
	public BookItem(DisplayDetailed displayDetailed,String userEmail)
	{
		userItem = new UserItem();
		userItem.setTitle(displayDetailed.getTitle());
		userItem.setCategory(displayDetailed.getItemType());
		userItem.setEmail(userEmail);
		userItem.setItemId(displayDetailed.getItemID());
		IDAOFactory factory = new DAOFactory();
		userItemDAO = factory.makeUserItemDAO();
	}
	
	private Boolean borrowBook()
	{
		return userItemDAO.addItem(userItem);
	}
	
	private Boolean holdItem()
	{
		return userItemDAO.addItemOnHold(userItem);
	}
	
	public Boolean bookItem(String status)
	{
		Boolean isItemBooked = false;
		EmailSender emailSender = new EmailSender(userItem);
		if(status.equals(StatusEnum.AVAILABLE.getStatus()) || status.equals(StatusEnum.RESERVE.getStatus()))
		{
			if(status.equals(StatusEnum.AVAILABLE.getStatus()))
			{
				isItemBooked = borrowBook();
				emailSender.sendBookingEmail();
				DescreaseAvailability decreaser = new DescreaseAvailability(userItem.getCategory());
				decreaser.decreaseAvailability(userItem.getItemId());
			}
			else if(status.equals(StatusEnum.RESERVE.getStatus()))
			{
				isItemBooked = holdItem();
				emailSender.sendReserveEmail();
			}
		}
		else
		{
			isItemBooked = true;
		}
		
		if(isItemBooked)
		{
			ChangeItemCount countChanger = new ChangeItemCount(userItem.getCategory(), userItem.getItemId());
			countChanger.changeCount();	
		}
		
		return isItemBooked;
	}
}
