package com.library.forgotPassword;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface IForgotPasswordController {

	public boolean recoverPassword() throws AddressException,MessagingException, IOException;
	public String setQuestion();
	public String getQuestion();
}
