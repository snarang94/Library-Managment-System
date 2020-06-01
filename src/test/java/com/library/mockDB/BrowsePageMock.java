package com.library.mockDB;

import java.util.ArrayList;
import java.util.List;

import com.library.businessModels.Book;
import com.library.businessModels.Movie;
import com.library.businessModels.Music;

public class BrowsePageMock {
	
	int id = 1;
	
	public List<Book> getBookObjects()
	{
		List<Book> books = new ArrayList<Book>();
		char content = 'a';
		for(int i=0; i<=10;i++)
		{
			Book book = new Book();
			book.setTitle(String.valueOf(content));
			book.setItemID(id);
			id += 1;
			content += 1;
			books.add(book);
		}
		return books;
	}
	
	public List<Movie> getMovieObjects()
	{
		List<Movie> movies = new ArrayList<Movie>();
		char content = 'a';
		for(int i=0; i<=10;i++)
		{
			Movie movie = new Movie();
			movie.setTitle(String.valueOf(content));
			movie.setItemID(id);
			id += 1;
			content += 1;
			movies.add(movie);
		}
		return movies;
	}
	
	public List<Music> getMusicObjects()
	{
		List<Music> musics = new ArrayList<Music>();
		char content = 'a';
		for(int i=0; i<=10;i++)
		{
			Music music = new Music();
			music.setTitle(String.valueOf(content));
			music.setItemID(id);
			id += 1;
			content += 1;
			musics.add(music);
		}
		return musics;
	}
}
