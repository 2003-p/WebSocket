package com.objetdirect.gwt.umldrawer.client.beans;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Exercise implements IsSerializable{
	private int exerciseId;
	private String type;
	private String title;
	private String task;
	private Date entryDatetime;
	private int isRemoved;

	public Exercise() {
		super();
	}

	public Exercise(String title, String task) {
		this(-1, null, title, task, null, 0);
	}

	public Exercise(int exerciseId, String type, String title, String task,
			Date entryDatetime, int isRemoved) {
		super();
		this.exerciseId = exerciseId;
		this.type = type;
		this.title = title;
		this.task = task;
		this.entryDatetime = entryDatetime;
		this.isRemoved = isRemoved;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getEntryDatetime() {
		return entryDatetime;
	}

	public void setEntryDatetime(Date entryDatetime) {
		this.entryDatetime = entryDatetime;
	}

	public int getIsRemoved() {
		return isRemoved;
	}

	public void setIsRemoved(int isRemoved) {
		this.isRemoved = isRemoved;
	}


}
