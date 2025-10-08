package com.objetdirect.gwt.umldrawer.client.beans;

import com.google.gwt.user.client.rpc.IsSerializable;


public class CheckItem implements IsSerializable{
	private int checkItemId;
	private String CheckItem;

	public CheckItem(){
	}

	public CheckItem(int checkItemId, String checkItem) {
		super();
		this.checkItemId = checkItemId;
		CheckItem = checkItem;
	}

	public int getCheckItemId() {
		return checkItemId;
	}

	public void setCheckItemId(int checkItemId) {
		this.checkItemId = checkItemId;
	}

	public String getCheckItem() {
		return CheckItem;
	}

	public void setCheckItem(String checkItem) {
		CheckItem = checkItem;
	}



}
