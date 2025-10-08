/**
 *
 */
package com.objetdirect.gwt.umldrawer.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;




public class LogViewerServiceImpl extends RemoteServiceServlet implements LogViewerService{

	public LogViewerServiceImpl(){
	}

	public List<String> getLog(String studentId, int exercisesId) {
		//System.out.println("start operationLog...");
		List<String> log = new ArrayList<String>();
		Dao dao = new Dao();

		log = dao.getEditEvent(studentId, exercisesId);

		if(!log.isEmpty()){
			System.out.println("drawer DB is OK  !(^^)!");
		}else {
			System.out.println("DB is NG");
		}
		return log;
	}

	@Override
	public List<EditEvent> getEditEventList(String studentId, int exercisesId) {
		// DAOからリストをget
		List<EditEvent> editEventList = new ArrayList<EditEvent>();
		Dao dao = new Dao();
		editEventList = dao.getEditEventList(studentId, exercisesId);
		System.out.println("Dao.getEditEventList");
		return editEventList;
	}

	@Override
	public List<EditEvent> getEditEventList() {
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession();
		String studentId = (String)session.getAttribute("studentId");
		int exercisesId = (Integer)session.getAttribute("exercisesId");
		Dao dao = new Dao();

		return dao.getEditEventList(studentId, exercisesId);
	}

	@Override
	public List<EditEvent> getAllEditEventList(String studentId, int exercisesId) {
		// DAOからリストをget
		List<EditEvent> editEventList = new ArrayList<EditEvent>();
		Dao dao = new Dao();
		editEventList = dao.getAllEditEventList(studentId, exercisesId);
		System.out.println("Dao.getEditEventList");
		return editEventList;
	}

	@Override
	public List<EditEvent> getAllEditEventList() {
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession();
		String studentId = (String)session.getAttribute("studentId");
		int exercisesId = (Integer)session.getAttribute("exercisesId");
		Dao dao = new Dao();

		return dao.getAllEditEventList(studentId, exercisesId);
	}

	@Override
	public List<EditEvent> getEditEventListForReplay(String studentId, int exercisesId) {
		// DAOからリストをget
		List<EditEvent> editEventList = new ArrayList<EditEvent>();
		Dao dao = new Dao();
		editEventList = dao.getEditEventListForReplay(studentId, exercisesId);
		System.out.println("Dao.getEditEventListForReplay");
		return editEventList;
	}

}

