package com.objetdirect.gwt.umldrawer.client.reflection;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.beans.OccurrenceReason;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;

public interface ReflectionService extends RemoteService{
	public Reflection addReflection(Reflection reflection);
	public Boolean removeReflection(Reflection reflection);
	public List<Reflection> getReflectionList( String studentId, int exerciseId);
	public List<OccurrenceReason> getAllOccurrenceReasonList();
	public List<CheckItem> getAllCheckItemList();
	public void registerReflectionEventAndReflection(Reflection reflection, String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
			  String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
			  String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId);
	public void updateCheckItem(List<CheckItem> checkItemList);
}
