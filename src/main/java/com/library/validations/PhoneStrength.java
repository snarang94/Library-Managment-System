package com.library.validations;

public class PhoneStrength {
	private String phoneStrength;

	public boolean isValidPhoneNumber(String valToCheck) {
		boolean value = false;

		if (Integer.parseInt(phoneStrength) <= (valToCheck.length())) {
			value = true;
		}
		return value;
	}

	public void setPhoneStrength(String phoneStrength) {
		this.phoneStrength = phoneStrength;
	}
}
