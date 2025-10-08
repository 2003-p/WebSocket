package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface DiffService extends RemoteService {

	Map<String,Map<String,String>> getDiffMap(String student_id,int exercise_id);

}
