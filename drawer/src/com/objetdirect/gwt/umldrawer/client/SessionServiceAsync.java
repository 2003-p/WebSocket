package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SessionServiceAsync {
	@SuppressWarnings("rawtypes")
	public void getSessionAttribute(String attributeId, AsyncCallback callback);
	public void setSessionAttribute(String attributeId, String value, AsyncCallback callback);
}
