package com.objetdirect.gwt.umldrawer.client.beans;

import java.util.Date;

public class ExerciseIsRemoved {
	private int exerciseId;
	private boolean isRemoved;
	private Date changedDatetime;

	public ExerciseIsRemoved() {
		super();
	}

	public ExerciseIsRemoved(int exerciseId, boolean isRemoved,
			Date changedDatetime) {
		super();
		this.exerciseId = exerciseId;
		this.isRemoved = isRemoved;
		this.changedDatetime = changedDatetime;
	}

	public ExerciseIsRemoved(int exerciseId, boolean isRemoved) {
		super();
		this.exerciseId = exerciseId;
		this.isRemoved = isRemoved;
	}


	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	public boolean isRemoved() {
		return isRemoved;
	}
	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}
	public Date getChangedDatetime() {
		return changedDatetime;
	}
	public void setChangedDatetime(Date changedDatetime) {
		this.changedDatetime = changedDatetime;
	}


}
