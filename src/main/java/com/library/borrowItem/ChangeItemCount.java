package com.library.borrowItem;

import com.library.dao.DAOFactory;
import com.library.dao.IBookDAO;
import com.library.dao.IDAOFactory;
import com.library.dao.IMovieDAO;
import com.library.dao.IMusicDAO;
import com.library.loanmanagement.CategoryEnum;

public class ChangeItemCount {

	private int itemID;
	private String itemType;
	IDAOFactory factory;
	
	public ChangeItemCount(String itemType, int itemID) 
	{
		this.itemID = itemID;
		this.itemType = itemType;
		factory = new DAOFactory();
	}
	
	private Boolean changeBookCount()
	{
		Boolean countChanged;
		IBookDAO bookDAO = factory.makeBookDAO();
		countChanged = bookDAO.increaseCount(itemID);
		return countChanged;
	}
	
	private Boolean changeMovieCount()
	{
		Boolean countChanged;
		IMovieDAO movieDAO = factory.makeMovieDAO();
		countChanged = movieDAO.increaseCount(itemID);
		return countChanged;
	}
	
	private Boolean changeMusicCount()
	{
		Boolean countChanged;
		IMusicDAO musicDAO = factory.makeMusicDAO();
		countChanged = musicDAO.increaseCount(itemID);
		return countChanged;
	}
	
	public Boolean changeCount()
	{
		Boolean isCountChanged = false;
		if(itemType.equals(CategoryEnum.BOOK.getText()))
		{
			isCountChanged = changeBookCount();
		}
		else if(itemType.equals(CategoryEnum.MOVIE.getText()))
		{
			isCountChanged = changeMovieCount();
		}
		else if(itemType.equals(CategoryEnum.MUSIC.getText()))
		{
			isCountChanged = changeMusicCount();
		}
		return isCountChanged;
	}
}
