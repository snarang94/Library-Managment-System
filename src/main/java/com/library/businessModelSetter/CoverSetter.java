package com.library.businessModelSetter;

import java.sql.ResultSet;

import com.library.businessModels.Cover;

public class CoverSetter implements ICoverMapper {
	
	@Override
	public Cover setCover(ResultSet resultSet) {
		try {
			Cover cover = new Cover();
			cover.setItemID(resultSet.getInt("Item_ID"));
			cover.setCoverBlob(resultSet.getBlob("Cover_Blob"));
			cover.setFileExtension(resultSet.getString("File_Extension"));
			return cover;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
