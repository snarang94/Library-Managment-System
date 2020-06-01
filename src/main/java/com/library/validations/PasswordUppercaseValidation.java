package com.library.validations;

public class PasswordUppercaseValidation {
	private String passwordUpper;

	private int countUpperChar(String input) {
		int upperCase = 0;
		// https://stackoverflow.com/questions/25224954/how-to-count-uppercase-and-lowercase-letters-in-a-string
		for (int k = 0; k < input.length(); k++) {
			if (Character.isUpperCase(input.charAt(k)))
				upperCase++;
		}
		return upperCase;
	}

	public boolean isValidUpperCase(String valToCheck) {
		boolean value = false;
		int upperOccurrence = countUpperChar(passwordUpper);
		if (upperOccurrence <= (valToCheck.length())) {
			value = true;
		}
		return value;
	}

	public void setPasswordUpper(String passwordUpper) {
		this.passwordUpper = passwordUpper;
	}
}
