package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface RecordCheckBoxStateService extends RemoteService {

	boolean recordCheckboxState(String student_id,
								 int exercise_id,
								 HashMap<String,Integer> tmpRecordState,
								 String openDate,
								 HashMap<String,String> reason);
}
