package com.objetdirect.gwt.umldrawer.client.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DesignPattern implements IsSerializable {
	private String spotItem;
	private int patternIndex;
	private int listSize;
	private String className;
	private List<String> checkedClassItemList = new ArrayList<String>();
	private boolean flag = true;
	
	public DesignPattern() {
		
	}
	
	public void setPatternIndex(int patternIndex) {
		this.patternIndex = patternIndex;
	}
	
	public int getPatternIndex() {
		return patternIndex;
	}
	
	public void setPatternListSize(int listSize) {
		this.listSize = listSize;
	}
	
	public int getPatternListSize() {
		return listSize;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setSpotItem(String spotItem) {
		this.spotItem = spotItem;
	}
	
	public String getSpotItem() {
		return spotItem;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public boolean getFlag() {
		return flag;
	}
	

}
