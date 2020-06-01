package com.library.email;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	public static void sendmail(EmailDetails details)
		throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(details.getAdminEmailID(),details.getAdminPassword());
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(details.getUserEmailID(), false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getUserEmailID()));
		msg.setSubject(details.getSubject());
		msg.setContent(details.getBody(), "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(details.getBody(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
