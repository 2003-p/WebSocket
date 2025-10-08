package com.objetdirect.gwt.umlapi.client.mylogger;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyLoggerServiceAsync {
	@SuppressWarnings("rawtypes")
//	public void registEditEvent(String logData, String canvasUrl, String step, AsyncCallback callback);
	public void registEditEvent(String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
											String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
											String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId, AsyncCallback callback) ;
}
