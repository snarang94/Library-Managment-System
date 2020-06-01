package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.browsePage.BrowseMusic;
import com.library.businessModels.Music;
import com.library.dao.DAOFactory;
import com.library.dao.IDAOFactory;
import com.library.dao.IMusicDAO;
import com.library.routes.ILibraryFactory;
import com.library.routes.LibraryFactorySingleton;

public class AddMusicController implements IAddMusicController {

	private IDAOFactory factory;
	private int itemIdOfMusic;
	private IMusicDAO iMusicDAO;
	private ILibraryFactory iLibraryfactory;
	private LibraryFactorySingleton factorySingleton;
	private IItemCoverSetter coverSetter;
	private boolean isMusicCoverCreated, isMusicDuplicate;;

	public AddMusicController() {
		factory = new DAOFactory();
		iMusicDAO = factory.makeMusicDAO();
		factorySingleton = LibraryFactorySingleton.instance();
		iLibraryfactory = factorySingleton.getFactory();
	}

	public AddItemMessagesEnum addMusicRecordInDatabase(Music music, MultipartFile musicCoverImage) {

		isMusicDuplicate = iMusicDAO.checkMusicDuplicacy(music);

		if (isMusicDuplicate) {
			return AddItemMessagesEnum.ERROR_DUPLICATE_MUSIC;
		}

		itemIdOfMusic = iMusicDAO.createMusic(music);
		if (itemIdOfMusic == 0) {

			return AddItemMessagesEnum.ERROR_MUSIC_CAN_NOT_BE_CREATED;

		} else {
			coverSetter = iLibraryfactory.makeItemCoverSetter();
			isMusicCoverCreated = coverSetter.isCoverAddedToDatabase(itemIdOfMusic, musicCoverImage);
			if (isMusicCoverCreated) {
				return AddItemMessagesEnum.SUCCESS_MUSIC;
			} else {
				iMusicDAO.deleteMusic(itemIdOfMusic);
				return AddItemMessagesEnum.ERROR_MUSIC_CAN_NOT_BE_CREATED;
			}
		}

	}

	@Override
	public List<String> getMusicCategories() {
		
		BrowseMusic browseMusic = new BrowseMusic();
		List<String> musicCategories = browseMusic.getCategories();
		return musicCategories;
	}
}
