package com.library.signIn;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class AuthenticatedUsers {
	private static AuthenticatedUsers instance = null;
	private Map<String, String> authenticatedUsersMap = null;
	
	private AuthenticatedUsers() {
		authenticatedUsersMap = new HashMap<String, String>();
	}
	
	synchronized public static AuthenticatedUsers instance() {
		if(null == instance) {
			instance = new AuthenticatedUsers();
		}
		return instance;
	}
	
	public String getUserEmail(HttpSession httpSession) {
		if(authenticatedUsersMap.containsKey(httpSession.getId())) {
			return authenticatedUsersMap.get(httpSession.getId());
		}
		return null;
	}

	public void addAuthenticatedUser(HttpSession httpSession, String userEmail) {
		authenticatedUsersMap.put(httpSession.getId(), userEmail);
	}
	
	public void removeAuthenticatedUser(HttpSession httpSession) {
		authenticatedUsersMap.remove(httpSession.getId());
	}
	
	public boolean userIsAuthenticated(HttpSession httpSession) {
		return authenticatedUsersMap.containsKey(httpSession.getId());
	}
}
