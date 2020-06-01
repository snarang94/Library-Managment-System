package com.library.parsers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.library.businessModels.UserItem;

public class JsonStringParser {
	
	//This function is referred from https://dzone.com/articles/deserializing-json-java-object
	
	public List<UserItem> parseSelections(String selections)
	{
		Gson gson = new Gson();
		Type type = new TypeToken<List<UserItem>>(){}.getType();
		List<UserItem> items = gson.fromJson(selections, type);
		return items;
	
	}
	
}
