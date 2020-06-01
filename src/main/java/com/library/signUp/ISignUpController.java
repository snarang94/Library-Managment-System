package com.library.signUp;

import java.util.List;
import java.util.Map.Entry;

public interface ISignUpController {

	List<Entry<String, String>> validateSignUp() throws Exception;

}
