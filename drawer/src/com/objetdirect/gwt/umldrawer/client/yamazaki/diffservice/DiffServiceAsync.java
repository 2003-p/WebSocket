package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DiffServiceAsync {

	 void getDiffMap(String student_id,int exercise_id,AsyncCallback<Map<String,Map<String,String>>> callback);

}
