package com.library.additem;

import org.springframework.web.multipart.MultipartFile;

public interface IItemCoverSetter {

	public boolean isCoverAddedToDatabase(int itemId,MultipartFile coverImage);
	
}
