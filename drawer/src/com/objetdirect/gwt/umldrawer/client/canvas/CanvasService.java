package com.objetdirect.gwt.umldrawer.client.canvas;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;

public interface CanvasService extends RemoteService{

	public void saveCanvas( String studentId, int exercisesId, String canvasUrl);

	public EditEvent loadCanvas(String studentId, int exercisesId);

	public EditEvent undo(String studentId, int exercisesId);

	public boolean saveCanvasAsAnswer( String studentId, int exercisesId, String canvasUrl);

	public String getAnswer(int exerciseId);
	
}
