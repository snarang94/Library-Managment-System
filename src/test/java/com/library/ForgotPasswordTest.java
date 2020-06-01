package com.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.email.EmailDetails;
import com.library.email.SendEmail;
import com.library.mockDB.ForgotPasswordMocked;
@RunWith(SpringRunner.class)
public class ForgotPasswordTest {
	public static ForgotPasswordMocked forgotPasswordMocked;

	@BeforeClass
	public static void initializer() {
		forgotPasswordMocked = new ForgotPasswordMocked();
	}

	@Test
	public void testRecoveredPassword() {
		ForgotPasswordMocked fPassword = new ForgotPasswordMocked();
		EmailDetails eDetails = fPassword.initiateForgotUserMock();
		try {
			SendEmail.sendmail(eDetails);
			assertTrue(true);
		} catch (MessagingException | IOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testGetQuestion() {
		ForgotPasswordMocked fPassword = new ForgotPasswordMocked();
		assertNotNull(fPassword.getQuestion());
	}

	@Test
	public void testGetPassword() {
		ForgotPasswordMocked fPassword = new ForgotPasswordMocked();
		assertEquals(fPassword.getPassword("QWERqwer1!@SALT"), "QWERqwer1!@");
		assertNotNull(fPassword.getPassword("QWERqwer1!@SALT"));
	}

	@Test
	public void testInitiateForgotPassword() {
		EmailDetails emailDetails = null;
		ForgotPasswordMocked fPassword = new ForgotPasswordMocked();
		emailDetails = fPassword.initiateForgotUserMock();
		assertNotNull(emailDetails);
		assertEquals(emailDetails.getAdminEmailID(), "group2library2@gmail.com");
		assertEquals(emailDetails.getAdminPassword(), "Rel7.xPass!");
		assertEquals(emailDetails.getSubject(),"TEST EMAIL, IGNORE.");
		assertNotNull(emailDetails.getBody());
	}
}
