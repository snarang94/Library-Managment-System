package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.businessModels.Music;

public interface IAddMusicController {

	public AddItemMessagesEnum addMusicRecordInDatabase(Music music, MultipartFile coverImage );

	public List<String> getMusicCategories();
	
}
