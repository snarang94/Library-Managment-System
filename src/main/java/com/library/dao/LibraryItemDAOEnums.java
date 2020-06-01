package com.library.dao;

public enum LibraryItemDAOEnums {
	
	GetLatestBookQuery("SELECT distinct * FROM books order by books.Item_ID desc limit "),
	GetLatestMovieQuery("SELECT distinct * FROM movie order by movie.Item_ID desc limit "),
	GetLatestMusicQuery("SELECT distinct * FROM music order by music.Item_ID desc limit "),
	GetFavBookQuery("SELECT distinct * FROM books order by Count desc limit "),
	GetFavMovieQuery("SELECT distinct * FROM movie order by Count desc limit "),
	GetFavMusicQuery("SELECT distinct * FROM music order by Count desc limit ");
	
	String query;

	private LibraryItemDAOEnums(String query) {
		
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
}
