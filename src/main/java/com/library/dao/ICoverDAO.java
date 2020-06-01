package com.library.dao;

import java.sql.Blob;
import com.library.businessModels.Cover;

public interface ICoverDAO {
	public Cover getCoverByID(int itemID);
	public boolean createCoverByID(int itemID, Blob cover, String fileExtension);
	public boolean deleteBlobByID(int itemID);
}
