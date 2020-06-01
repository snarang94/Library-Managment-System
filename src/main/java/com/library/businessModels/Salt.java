package com.library.businessModels;

public class Salt implements ISalt {
	private String passwordValue;

	@Override
	public void setSaltedPassword(String passwordValue, String salt) {
//		this.passwordValue=passwordValue;
//		this.salt = salt;
//		
		this.passwordValue = passwordValue.concat(salt);
	}

	@Override
	public String getSaltedPassword() {
		return passwordValue;
	}

}
