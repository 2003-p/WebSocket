package com.objetdirect.gwt.umldrawer.client.reflection;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.beans.OccurrenceReason;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;

public interface ReflectionServiceAsync {

	public void addReflection(Reflection reflection, AsyncCallback<Reflection> async);
	public void removeReflection(Reflection reflection, AsyncCallback<Boolean> async);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.Reflection[]>
	public void getReflectionList( String studentId, int exerciseId, AsyncCallback<List<Reflection>> async);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.OccurrenceReason[]>
	public void getAllOccurrenceReasonList(AsyncCallback<List<OccurrenceReason>> async);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.CheckItem[]>
	public void getAllCheckItemList(AsyncCallback<List<CheckItem>> async);
	public void registerReflectionEventAndReflection(Reflection reflection, String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
			  String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
			  String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId, AsyncCallback async);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.CheckItem[]>
	public void updateCheckItem(List<CheckItem> checkItemList,
			AsyncCallback<Void> callback);

}
