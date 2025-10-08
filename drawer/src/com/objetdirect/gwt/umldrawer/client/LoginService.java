package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.Student;

public interface LoginService extends RemoteService{
	public Student login(String studentId, String password);
}
