package com.library.browsePage;

import java.util.List;

import com.library.businessModelSetter.DisplaySetter;
import com.library.businessModelSetter.IDisplaySetter;
import com.library.businessModels.Display;
import com.library.businessModels.Music;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMusicDAO;

public class BrowseMusic implements IBrowseDisplayObjects{

	private IMusicDAO musicDAO;
	private String itemType;
	private static String music = "Music";
	
	public BrowseMusic()
	{
		IDAOFactory factory = new DAOFactory();
		musicDAO = factory.makeMusicDAO();	
		itemType = music;
	}
	
	@Override
	public List<Display> itemsByCategory(String category) {
		IDisplaySetter displaySetter = new DisplaySetter();
		List<Music> music = musicDAO.getMusicByCategory(category);
		List<Display> displayMusic = displaySetter.getMusicDisplayObjects(music);
		return displayMusic;
	}

	@Override
	public List<String> getCategories() {
		List<String> categories = musicDAO.getMusicCategories();
		return categories;
	}

	@Override
	public String getItemType() {
		return itemType;
	}

}
