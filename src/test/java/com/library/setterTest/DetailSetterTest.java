package com.library.setterTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.library.businessModelSetter.DetailsSetter;
import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public class DetailSetterTest {

	@Test
	public void getMusicDetailsTest()
	{
		Music music = new Music();
		music.setArtist("a");
		music.setCategory("b");
		music.setDescription("c");	
		music.setRecordLabel("d");
		music.setItemID(1);
		music.setTitle("e");
		DetailsSetter detailSetter = new DetailsSetter();
		String details = detailSetter.getMusicDetails(music);
		assertEquals("Artist: a<br />Record Label: d<br />", details);
	}
	
	@Test
	public void getMovieDetailsTest()
	{
		Movie movie = new Movie();
		movie.setDescription("a");
		movie.setDirector("b");
		DetailsSetter detailsSetter = new DetailsSetter();
		String details = detailsSetter.getMovieDetails(movie);
		assertEquals("Director: b<br />Description: a<br />", details);
	}
	
	@Test
	public void getBookDetailsTest()
	{
		Book book = new Book();
		book.setDescription("a");
		book.setAuthor("b");
		book.setPublisher("c");
		DetailsSetter detailsSetter = new DetailsSetter();
		String details = detailsSetter.getBookDetails(book);
		assertEquals("Author: b<br />Publisher: c<br />Description: a", details);
	}
}
