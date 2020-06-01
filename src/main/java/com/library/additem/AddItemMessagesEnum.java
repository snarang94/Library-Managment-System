package com.library.additem;

public enum AddItemMessagesEnum {
	
	SUCCESS_BOOK("Book Successfully added!"),
	ERROR_DUPLICATE_BOOK("Book exists in Library! Please add new Book!"),
	ERROR_BOOK_CAN_NOT_BE_CREATED("Book can not be created! Please try again!"),
	SUCCESS_MOVIE("Movie Successfully added!"),
	ERROR_DUPLICATE_MOVIE("Movie exists in Library! Please add new Movie!"),
	ERROR_MOVIE_CAN_NOT_BE_CREATED("Movie can not be created! Please try again!"),
	SUCCESS_MUSIC("Music Successfully added!"),
	ERROR_DUPLICATE_MUSIC("Music exists in Library! Please add new Music!"),
	ERROR_MUSIC_CAN_NOT_BE_CREATED("Music can not be created! Please try again!");
	
	String message;
	
	private AddItemMessagesEnum(String message) {
	
		this.message = message;
		
	}
	
	public String getMessage() {
		return message;
	}

}
