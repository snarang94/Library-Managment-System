package com.library.forgotPassword;

import java.io.IOException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.validations.ValidateUserForms;

public class ForgotPasswordController implements IForgotPasswordController {

	private boolean status = false;
	private static final Logger logger = LogManager.getLogger(ForgotPasswordController.class);
	private RecoverPassword recoverDetails = null;

	public ForgotPasswordController(RecoverPassword recoverDetails) throws Exception {
		this.recoverDetails = recoverDetails;	
		new ValidateUserForms().setValidationRulesandStatement();
	}

	public boolean recoverPassword() throws AddressException, MessagingException, IOException {
		if (isAnswerCorrect()) {
			RecoverPassword recoverPassword = new RecoverPassword();
			recoverPassword.setEmail(recoverDetails.getEmail());
			recoverPassword.setSecurityQuestionAnswer(recoverDetails.getSecurityQuestionAnswer());
			recoverPassword.setSecurityQuestion(recoverDetails.getSecurityQuestion());
			status = recoverPassword.sendEmailToUser();
			logger.log(Level.ALL, "Email status => ");
		}
		return status;
	}

	public String setQuestion() {
		double x = (int) (Math.random() * 10) + 0;
		recoverDetails.setSecurityQuestion(Double.toString(x));
		return recoverDetails.getSecurityQuestion();
	}

	public String getQuestion() {
		return recoverDetails.getSecurityQuestion();
	}

	private boolean isAnswerCorrect() {
		if (recoverDetails.getSecurityQuestionAnswer().equals(recoverDetails.getSecurityQuestion())) {
			return true;
		} else {
			return false;
		}
	}
}
