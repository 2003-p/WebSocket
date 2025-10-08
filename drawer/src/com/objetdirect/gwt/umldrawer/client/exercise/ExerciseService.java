package com.objetdirect.gwt.umldrawer.client.exercise;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.Exercise;

public interface ExerciseService extends RemoteService{
	public boolean addExercise( String type, String title, String task );
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.Exercise[]>
	public List<Exercise> getExerciseList( String type);
	public Exercise getExercise(int exerciseId);
	public void setExerciseRemoved(int exerciseId, Boolean isRemoved);

}
