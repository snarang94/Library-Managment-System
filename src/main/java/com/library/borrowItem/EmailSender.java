package com.library.borrowItem;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.UserItem;
import com.library.email.EmailDetails;
import com.library.email.SendEmail;

public class EmailSender {

	EmailDetails emailDetails;
	private static final Logger logger = LogManager.getLogger(EmailSender.class);
	String title;
	String email;
	String category;
	
	public EmailSender(UserItem item) {
		title = item.getTitle();
		email = item.getEmail();
		category = item.getCategory();
		emailDetails = new EmailDetails();
		emailDetails.setUserEmailID(email);
	}
	
	public void sendBookingEmail() {	
		emailDetails.setSubject("Reg : "+category+" titled " + title + " is borrowed for you!!");
		emailDetails.setBody("Dear " + email + " ,<br/><br/>" + "This is to notify you that the "+category+"  titled " + title
				+ " is booked for you!" + "<br/><br/>" + "Regards, " + "<br/>" + " Public Library.");
		try {
			SendEmail.sendmail(emailDetails);
		} catch (MessagingException | IOException e) {

			logger.log(Level.ALL, "Check Email Sender class!", e);
		}

	}
	
	public void sendReserveEmail()
	{
		emailDetails.setSubject("Reg : "+category+" titled " + title + " is reserved for you!!");
		emailDetails.setBody("Dear " + email + " ,<br/><br/>" + "This is to notify you that the "+category+"  titled " + title
				+ " is reserved for you!" + "<br/><br/>" + "Regards, " + "<br/>" + " Public Library.");
		try {
			SendEmail.sendmail(emailDetails);
		} catch (MessagingException | IOException e) {

			logger.log(Level.ALL, "Check Email Sender class!", e);
		}
	}
}
