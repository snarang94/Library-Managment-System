package com.library.loanmanagement;

public enum CategoryEnum {

	BOOK("Book"), MOVIE("Movie"), MUSIC("Music");

	private String text;

	private CategoryEnum(String text) {

		this.text = text;
	}

	public String getText() {
		return text;
	}
}
