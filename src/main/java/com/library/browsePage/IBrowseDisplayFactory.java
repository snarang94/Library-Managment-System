package com.library.browsePage;

public interface IBrowseDisplayFactory {
	public IBrowseDisplayObjects makeBookDisplay();
	public IBrowseDisplayObjects makeMovieDisplay();
	public IBrowseDisplayObjects makeMusicDisplay();
}
