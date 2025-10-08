package com.objetdirect.gwt.umldrawer.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.SessionService;

public class SessionServiceImpl extends RemoteServiceServlet implements SessionService{

	public SessionServiceImpl(){

	}

	@Override
	public String getSessionAttribute(String attributeId) {
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		if (session == null){
			return "SESSIONNULL";
		}
		return (String)session.getAttribute(attributeId);
	}

	@Override
	public void setSessionAttribute(String attributeId, String value) {
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		session.setAttribute(attributeId, value);
	}
}
