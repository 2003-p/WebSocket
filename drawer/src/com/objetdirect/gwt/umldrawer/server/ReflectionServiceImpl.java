package com.objetdirect.gwt.umldrawer.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.beans.OccurrenceReason;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class ReflectionServiceImpl extends RemoteServiceServlet implements ReflectionService{

	@Override
	public Reflection addReflection(Reflection reflection) {
		Dao dao = new Dao();
		Reflection newReflection = dao.addReflection(reflection);
		dao.addOccurrenceReason(newReflection);
		dao.addCheckItem(newReflection);
		dao.addModificationEvent(newReflection);
		return newReflection;
	}

	@Override
	public void registerReflectionEventAndReflection(Reflection reflection, String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
			String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
			String targetPart, String beforeEdit, String afterEdit,  String canvasUrl,  int umlArtifactId) {

		int canvasId = 1;
		int defaultDifficulty = 1;

		Dao dao = new Dao();
		com.objetdirect.gwt.umlapi.server.dao.Dao apiDao = new com.objetdirect.gwt.umlapi.server.dao.Dao();
		apiDao.registEditEvent(studentId, exercisesId, preEventId, editEvent, eventType, targetType, targetId, linkKind, rightObjectId, leftObjectId, targetPart, beforeEdit, afterEdit, canvasId, canvasUrl, defaultDifficulty, umlArtifactId);

		List<EditEvent> eventList = dao.getEditEventList(studentId, exercisesId);
		EditEvent e = new EditEvent();
		for(int i = eventList.size()-1 ; i >= 0 ; i--){
			if( eventList.get(i).getEventType().equals("Check") ){
				e = eventList.get(i);
				break;
			}
		}
		reflection.setOccurrencePoint(e);

		Reflection newReflection = dao.addReflection(reflection);
		dao.addOccurrenceReason(newReflection);
		dao.addCheckItem(newReflection);
		dao.addModificationEvent(newReflection);

	}

	@Override
	public Boolean removeReflection(Reflection reflection){
		Dao dao = new Dao();
		return dao.removeReflection(reflection);
	}

	@Override
	public List<Reflection> getReflectionList(String studentId, int exerciseId) {
		Dao dao = new Dao();
		List<Reflection> reflectionList = dao.getReflectionList(studentId, exerciseId);
		for(Reflection r : reflectionList){
			r.setReasonList( dao.getOccurrenceReasonList(r.getReflectionId()) );
			r.setCheckedItemList( dao.getCheckItemList(r.getReflectionId() ) );
			r.setModificationEventList(dao.getModificationEventList(r.getReflectionId()));
			//TODO setModificationEvent
		}
		return reflectionList;
	}

	@Override
	public List<OccurrenceReason> getAllOccurrenceReasonList() {
		Dao dao = new Dao();
		return dao.getAllOccurrenceReasonList();
	}

	@Override
	public List<CheckItem> getAllCheckItemList() {
		Dao dao = new Dao();
		return dao.getAllCheckItemList();
	}

	@Override
	public void updateCheckItem(List<CheckItem> checkItemList) {
		Dao dao = new Dao();
		dao.updateCheckItem(checkItemList);

	}

}
