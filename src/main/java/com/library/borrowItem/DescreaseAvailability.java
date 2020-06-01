package com.library.borrowItem;

import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;
import com.library.dao.IMusicDAO;
import com.library.loanmanagement.CategoryEnum;

public class DescreaseAvailability {

	static final int quantity = -1;
	String category;
	IDAOFactory factory;
	int currentAvailability;

	public DescreaseAvailability(String category) {
		this.category = category;
		factory = new DAOFactory();
	}
	
	public void decreaseAvailability(int itemID)
	{
		if(category.equals(CategoryEnum.BOOK.getText()))
		{
			IBookDAO bookDAO = factory.makeBookDAO();
			currentAvailability = bookDAO.getAvailability(itemID);
			bookDAO.updateAvailability(itemID, currentAvailability-1);
		}
		else if (category.equals(CategoryEnum.MOVIE.getText()))
		{
			IMovieDAO movieDAO = factory.makeMovieDAO();
			currentAvailability = movieDAO.getAvailability(itemID);
			movieDAO.updateAvailability(itemID, currentAvailability-1);
		}
		else if (category.equals(CategoryEnum.MUSIC.getText()))
		{
			IMusicDAO musicDAO = factory.makeMusicDAO();
			currentAvailability = musicDAO.getAvailability(itemID);
			musicDAO.updateAvailability(itemID, currentAvailability-1);
		}
	}
}
