package com.library.browsePage;

public class BrowseDisplayFactory implements IBrowseDisplayFactory{

	private static BrowseDisplayFactory browseDisplayFactory = null;
	
	public static BrowseDisplayFactory getInstance()
	{
		if(browseDisplayFactory == null)
		{
			browseDisplayFactory = new BrowseDisplayFactory();
		}
		return browseDisplayFactory;
	}
	
	@Override
	public IBrowseDisplayObjects makeBookDisplay() {
		return new BrowseBooks();
	}

	@Override
	public IBrowseDisplayObjects makeMovieDisplay() {
		return new BrowseMovies();
	}

	@Override
	public IBrowseDisplayObjects makeMusicDisplay() {
		return new BrowseMusic();
	}

}
