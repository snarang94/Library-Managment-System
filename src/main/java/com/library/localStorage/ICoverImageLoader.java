package com.library.localStorage;

public interface ICoverImageLoader {
	public String loadCoverImageByItemIdToDisk(int itemId, String pathToDirToSaveInto);
	public void deleteDynamicContent(String dynamicContentSubDirPath);
}
