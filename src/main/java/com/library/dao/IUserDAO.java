package com.library.dao;

import com.library.businessModels.User;

public interface IUserDAO {
	public Boolean checkPassword(String emailAddress,String password);
	public Boolean changePassword(String emailAddress,String password);
	public Boolean registerUser(User user);
	public String getEmailRelatedPassword(String emailAddress);
	public Boolean checkEmailIdExist(String emailID);
}
