package com.objetdirect.gwt.umldrawer.client.canvas;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CanvasServiceAsync {
	@SuppressWarnings("rawtypes")
	public void saveCanvas(String studentId, int exercisesId, String canvasUrl, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	public void loadCanvas(String studentId, int exercisesId, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	public void undo(String studentId, int exercisesId, AsyncCallback callback);
	@SuppressWarnings("rawtypes")
	public void saveCanvasAsAnswer(String studentId, int exercisesId, String canvasUrl, AsyncCallback callback);
	//@gwt.typeArgs <com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact[]>
	public void getAnswer(int exerciseId, AsyncCallback callback);

}
