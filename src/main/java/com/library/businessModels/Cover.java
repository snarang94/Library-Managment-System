package com.library.businessModels;

import java.sql.Blob;

import org.springframework.web.multipart.MultipartFile;

public class Cover {
	private int itemID;
	private Blob coverBlob;
	private String fileExtension;
	private MultipartFile coverImage;
	
	public MultipartFile getCoverImage() {
		return coverImage;
	}
	
	 public void setCoverImage(MultipartFile coverImage) {
		this.coverImage = coverImage;
	}
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public Blob getCoverBlob() {
		return coverBlob;
	}
	public void setCoverBlob(Blob coverBlob) {
		this.coverBlob = coverBlob;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
}
