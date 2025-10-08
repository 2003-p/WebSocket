package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
	@SuppressWarnings("rawtypes")
	public void login(String studentId, String password, AsyncCallback callback);
}
