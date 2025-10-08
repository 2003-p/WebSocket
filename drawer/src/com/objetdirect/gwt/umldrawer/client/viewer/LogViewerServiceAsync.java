package com.objetdirect.gwt.umldrawer.client.viewer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogViewerServiceAsync {
	@SuppressWarnings("rawtypes")
	public void getLog(String studentId, int exercisesId, AsyncCallback callback);
	public void getEditEventList(String studentId, int exercisesId, AsyncCallback callback);
	public void getEditEventList(AsyncCallback callback);
	public void getAllEditEventList(String studentId, int exercisesId, AsyncCallback callback);
	public void getAllEditEventList(AsyncCallback callback);
	public void getEditEventListForReplay(String studentId, int exercisesId, AsyncCallback callback);
}