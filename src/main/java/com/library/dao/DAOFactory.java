package com.library.dao;

import com.library.dao.*;

public class DAOFactory implements IDAOFactory {

	@Override
	public IBookDAO makeBookDAO() {

		return new BookDAO();
	}

	@Override
	public ICoverDAO makeCoverDAO() {

		return new CoverDAO();
	}

	@Override
	public ILibraryItemDAO makeLibraryItemDAO() {

		return new LibraryItemDAO();
	}

	@Override
	public IMovieDAO makeMovieDAO() {

		return new MovieDAO();
	}

	@Override
	public IMusicDAO makeMusicDAO() {

		return new MusicDAO();
	}

	@Override
	public IUserDAO makeUserDAO() {

		return new UserDAO();
	}

	@Override
	public IUserItemDAO makeUserItemDAO() {

		return new UserItemDAO();
	}

}
