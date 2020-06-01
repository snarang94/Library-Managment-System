package com.library.validations;

import java.util.regex.Pattern;

public class PasswordSymbolValidation {
	private String passwordSymbols;

	public  boolean isValid(String valToCheck) {
		boolean value = false;
		if (Pattern.compile(passwordSymbols).matcher(valToCheck).find()) {
			value = true;
		}
		return value;
	}

	public void setPasswordSymbols(String passwordSymbols) {
		this.passwordSymbols = passwordSymbols;
	}
}
