package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import java.sql.Date;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.gwt.umlapi.client.helpers.Session;

public interface RecordCheckBoxStateServiceAsync {
	void recordCheckboxState(String student_id,
							  int exercise_id,
							  HashMap<String, Integer> tmpRecordState,
							  String openDate,
							  HashMap<String,String> reason,
							  AsyncCallback<Boolean> callback);
}
