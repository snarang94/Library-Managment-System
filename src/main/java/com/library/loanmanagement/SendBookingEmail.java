package com.library.loanmanagement;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.UserItem;
import com.library.email.EmailDetails;
import com.library.email.SendEmail;
import com.library.forgotPassword.ForgotPasswordController;

public class SendBookingEmail {

	private EmailDetails emailDetails;
	private static final Logger logger = LogManager.getLogger(ForgotPasswordController.class);

	public SendBookingEmail() {

		emailDetails = new EmailDetails();
	}

	public void sendBookingItemEmail(UserItem item) {
		String title = item.getTitle();
		String email = item.getEmail();
		String category = item.getCategory();
		emailDetails.setSubject("Reg : " + category + " titled " + title + " is borrowed for you!");
		emailDetails
				.setBody("Dear " + email + " ,<br/><br/>" + "This is to notify you that the " + category + " titled "
						+ title + " is booked for you!" + "<br/><br/>" + "Regards, " + "<br/>" + " Public Library.");
		emailDetails.setUserEmailID(email);
		try {
			SendEmail.sendmail(emailDetails);
		} catch (MessagingException | IOException e) {

			logger.log(Level.ALL, "Check Email Sender class!", e);
		}
	}

}
