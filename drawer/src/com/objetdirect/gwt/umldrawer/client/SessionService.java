package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SessionService extends RemoteService{
	public String getSessionAttribute(String attributeId);
	public void setSessionAttribute(String attributeId, String value);
}
