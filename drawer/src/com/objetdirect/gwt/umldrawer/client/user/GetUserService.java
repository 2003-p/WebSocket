package com.objetdirect.gwt.umldrawer.client.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.Student;

public interface GetUserService extends RemoteService{
	//@gwt.typeArgs <java.lang.String[]>
	public List<String> getUserList();
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.Student[]>
	public List<Student> getUserListForReplay(int exerciseId);
}
