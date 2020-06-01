package com.library.displayTest;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.library.browsePage.BrowseDisplayFactory;
import com.library.browsePage.IBrowseDisplayObjects;

public class BrowseDisplayFactoryTest {

	private BrowseDisplayFactory factory;
	
	public BrowseDisplayFactoryTest() {
		factory = BrowseDisplayFactory.getInstance();
	}
	
	@Test
	public void makeBookDisplayTest()
	{
		IBrowseDisplayObjects bookDisplay = factory.makeBookDisplay();
		assertEquals("Book", bookDisplay.getItemType());
	}
	
	@Test
	public void makeMusicDisplayTest()
	{
		IBrowseDisplayObjects musicDisplay = factory.makeMusicDisplay();
		assertEquals("Music", musicDisplay.getItemType());
	}

	@Test
	public void makeMovieDisplayTest()
	{
		IBrowseDisplayObjects movieDisplay = factory.makeMovieDisplay();
		assertEquals("Movie", movieDisplay.getItemType());
	}
}
