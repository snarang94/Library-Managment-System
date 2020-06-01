package com.library.signOut;

import javax.servlet.http.HttpSession;

public interface ISignOutObserver {
	public boolean notifyUserSignOut(HttpSession httpSession);
}
