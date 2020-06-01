package com.library.routes;

public class LibraryFactorySingleton {
	private static LibraryFactorySingleton singletonLibraryFactory = null;
	private ILibraryFactory factory;

	public static LibraryFactorySingleton instance() {
		if (singletonLibraryFactory == null) {
			singletonLibraryFactory = new LibraryFactorySingleton();
		}
		return singletonLibraryFactory;
	}

	public ILibraryFactory getFactory() {
		return new LibraryControllerFactory();
	}

}
