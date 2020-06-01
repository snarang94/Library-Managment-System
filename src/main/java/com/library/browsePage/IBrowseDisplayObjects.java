package com.library.browsePage;

import java.util.List;

import com.library.businessModels.Display;

public interface IBrowseDisplayObjects {
	public List<Display> itemsByCategory(String category);
	public List<String> getCategories();
	public String getItemType();
}
