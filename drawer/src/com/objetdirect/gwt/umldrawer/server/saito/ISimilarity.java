package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.Map;

public interface ISimilarity {
	public void show();
	public Map<String,String> getSimMatchMap();    //類似度１
	public Map<String,String> getSimMisMatchMap(); //類似度0
}
