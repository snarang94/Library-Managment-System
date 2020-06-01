package com.library.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.library.businessModels.IUserBasicInfo;
import com.library.businessModels.IUserExtendedInfo;
import com.library.parsers.XmlParser;

public abstract class ValidateUserFormsAbstract {
	protected String passwordErrorStatement;
	protected String emailErrorStatement;
	protected String blankErrorStatement;
	protected String phoneErrorStatement;
	protected String cpasswordErrorStatement;
	public static String isAdminPwd;
	public static String isAdmin;
	public static String saltValue;
	protected List<Object> passwordListRules = new ArrayList<Object>();
	protected List<Object> emailPhoneListRules = new ArrayList<Object>();
	protected List<String> errorStringToDisplay = new ArrayList<String>();

	public abstract ArrayList<Map.Entry<String, String>> signUpUserData(IUserBasicInfo userBasicInfo,
			IUserExtendedInfo userExtendedInfo) throws Exception;

	public abstract ArrayList<Map.Entry<String, String>> signInUserData(IUserBasicInfo userBasicInfo) throws Exception;

	public final void setValidationRulesandStatement() throws Exception {
		setValidationRules();
		setErrorStringToHTML();
	}

	private void setValidationRules() throws Exception {
		List<Map.Entry<String, String>> list = XmlParser.parse(ValidateFormEnums.filePathToValidations.getStatement());
		String passwordLengthKey = ValidateFormEnums.passwordLengthKeyRoot.getStatement();
		String passwordUpperCase = ValidateFormEnums.passwordUpperKeyRoot.getStatement();
		String passwordLowerCase = ValidateFormEnums.passwordLowerKeyRoot.getStatement();
		String passwordSymbol = ValidateFormEnums.passwordSymbolsKeyRoot.getStatement();
		String emailRegex = ValidateFormEnums.emailRegexKeyRoot.getStatement();
		String phoneCheck = ValidateFormEnums.phoneCheckKeyRoot.getStatement();
		String adminID = ValidateFormEnums.adminIDCheckKeyRoot.getStatement();
		String adminPassword = ValidateFormEnums.adminPasswordCheckKeyRoot.getStatement();
		String saltKey = ValidateFormEnums.saltKeyRoot.getStatement();

		for (int i = 0; i < list.size(); i++) {
			String getKeyFromList = list.get(i).getKey();
			String getValueFromList = list.get(i).getValue();
			if (passwordLengthKey.equals(getKeyFromList)) {
				PasswordLengthValidation pwdLength = new PasswordLengthValidation();
				pwdLength.setPasswordLength(getValueFromList);
				passwordListRules.add(pwdLength);
				continue;
			} else if (passwordUpperCase.equals(getKeyFromList)) {
				PasswordUppercaseValidation pwdUpper = new PasswordUppercaseValidation();
				pwdUpper.setPasswordUpper(getValueFromList);
				passwordListRules.add(pwdUpper);
				continue;
			} else if (passwordLowerCase.equals(getKeyFromList)) {
				PasswordLowercaseValidation pwdLower = new PasswordLowercaseValidation();
				pwdLower.setPasswordLowerCase(getValueFromList);
				passwordListRules.add(pwdLower);
				continue;
			} else if (passwordSymbol.equals(getKeyFromList)) {
				PasswordSymbolValidation pwdSymbols = new PasswordSymbolValidation();
				pwdSymbols.setPasswordSymbols(getValueFromList);
				passwordListRules.add(pwdSymbols);
				continue;
			} else if (emailRegex.equals(getKeyFromList)) {
				EmailStrength emailStrength = new EmailStrength();
				emailStrength.setEmailStrength(getValueFromList);
				emailPhoneListRules.add(emailStrength);
				continue;
			} else if (phoneCheck.equals(getKeyFromList)) {
				PhoneStrength phoneStrength = new PhoneStrength();
				phoneStrength.setPhoneStrength(getValueFromList);
				emailPhoneListRules.add(phoneStrength);
				continue;
			} else if (adminID.equals(getKeyFromList)) {
				isAdmin = getValueFromList;
				continue;
			} else if (adminPassword.equals(getKeyFromList)) {
				isAdminPwd = getValueFromList;
				continue;
			} else if (saltKey.equals(getKeyFromList)) {
				saltValue = getValueFromList;
				continue;
			}
		}
	}

	private void setErrorStringToHTML() throws Exception {
		this.emailErrorStatement = "";
		this.blankErrorStatement = "";
		this.passwordErrorStatement = "";
		this.phoneErrorStatement = "";
		this.cpasswordErrorStatement = "";
		String emailError = ValidateFormEnums.emailErrorKeyRoot.getStatement();
		String passwordError = ValidateFormEnums.passwordErrorKeyRoot.getStatement();
		String emptyError = ValidateFormEnums.emptyErrorKeyRoot.getStatement();
		String phoneError = ValidateFormEnums.phoneErrorKeyRoot.getStatement();
		String cpasswordError = ValidateFormEnums.cpasswordErrorKeyRoot.getStatement();
		List<Map.Entry<String, String>> list = XmlParser.parse(ValidateFormEnums.filePathToErrorStatements.getStatement());
		for (int i = 0; i < list.size(); i++) {
			String getKeyFromList = list.get(i).getKey();
			String getValFromList = list.get(i).getValue();
			if (emailError.equals(getKeyFromList)) {
				this.emailErrorStatement = getValFromList;
				continue;
			} else if (passwordError.equals(getKeyFromList)) {
				this.passwordErrorStatement = getValFromList;
				continue;
			} else if (emptyError.equals(getKeyFromList)) {
				this.blankErrorStatement = getValFromList;
				continue;
			} else if (phoneError.equals(getKeyFromList)) {
				this.phoneErrorStatement = getValFromList;
				continue;
			} else if (cpasswordError.equals(getKeyFromList)) {
				this.cpasswordErrorStatement = getValFromList;
				continue;
			}
		}
	}
}
