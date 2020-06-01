package com.library.mockDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.library.businessModels.IUserBasicInfo;
import com.library.businessModels.UserBasicInfo;

public class SignInMocked {
	private String email;
	private String password;
	private String salt = "-LMS";
	List arrItems;
	private IUserBasicInfo userBasicInfo;
	Map map;

	public SignInMocked() {
		userBasicInfo = new UserBasicInfo();
		addSaltToMockData();
	}

	public Map getCorruptMockData() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("devanshu0101@gmail.com");
		userBasicInfo.setPassword("1qaz!QAZ");
		arrItems.add(userBasicInfo);
		map.put("corrupt-data", arrItems);

		return map;
	}

	public Map getMockData() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("devanshu1@gmail.com");
		userBasicInfo.setPassword("123456789");
		arrItems.add(userBasicInfo);
		map.put("clean-data", arrItems);

		return map;
	}

	public IUserBasicInfo addSaltToMockData() {
		Map mapList = getMockData();
		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.containsKey("clean-data")) {
				ArrayList arrayList = (ArrayList) mapList.get("clean-data");
				userBasicInfo = (UserBasicInfo) arrayList.get(0);
				userBasicInfo.setPassword(userBasicInfo.getPassword().concat(salt));
			}
		}
		return userBasicInfo;
	}

	public Map getAdminMockData() {
		arrItems = new ArrayList<String>();
		map = new HashMap<String, ArrayList>();
		userBasicInfo.setEmail("admin");
		userBasicInfo.setPassword("admin");
		arrItems.add(userBasicInfo);
		map.put("admin-data", arrItems);

		return map;
	}

	public UserBasicInfo getDataForValidation() {
		userBasicInfo.setEmail("devanshu");
		userBasicInfo.setPassword("1qaz!QAZ");
		return (UserBasicInfo) userBasicInfo;
	}

}
