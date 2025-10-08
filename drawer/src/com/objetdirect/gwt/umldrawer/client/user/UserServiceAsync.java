package com.objetdirect.gwt.umldrawer.client.user;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {
	public void addUser(String studentId, AsyncCallback callback);
	//public void changePassword(String studentId, String password, AsyncCallback callback);
	public void changePassword(String password, AsyncCallback callback);
	public void changePassword(String nowPassword, String newPassword, AsyncCallback callback);
	public void initializePassword(String studentId, AsyncCallback callback);
	public void setAdmin(String text, boolean isAdmin, AsyncCallback callback);
}
