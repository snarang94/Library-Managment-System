package com.library.forgotPassword;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.dao.DAOFactory;
import com.library.dao.IUserDAO;
import com.library.email.EmailDetails;
import com.library.email.SendEmail;
import com.library.validations.ValidateUserFormsAbstract;

public class RecoverPassword extends RecoverPasswordAbstract {
	private String securityQuestion;
	private String securityQuestionAnswer;
	private String email;
	EmailDetails details = null;
	private static final Logger logger = LogManager.getLogger(RecoverPassword.class);

	public RecoverPassword() {
		details = new EmailDetails();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}

	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}

	protected boolean sendEmailToUser() throws AddressException, MessagingException, IOException {
		details.setUserEmailID(email);
		details.setSubject("LMS reminder for password.");
		fetchSaltedPwdFromDB();
		SendEmail.sendmail(details);
		emailSent = true;
		logger.log(Level.ALL, "Email sent successfully to the user => ");
		return emailSent;
	}

	private void fetchSaltedPwdFromDB() {
		DAOFactory factory = new DAOFactory();
		IUserDAO user = factory.makeUserDAO();
		String passwordFromDB = user.getEmailRelatedPassword(details.getUserEmailID());
		if (!passwordFromDB.isEmpty()) {
			details.setBody("The password is: " + passwordFromDB.replace(ValidateUserFormsAbstract.saltValue, ""));
			logger.log(Level.ALL, "Email is sent with the details of password to the registered user.");
		} else {
			details.setBody("You don't have registered EmailID, please register again to the library.");
			logger.log(Level.ALL, "You don't have registered EmailID, please register again to the library.");
		}

	}
}
