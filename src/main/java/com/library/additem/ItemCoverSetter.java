package com.library.additem;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.library.dao.DAOFactory;
import com.library.dao.ICoverDAO;
import com.library.dao.IDAOFactory;

public class ItemCoverSetter implements IItemCoverSetter {

	private IDAOFactory iDAOfactory;
	private boolean isCoverCreated;
	private ICoverDAO coverDAO;
	private static final Logger logger = LogManager.getLogger(ItemCoverSetter.class);

	public ItemCoverSetter() {
		iDAOfactory = new DAOFactory();
		coverDAO = iDAOfactory.makeCoverDAO();
	}
	
	// MutipartFile to Blob conversion function SerialBlob is referred from
	//https://stackoverflow.com/questions/18073659/how-to-convert-multipartfile-into-blob-type/21306339
	
	public boolean isCoverAddedToDatabase(int itemId, MultipartFile coverImage) {
		try {
			byte[] bytes;
			bytes = coverImage.getBytes();
			Blob coverBlob = new javax.sql.rowset.serial.SerialBlob(bytes);
			String[] fileNameTokens = coverImage.getOriginalFilename().split("\\.");
			String fileExtension = fileNameTokens[fileNameTokens.length - 1];
			isCoverCreated = coverDAO.createCoverByID(itemId, coverBlob, fileExtension);
		} catch (IOException e) {
			logger.log(Level.ALL, "IO exception error in creating cover for itemId["+itemId+"]", e);
		} catch (SQLException e) {
			logger.log(Level.ALL, "SQL syntax error in creating cover for itemId["+itemId+"]", e);
		}
		return isCoverCreated;
	}

}
