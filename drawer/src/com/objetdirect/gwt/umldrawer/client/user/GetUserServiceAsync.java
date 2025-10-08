package com.objetdirect.gwt.umldrawer.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.gwt.umldrawer.client.beans.Student;

public interface GetUserServiceAsync {
	public void getUserList(AsyncCallback<List<String>> callback);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.Student[]>
	public void getUserListForReplay(int exerciseId, AsyncCallback<List<Student>> callback);
}
