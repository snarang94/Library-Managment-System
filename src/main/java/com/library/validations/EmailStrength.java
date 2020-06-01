package com.library.validations;

import java.util.regex.Pattern;

public class EmailStrength {
	private String emailStrength;

	public boolean isValidEmail(String valToCheck) {
		boolean value = false;

		if (Pattern.compile(emailStrength).matcher(valToCheck).find()){
			value = true;
		}
		return value;
	}

	public void setEmailStrength(String emailStrength) {
		this.emailStrength = emailStrength;
	}
}
