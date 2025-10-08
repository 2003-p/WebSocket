package com.objetdirect.gwt.umldrawer.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.canvas.CanvasService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class CanvasServiceImpl extends RemoteServiceServlet implements CanvasService {

	@Override
	public void saveCanvas(String studentId, int exerciseId, String canvasUrl) {
		Dao dao = new Dao();

		dao.saveCanvas( studentId, exerciseId, canvasUrl);

	}

	@Override
	public EditEvent loadCanvas(String studentId, int exercisesId) {
		EditEvent lastEvent;
		Dao dao = new Dao();

		lastEvent = dao.loadCanvas( studentId, exercisesId);

		return lastEvent;
	}

	@Override
	public EditEvent undo(String studentId, int exercisesId) {
		EditEvent backEvent;
		Dao dao = new Dao();

		backEvent = dao.undo(studentId, exercisesId);

		return backEvent;
	}

	@Override
	public boolean saveCanvasAsAnswer(String studentId, int exerciseId, String canvasUrl) {
		Dao dao = new Dao();

		return dao.saveCanvasAsAnswer( studentId, exerciseId, canvasUrl);

	}

	@Override
	public String getAnswer(int exerciseId) {
		Dao dao = new Dao();
		String canvasUrl = dao.getAnswer(exerciseId);

		return canvasUrl;
	}

}
