package com.objetdirect.gwt.umldrawer.client.progress;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.CanvasUrlManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.canvas.CanvasService;
import com.objetdirect.gwt.umldrawer.client.canvas.CanvasServiceAsync;
import com.objetdirect.gwt.umldrawer.client.user.GetUserService;
import com.objetdirect.gwt.umldrawer.client.user.GetUserServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerService;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerServiceAsync;

public class ProgressViewPanel extends VerticalPanel{
	private List<ProgressPanel> progressPanelList;
	private int pageNum = 3; //FIX ME
	private List<UMLArtifact>answer;
	private List<String> studentIdList;
	private Map<String, List<EditEvent>> editEventListMap;


	public ProgressViewPanel(){
		editEventListMap = new HashMap<String, List<EditEvent>>();
		init();
	}

	public void init(){
		getAnswer(Session.exerciseId);
	}
	public void getAnswer(int exerciseId) {
		CanvasServiceAsync async = (CanvasServiceAsync)GWT.create(CanvasService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getAnswer";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			public void onSuccess(Object result){
				CanvasUrlManager cum = new CanvasUrlManager();
				answer = cum.fromURL( (String)result, false);
				getStudentIdList();
			}
			public void onFailure(Throwable caught){
				Window.alert( caught.toString());
			}
		};
		async.getAnswer( exerciseId, callback);

	}

	public void getStudentIdList(){
		GetUserServiceAsync async = (GetUserServiceAsync)GWT.create(GetUserService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getUserList";
		entryPoint.setServiceEntryPoint(entryURL);

		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(Object result) {
				studentIdList = (List<String>) result;
				Collections.sort(studentIdList);
				for(String studentId: studentIdList){
					getEditEventList(studentId);
				}
			}
		};
		async.getUserList(callback);
	}

	private void getEditEventList(final String studentId){
		LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getEditEventListForReplay";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			public void onSuccess(Object result){
				if( (List<EditEvent>) result != null && ( (List<EditEvent>)result ).size() !=0 ){
					editEventListMap.put( studentId, (List<EditEvent>) result );
					ProgressPanel pp = new ProgressPanel(studentId, editEventListMap.get(studentId), answer);
					add(pp);
				}
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getEditEventListForReplay( studentId, Session.exerciseId, callback);
	}

	private void createProgressView(){
		int i=0;
		HorizontalPanel hp = new HorizontalPanel();
		for(String studentId :studentIdList){
			i++;
			if(i % pageNum == 0){
				if(i !=0) this.add(hp);
				hp = new HorizontalPanel();
				ProgressPanel pp = new ProgressPanel(studentId, editEventListMap.get(studentId), answer);
				hp.add(pp);
			}
			else{
				ProgressPanel pp = new ProgressPanel(studentId, editEventListMap.get(studentId), answer);
				hp.add(pp);
				if(i == studentIdList.size())  this.add(hp);
			}
		}
	}
}
