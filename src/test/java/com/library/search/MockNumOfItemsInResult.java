package com.library.search;

public class MockNumOfItemsInResult {
	private int numOfBooks;
	private int numOfMovies;
	private int numOfMusic;

	public MockNumOfItemsInResult(int numOfBooks, int numOfMovies, int numOfMusic) {
		this.numOfBooks = numOfBooks;
		this.numOfMovies = numOfMovies;
		this.numOfMusic = numOfMusic;
	}

	public int getNumOfBooks() {
		return numOfBooks;
	}

	public int getNumOfMovies() {
		return numOfMovies;
	}

	public int getNumOfMusic() {
		return numOfMusic;
	}
}
