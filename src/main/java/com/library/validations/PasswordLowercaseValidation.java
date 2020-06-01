package com.library.validations;

public class PasswordLowercaseValidation {
	private String passwordLowerCase;

	private int countLowerChar(String input) {
		int lowerCase = 0;
		// https://stackoverflow.com/questions/25224954/how-to-count-uppercase-and-lowercase-letters-in-a-string
		for (int k = 0; k < input.length(); k++) {
			if (Character.isLowerCase(input.charAt(k)))
				lowerCase++;
		}
		return lowerCase;
	}

	public boolean isValidLowerCase(String valToCheck) {
		boolean value = false;
		int lowerOccurences = countLowerChar(this.passwordLowerCase);
		if (lowerOccurences <= valToCheck.length()) {
			value = true;
		}
		return value;
	}

	public void setPasswordLowerCase(String passwordLowerCase) {
		this.passwordLowerCase = passwordLowerCase;
	}
}
