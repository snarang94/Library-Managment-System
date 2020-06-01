package com.library;

import static org.assertj.core.api.Assertions.contentOf;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.library.businessModels.UserBasicInfo;
import com.library.mockDB.ConfigurableBLogicMock;
import com.library.mockDB.SignInMocked;
import com.library.validations.EmailStrength;
import com.library.validations.PasswordLengthValidation;
import com.library.validations.PasswordLowercaseValidation;
import com.library.validations.PasswordSymbolValidation;
import com.library.validations.PasswordUppercaseValidation;
import com.library.validations.PhoneStrength;
import com.library.validations.ValidateUserForms;

public class ConfigurableBLogicTest {
	public static SignInMocked signInMocked;

	@BeforeClass
	public static void initializer() throws Exception {
		signInMocked = new SignInMocked();
	}

	@Test
	public void testPasswordLength() {
		PasswordLengthValidation plen = new PasswordLengthValidation();
		plen.setPasswordLength("3");
		boolean isValid = plen.isValidLength(ConfigurableBLogicMock.getMockPassword());
		assertTrue("Password length is not good to go!", isValid);
	}

	@Test
	public void testPasswordLower() {
		PasswordLowercaseValidation plower = new PasswordLowercaseValidation();
		plower.setPasswordLowerCase("1");
		boolean isValid = plower.isValidLowerCase(ConfigurableBLogicMock.getMockPassword());
		assertTrue("Password lower characters are not good to go!", isValid);
	}

	@Test
	public void testPasswordUpper() {
		PasswordUppercaseValidation pupper = new PasswordUppercaseValidation();
		pupper.setPasswordUpper("1");
		boolean isValid = pupper.isValidUpperCase(ConfigurableBLogicMock.getMockPassword());
		assertTrue("Password Uppert characters are not good to go!", isValid);
	}

	@Test
	public void testPasswordSymbols() {
		PasswordSymbolValidation psymb = new PasswordSymbolValidation();
		psymb.setPasswordSymbols("[@#$%]");
		boolean isValid = psymb.isValid(ConfigurableBLogicMock.getMockPassword());
		assertTrue("Password symbols are not good to go!", isValid);
	}

	@Test
	public void testEmailStrength() {
		EmailStrength emailStrength = new EmailStrength();
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		emailStrength.setEmailStrength(regex);
		boolean isValid = emailStrength.isValidEmail(ConfigurableBLogicMock.getMockEmail());
		assertTrue("Email address is not good to go!", isValid);
	}

	@Test
	public void testPhoneStrength() {
		PhoneStrength phone = new PhoneStrength();
		phone.setPhoneStrength("10");
		boolean isValid = phone.isValidPhoneNumber(ConfigurableBLogicMock.getMockPhone());
		assertTrue("Phone length is not good to go!", isValid);
	}
}
