package com.library.businessModels;

public class UserExtendedInfo implements IUserExtendedInfo {
	private String fullName;
	private String phoneNumber;
	private String cpassword;
	@Override
	public String getFullname() {
		return fullName;
	}

	@Override
	public String getPhone() {
		return phoneNumber;
	}

	@Override
	public void setFullname(String fullname) {
		fullName = fullname;
	}

	@Override
	public void setPhone(String phone) {
		phoneNumber = phone;
	}

	@Override
	public String getCPassword() {
		return cpassword;
	}

	@Override
	public void setCPassword(String cpass) {
		cpassword = cpass;
	}

}
