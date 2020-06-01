package com.library.businessModels;

public class Music extends  LibraryItem {

	private String artist;
	private String recordLabel;

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getRecordLabel() {
		return recordLabel;
	}

	public void setRecordLabel(String recordLabel) {
		this.recordLabel = recordLabel;
	}

}
