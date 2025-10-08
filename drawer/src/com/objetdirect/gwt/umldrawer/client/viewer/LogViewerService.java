package com.objetdirect.gwt.umldrawer.client.viewer;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;




public interface LogViewerService extends RemoteService{
	//@gwt.typeArgs <java.lang.String[]>
	public List<String> getLog(String studentId, int exercisesId);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.EditEvent[]>
	public List<EditEvent> getEditEventList(String studentId, int exercisesId);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.EditEvent[]>
	public List<EditEvent> getEditEventList();
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.EditEvent[]>
	public List<EditEvent> getAllEditEventList(String studentId, int exercisesId);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.EditEvent[]>
	public List<EditEvent> getAllEditEventList();
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.EditEvent[]>
	public List<EditEvent> getEditEventListForReplay(String studentId, int exercisesId);


}
