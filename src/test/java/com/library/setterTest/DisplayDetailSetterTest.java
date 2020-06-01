package com.library.setterTest;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.library.businessModelSetter.DetailedDisplaySetter;
import com.library.businessModels.Book;
import com.library.businessModels.DisplayDetailed;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public class DisplayDetailSetterTest {

	DetailedDisplaySetter detailedDisplaySetter = new DetailedDisplaySetter();
	
	@Test
	public void makeDetailedMusicTest()
	{
		Music music = new Music();
		music.setArtist("a");
		music.setCategory("b");
		music.setDescription("c");	
		music.setRecordLabel("d");
		music.setItemID(1);
		music.setTitle("e");
		DisplayDetailed displayDetailed = detailedDisplaySetter.makeDetailedMusic(music);
		assertEquals(1, displayDetailed.getItemID());
		assertEquals("e", displayDetailed.getTitle());
		assertEquals("Artist: a<br />Record Label: d<br />", displayDetailed.getDetails());
	}
	
	@Test
	public void makeDetailedBookTest()
	{
		Book book = new Book();
		book.setDescription("a");
		book.setAuthor("b");
		book.setPublisher("c");
		book.setTitle("d");
		book.setItemID(1);
		DisplayDetailed displayDetailed = detailedDisplaySetter.makeDetailedBook(book);
		assertEquals("Author: b<br />Publisher: c<br />Description: a", displayDetailed.getDetails());
		assertEquals(1, displayDetailed.getItemID());
		assertEquals("d", displayDetailed.getTitle());
	}
	
	
	@Test
	public void makeDetailedMovieTest()
	{
		Movie movie = new Movie();
		movie.setDescription("a");
		movie.setDirector("b");
		movie.setTitle("c");
		movie.setItemID(1);
		DisplayDetailed displayDetailed = detailedDisplaySetter.makeDetailedMovie(movie);
		assertEquals("Director: b<br />Description: a<br />", displayDetailed.getDetails());
		assertEquals(1, displayDetailed.getItemID());
		assertEquals("c", displayDetailed.getTitle());
	}
}
