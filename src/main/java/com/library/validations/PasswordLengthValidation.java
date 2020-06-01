package com.library.validations;

public class PasswordLengthValidation {

	private  String passwordLength;

	public boolean isValidLength(String valToCheck) {
		boolean value = false;
		
		if (Integer.parseInt(passwordLength)<=(valToCheck.length())) {
			value = true;
		}
		return value;
	}
	public void setPasswordLength(String passwordLength) {
		this.passwordLength = passwordLength;
	}
}
