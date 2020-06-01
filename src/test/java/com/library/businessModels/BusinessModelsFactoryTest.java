package com.library.businessModels;

import static org.junit.Assert.*;

import org.junit.Test;

public class BusinessModelsFactoryTest {
	private BusinessModelsFactory bmf = BusinessModelsFactory.instance();

	@Test
	public void test() {
		assertTrue(bmf.makeBook() instanceof Book);
		assertTrue(bmf.makeCover() instanceof Cover);
		assertTrue(bmf.makeDisplay() instanceof Display);
		assertTrue(bmf.makeDisplayDetailed() instanceof DisplayDetailed);
		assertTrue(bmf.makeMovie() instanceof Movie);
		assertTrue(bmf.makeMusic() instanceof Music);
		assertTrue(bmf.makeSalt() instanceof Salt);
		assertTrue(bmf.makeUser() instanceof User);
		assertTrue(bmf.makeUserBasicInfo() instanceof UserBasicInfo);
		assertTrue(bmf.makeUserExtendedInfo() instanceof UserExtendedInfo);
		assertTrue(bmf.makeUserItem() instanceof UserItem);
	}

}
