package com.library.setterTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.library.businessModelSetter.DisplaySetter;
import com.library.businessModels.Book;
import com.library.businessModels.Display;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;
import com.library.mockDB.BrowsePageMock;

public class DisplaySetterTest {

	@Test
	public void bookDisplayTest() {
		List<Book> books = new ArrayList<Book>();
		List<Display> bookDisplay = new ArrayList<Display>();
		BrowsePageMock browsePageMock = new BrowsePageMock();
		books = browsePageMock.getBookObjects();
		DisplaySetter displaySetter = new DisplaySetter();
		bookDisplay = displaySetter.getBookDisplayObjects(books);
		Display testBookDisplay = bookDisplay.get(0);
		assertEquals("a", testBookDisplay.getTitle());
	}

	@Test
	public void movieDisplayTest() {
		List<Movie> movies = new ArrayList<Movie>();
		List<Display> movieDisplay = new ArrayList<Display>();
		BrowsePageMock browsePageMock = new BrowsePageMock();
		movies = browsePageMock.getMovieObjects();
		DisplaySetter displaySetter = new DisplaySetter();
		movieDisplay = displaySetter.getMovieDisplayObjects(movies);
		Display testMovieDisplay = movieDisplay.get(0);
		assertEquals("a", testMovieDisplay.getTitle());
	}

	@Test
	public void MusicDisplayTest() {
		List<Music> music = new ArrayList<Music>();
		List<Display> musicDisplay = new ArrayList<Display>();
		BrowsePageMock browsePageMock = new BrowsePageMock();
		music = browsePageMock.getMusicObjects();
		DisplaySetter displaySetter = new DisplaySetter();
		musicDisplay = displaySetter.getMusicDisplayObjects(music);
		Display testMusicDisplay = musicDisplay.get(0);
		assertEquals("a", testMusicDisplay.getTitle());
	}
}
