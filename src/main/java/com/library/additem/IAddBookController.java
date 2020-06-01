package com.library.additem;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.library.businessModels.Book;

public interface IAddBookController {
	
	public AddItemMessagesEnum addBookRecordInDatabase(Book book, MultipartFile coverImage);

	public List<String> getBookCategories();
}
