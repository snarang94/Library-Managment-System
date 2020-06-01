package com.library.dao;

import java.util.List;

import com.library.businessModels.LibraryItem;
import com.library.businessModels.Music;
import com.library.search.MusicSearch;

public interface IMusicDAO {
	public Music getMusicById(int itemID);
	public List<Music> getMusicByCategory(String category);
	public int createMusic(Music music);
	public Boolean deleteMusic(int itemID); 
	public List<LibraryItem> getMusicBySearchTerms(MusicSearch requestDetails, String searchTerms);
	List<String> getMusicCategories();
	public int getAvailability(int itemID); 
	public boolean checkMusicDuplicacy(Music music);
	public Boolean increaseCount(int itemID);
	void updateAvailability(int itemId, int udatedAvailability);
	
}
