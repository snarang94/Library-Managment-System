package com.library.validations;

public enum ValidateFormEnums {
	passwordLengthKeyRoot("passwordlength"),
	passwordUpperKeyRoot("passwordUpperCase"),
	passwordLowerKeyRoot("passwordLowerCase"),
	passwordSymbolsKeyRoot("passwordSymbols"),
	emailRegexKeyRoot("emailRegex"),
	phoneCheckKeyRoot("phoneCheck"),
	adminIDCheckKeyRoot("adminId"),
	adminPasswordCheckKeyRoot("adminPwd"),
	emailErrorKeyRoot("emailErrorString"),
	passwordErrorKeyRoot("passwordErrorString"),
	emptyErrorKeyRoot("emptyErrorString"),
	phoneErrorKeyRoot("phoneErrorString"),
	cpasswordErrorKeyRoot("cpasswordErrorString"),
	saltKeyRoot("salt"),
	filePathToValidations("AuthenticationRules.xml"),
	filePathToErrorStatements("ValidationStatements.xml");
	
	
	private final String statement;

	private ValidateFormEnums(final String statement) {
		this.statement = statement;
	}

	public String getStatement() {
		return statement;
	}
}
