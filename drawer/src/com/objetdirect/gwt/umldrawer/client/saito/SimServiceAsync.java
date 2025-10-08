package com.objetdirect.gwt.umldrawer.client.saito;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimServiceAsync {
	void urlToCompare(String studentId, int exercisesId, AsyncCallback <Map<String,Map<String,String>>> callback);
	
	void removeRelation(String studentId, int exercisesId, AsyncCallback <Map<String,Map<String,String>>> callback);
	
	void addColor(String studentId, int exercisesId, AsyncCallback <Map<String,Map<String,String>>> callback);
	
	void getSim(String studentId, int exercisesId, AsyncCallback <Double> callback);

}
