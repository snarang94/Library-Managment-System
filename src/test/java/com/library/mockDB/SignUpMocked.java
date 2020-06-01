package com.library.mockDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.businessModels.IUserBasicInfo;
import com.library.businessModels.IUserExtendedInfo;
import com.library.businessModels.User;
import com.library.businessModels.UserBasicInfo;
import com.library.businessModels.UserExtendedInfo;

public class SignUpMocked {
	public String fullName;
	public String phoneNumber;
	public String email;
	public String password;
	public String cpassword;
	List arrItems;
	private IUserBasicInfo userBasicInfo;
	private IUserExtendedInfo userExtendedInfo;
	Map map;

	public SignUpMocked() {
		userBasicInfo = new UserBasicInfo();
		userExtendedInfo = new UserExtendedInfo();
	}

	public Map getCorruptMockData() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("devanshu0101@gmail.com");
		userBasicInfo.setPassword("1qaz!QAZ");
		userExtendedInfo.setCPassword("1qazZAQ!");
		userExtendedInfo.setFullname("devanshu sriv");
		userExtendedInfo.setPhone("902");
		arrItems.add(userBasicInfo);
		arrItems.add(userExtendedInfo);
		map.put("corrupt-data", arrItems);
		return map;
	}

	public Map getMockData() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("devanshu1@gmail.com");
		userBasicInfo.setPassword("123456789");
		userExtendedInfo.setCPassword("123456789");
		userExtendedInfo.setFullname("deva sriv");
		userExtendedInfo.setPhone("9024031714");
		arrItems.add(userBasicInfo);
		arrItems.add(userExtendedInfo);
		map.put("clean-data", arrItems);
		return map;
	}

	public Map getMockDataForValidation() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("devanshu1@gmail.com");
		userBasicInfo.setPassword("1qaz!QAZ");
		userExtendedInfo.setCPassword("1qaz!QAZ");
		userExtendedInfo.setFullname("deva sriv");
		userExtendedInfo.setPhone("9024031714");
		arrItems.add(userBasicInfo);
		arrItems.add(userExtendedInfo);
		map.put("validation-data", arrItems);
		return map;
	}
}
