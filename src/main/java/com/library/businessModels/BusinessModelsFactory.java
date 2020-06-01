package com.library.businessModels;

public class BusinessModelsFactory {
	private static BusinessModelsFactory instance = null;

	private BusinessModelsFactory() {
	}

	public static BusinessModelsFactory instance() {
		if (null == instance) {
			instance = new BusinessModelsFactory();
		}
		return instance;
	}

	public Book makeBook() {
		return new Book();
	}

	public Cover makeCover() {
		return new Cover();
	}

	public Display makeDisplay() {
		return new Display();
	}

	public DisplayDetailed makeDisplayDetailed() {
		return new DisplayDetailed();
	}

	public Movie makeMovie() {
		return new Movie();
	}

	public Music makeMusic() {
		return new Music();
	}

	public Salt makeSalt() {
		return new Salt();
	}

	public User makeUser() {
		return new User();
	}

	public UserBasicInfo makeUserBasicInfo() {
		return new UserBasicInfo();
	}

	public UserExtendedInfo makeUserExtendedInfo() {
		return new UserExtendedInfo();
	}

	public UserItem makeUserItem() {
		return new UserItem();
	}
}
