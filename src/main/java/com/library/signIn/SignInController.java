package com.library.signIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.businessModels.IUserBasicInfo;
import com.library.businessModels.Salt;
import com.library.businessModels.User;
import com.library.businessModels.UserBasicInfo;
import com.library.dao.DAOFactory;
import com.library.dao.IUserDAO;
import com.library.messages.Messages;
import com.library.validations.ValidateUserForms;
import com.library.validations.ValidateUserFormsAbstract;
import com.library.welcomePage.UserSessionDetail;

public class SignInController implements ISignInController {

	private IUserBasicInfo userBasicInfo = null;
	private User user = null;
	private Salt salt = null;
	private HttpSession httpSession = null;
	private List<Entry<String, String>> listofValidationErrors = null;
	private static final Logger logger = LogManager.getLogger(SignInController.class);
	private ValidateUserForms validateForm=null;

	public SignInController(User user, HttpSession httpSession) throws Exception {
		this.user = user;
		this.salt = new Salt();
		this.userBasicInfo = new UserBasicInfo();
		this.httpSession = httpSession;
		validateForm = new ValidateUserForms();
		
	}

	public String checkUserCredential() throws Exception {
		String redirectToWelcome = Messages.WelcomePageRedirect.getMessage();
		DAOFactory factory = new DAOFactory();
		IUserDAO userDAO = factory.makeUserDAO();
		addSaltToPassword();
		AuthenticatedUsers authUsers = AuthenticatedUsers.instance();
		authUsers.addAuthenticatedUser(httpSession, userBasicInfo.getEmail());
		if (userDAO.checkPassword(user.getEmail(), salt.getSaltedPassword())) {
			logger.log(Level.ALL, "User has successfully logged in.");
			UserSessionDetail.setAvailableAdmin(false);
			UserSessionDetail.setAvailableUserID(authUsers.getUserEmail(httpSession));
			UserSessionDetail.setClientActiveStatus(Messages.Logout.getMessage());
			logger.log(Level.ALL, "checkUserCredential method implemented successfully.");
			return redirectToWelcome;

		} else if (userBasicInfo.getEmail().equals(ValidateUserFormsAbstract.isAdmin)
				&& userBasicInfo.getPassword().equals(ValidateUserFormsAbstract.isAdminPwd)) {
			UserSessionDetail.setAvailableAdmin(true);
			UserSessionDetail.setAvailableUserID(Messages.AdminEmailID.getMessage());
			UserSessionDetail.setClientActiveStatus(Messages.Logout.getMessage());
			logger.log(Level.ALL, "checkUserCredential method implemented successfully.");
			return redirectToWelcome;
		}
		return redirectToWelcome;
	}

	private void addSaltToPassword() {
		salt.setSaltedPassword(user.getPassword(), ValidateUserFormsAbstract.saltValue);
	}

	public ArrayList<Entry<String, String>> validateSignIn() throws Exception {
		userBasicInfo.setEmail(user.getEmail());
		userBasicInfo.setPassword(user.getPassword());
		validateForm.setValidationRulesandStatement();
		listofValidationErrors = validateForm.signInUserData(userBasicInfo);
		logger.log(Level.ALL, "validateSignIn method implemented successfully.");
		return (ArrayList<Entry<String, String>>) listofValidationErrors;
	}
}
