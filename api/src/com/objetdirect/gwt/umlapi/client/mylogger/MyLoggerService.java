package com.objetdirect.gwt.umlapi.client.mylogger;

import com.google.gwt.user.client.rpc.RemoteService;



public interface MyLoggerService extends RemoteService{

//	public void registEditEvent(String logData,String canvasUrl, String step);
	public boolean registEditEvent(String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
			String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId) ;

}
