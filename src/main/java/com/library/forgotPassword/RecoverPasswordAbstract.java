package com.library.forgotPassword;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public abstract class RecoverPasswordAbstract {

	protected boolean emailSent = false;

	protected boolean getEmailSentStatus() {
		return emailSent;
	}
	
	protected abstract boolean sendEmailToUser() throws AddressException, MessagingException, IOException;

}
