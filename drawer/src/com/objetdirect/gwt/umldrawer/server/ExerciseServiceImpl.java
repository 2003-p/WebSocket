package com.objetdirect.gwt.umldrawer.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.Exercise;
import com.objetdirect.gwt.umldrawer.client.exercise.ExerciseService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class ExerciseServiceImpl extends RemoteServiceServlet implements ExerciseService{

	@Override
	public boolean addExercise(String type, String title, String task) {
		Dao dao = new Dao();
		return dao.addExercise(type, title, task);
	}

	@Override
	public List<Exercise> getExerciseList(String type) {
		Dao dao = new Dao();
		return dao.getExerciseList(type);
	}

	@Override
	public Exercise getExercise(int exerciseId) {
		Dao dao = new Dao();
		return dao.getExercise(exerciseId);
	}

	@Override
	public void setExerciseRemoved(int exerciseId, Boolean isRemoved) {
		Dao dao = new Dao();
		System.out.println("server");
		dao.setExerciseRemoved(exerciseId, isRemoved);
		return;
	}

}
