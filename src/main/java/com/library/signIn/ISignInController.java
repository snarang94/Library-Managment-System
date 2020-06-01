package com.library.signIn;

import java.util.List;
import java.util.Map.Entry;

public interface ISignInController {

	public List<Entry<String, String>> validateSignIn() throws Exception;

	public String checkUserCredential() throws Exception;

}
