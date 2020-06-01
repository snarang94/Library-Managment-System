package com.library.borrowItem;

public enum StatusEnum {

	BORROWED("Borrowed"), 
	ONHOLD("Reserved"), 
	AVAILABLE("Borrow"),
	RESERVE("Reserve");

	private String status;

	private StatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
