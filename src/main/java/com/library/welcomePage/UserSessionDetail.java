package com.library.welcomePage;

import com.library.messages.Messages;

public class UserSessionDetail {
	private static boolean isAdminAvailable = false;
	private static String availableUserID;
	private static String clientActiveStatus = Messages.RegisterLogin.getMessage();

	public static boolean getAdminAvailable() {
		return isAdminAvailable;
	}

	public static void setAvailableAdmin(boolean isAdminAvailable) {
		UserSessionDetail.isAdminAvailable = isAdminAvailable;
	}

	public static String getAvailableUserID() {
		return availableUserID;
	}

	public static void setAvailableUserID(String availableUserID) {
		UserSessionDetail.availableUserID = availableUserID;
	}

	public static String getClientActiveStatus() {
		return clientActiveStatus;
	}

	public static void setClientActiveStatus(String loggingStatus) {
		UserSessionDetail.clientActiveStatus = loggingStatus;
	}
}
