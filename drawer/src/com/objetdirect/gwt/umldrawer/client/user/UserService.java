package com.objetdirect.gwt.umldrawer.client.user;

import com.google.gwt.user.client.rpc.RemoteService;

public interface UserService extends RemoteService{
	public Boolean addUser(String userId);
	//public void changePassword(String studentId, String password);
	public void changePassword(String password);
	public void changePassword(String nowPassword, String newPassword);
	public void initializePassword(String studentId);
	public void setAdmin(String text, boolean isAdimn);
}
