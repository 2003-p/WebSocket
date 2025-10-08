package com.objetdirect.gwt.umldrawer.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerService;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerServiceAsync;

public class EditEventPanel extends AbsolutePanel{
	private List<EditEvent> eventList;
	private int idx;
	public EditEventPanel( String studentId, int exercisesId ){
		LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getEditEventList";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result){
				eventList = (List<EditEvent>) result;
				idx = eventList.size()-1;
			}
			public void onFailure(Throwable caught){
				Window.alert("Connection Error!");
			}
		};
		async.getEditEventList(studentId, exercisesId, callback);
	}


	public List<EditEvent> getEventList() {
		return eventList;
	}
	public void setEventList(List<EditEvent> eventList) {
		this.eventList = eventList;
	}


	public int getIdx() {
		return idx;
	}


	public void setIdx(int idx) {
		this.idx = idx;
	}

}
