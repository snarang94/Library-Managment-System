package com.library.mockDB;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.email.EmailDetails;
import com.library.validations.ValidateUserForms;
import com.library.validations.ValidateUserFormsAbstract;

public class ForgotPasswordMocked {
	private EmailDetails eDetails = null;
	private List<String> arrItems = null;
	private static final Logger logger = LogManager.getLogger(ForgotPasswordMocked.class);

	public ForgotPasswordMocked() {
		eDetails = new EmailDetails();
		try {
			new ValidateUserForms().setValidationRulesandStatement();
		} catch (Exception e) {
			logger.log(Level.ALL, "Something went wrong in the Mock data of forgotpassword please refer logs.", e);
		}
	}

	public List<String> addUserEmailIds() {
		arrItems = new ArrayList<String>();
		arrItems.add("ravi.nair@dal.ca");
		arrItems.add("sn891352@dal.ca");
		arrItems.add("dv960112@dal.ca");
		return arrItems;
	}
	private List<String> addUserPassword() {
		arrItems = new ArrayList<String>();
		arrItems.add("1qaz!QAZSALT");
		arrItems.add("QWERqwer1!@SALT");
		arrItems.add("listlist123!");
		return arrItems;
	}
	public EmailDetails initiateForgotUserMock() {
		List<String> emailIDs = addUserEmailIds();
		List<String> pwds = addUserPassword();
		String retrievedPwd =  getPassword(pwds.get(2));
		eDetails.setBody(retrievedPwd);
		eDetails.setUserEmailID(emailIDs.get(2));
		eDetails.setSubject("TEST EMAIL, IGNORE.");
		return eDetails;
	}

	public String getPassword(String saltedPwd) {
		String val = "";
		val = saltedPwd.replace(ValidateUserFormsAbstract.saltValue, "");
		return val;
	}
	public String setQuestion() {
		double x = (int) (Math.random() * 10) + 0;
		return Double.toString(x);
	}

	public String getQuestion() {
		return setQuestion();
	}
}
