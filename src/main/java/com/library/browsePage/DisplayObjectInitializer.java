package com.library.browsePage;

import com.library.loanmanagement.CategoryEnum;

public class DisplayObjectInitializer {
	
	
	
	public IBrowseDisplayObjects getDisplayObject(String itemType) {
		IBrowseDisplayObjects browseDisplayObjects = null;
		IBrowseDisplayFactory browseFactory = BrowseDisplayFactory.getInstance();
		if (itemType.equals(CategoryEnum.BOOK.getText())) {
			browseDisplayObjects = browseFactory.makeBookDisplay();
		} else if (itemType.equals(CategoryEnum.MOVIE.getText())) {
			browseDisplayObjects = browseFactory.makeMovieDisplay();
		} else if (itemType.equals(CategoryEnum.MUSIC.getText())) {
			browseDisplayObjects = browseFactory.makeMusicDisplay();
		}
		return browseDisplayObjects;
	}
}
