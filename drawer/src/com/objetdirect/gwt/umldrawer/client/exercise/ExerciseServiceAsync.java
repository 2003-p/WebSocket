package com.objetdirect.gwt.umldrawer.client.exercise;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.gwt.umldrawer.client.beans.Exercise;

public interface ExerciseServiceAsync{
	void addExercise(String type, String title, String task, AsyncCallback<Boolean> callback);
	void getExerciseList( String type, AsyncCallback<List<Exercise>> callback);
	void getExercise( int exerciseId, AsyncCallback<Exercise> callback);
	void setExerciseRemoved( int exerciseId, Boolean isRemoved, AsyncCallback<Void> callback);
}
