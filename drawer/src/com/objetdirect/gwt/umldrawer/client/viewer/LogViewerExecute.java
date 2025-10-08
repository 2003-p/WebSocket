package com.objetdirect.gwt.umldrawer.client.viewer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerServiceAsync;

public class LogViewerExecute {

	static List<String> logData = new ArrayList<String>();

	public static List<String> getLog(String studentId ,int exercisesId){
		LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getLog";
		entryPoint.setServiceEntryPoint(entryURL);

		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result){
				LogViewerExecute.logData = (List<String>) result;
				System.out.println( (List<String>) result);
			}
			public void onFailure(Throwable caught){

			}
		};
		async.getLog(studentId, exercisesId, callback);
		return logData;


	}





}
