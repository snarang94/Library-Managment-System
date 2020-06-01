package com.library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.VfsResource;
import org.springframework.test.context.junit4.SpringRunner;

import com.library.businessModels.UserBasicInfo;
import com.library.businessModels.UserExtendedInfo;
import com.library.mockDB.SignUpMocked;
import com.library.validations.ValidateUserForms;

@RunWith(SpringRunner.class)
public class SignUpTest {
	private static SignUpMocked signUpMocked;
	private static Map mapList;
	private List arrayList;
	private UserBasicInfo userBasicInfo = null;
	private UserExtendedInfo userExtendInfo = null;
	ArrayList<Map.Entry<String, String>> arralistVal = new ArrayList<Map.Entry<String, String>>();
	private static ValidateUserForms vForms= null;
	@BeforeClass
	public static void initializer() throws Exception {
		signUpMocked = new SignUpMocked();
		vForms= new ValidateUserForms();
	}

	@Test
	public void testSignUpwithCorrectUserInfo() {
		mapList = signUpMocked.getMockData();

		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.containsKey("clean-data")) {
				arrayList = (ArrayList) mapList.get("clean-data");
				userBasicInfo = (UserBasicInfo) arrayList.get(0);
				assertEquals("123456789", userBasicInfo.getPassword());
				assertEquals("devanshu1@gmail.com", userBasicInfo.getEmail());
				assertTrue(userBasicInfo.getEmail().contains("@"));
				userExtendInfo = (UserExtendedInfo) arrayList.get(1);
				assertEquals("deva sriv", userExtendInfo.getFullname());
				assertEquals("9024031714", userExtendInfo.getPhone());
				assertEquals("123456789", userExtendInfo.getCPassword());
				assertTrue(userBasicInfo.getPassword() == userExtendInfo.getCPassword());
				assertTrue(userExtendInfo.getPhone().length() == 10);
			}
		}
	}

	@Test
	public void testSignUpwithINCorrectUserInfo() {
		mapList = signUpMocked.getCorruptMockData();
		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.containsKey("corrupt-data")) {
				arrayList = (ArrayList) mapList.get("corrupt-data");
				userBasicInfo = (UserBasicInfo) arrayList.get(0);
				assertEquals("1qaz!QAZ", userBasicInfo.getPassword());
				assertEquals("devanshu0101@gmail.com", userBasicInfo.getEmail());
				assertTrue(userBasicInfo.getEmail().contains("@"));
				userExtendInfo = (UserExtendedInfo) arrayList.get(1);
				assertEquals("devanshu sriv", userExtendInfo.getFullname());
				assertEquals("902", userExtendInfo.getPhone());
				assertEquals("1qazZAQ!", userExtendInfo.getCPassword());
				assertTrue(userBasicInfo.getPassword() != userExtendInfo.getCPassword());
				assertTrue(userExtendInfo.getPhone().length() != 10);
			}
		}
	}
	@Test
	public void testSignUpValidation() {
		mapList = signUpMocked.getMockDataForValidation();
		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.containsKey("validation-data")) {
				arrayList = (ArrayList) mapList.get("validation-data");
				userBasicInfo = (UserBasicInfo) arrayList.get(0);
				userExtendInfo = (UserExtendedInfo) arrayList.get(1);
				try {
					vForms.setValidationRulesandStatement();
					arralistVal = vForms.signUpUserData(userBasicInfo,userExtendInfo);
					assertTrue("SignIn validation successfull", arralistVal.isEmpty());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
