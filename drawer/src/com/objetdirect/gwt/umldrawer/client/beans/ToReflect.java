package com.objetdirect.gwt.umldrawer.client.beans;

public class ToReflect {
	private int isChecked;
	private int exerciseId;
	private int editEventId;
	private String studentId;

	public ToReflect(int isChecked, int exerciseId, int editEventId,
			String studentId) {
		super();
		this.isChecked = isChecked;
		this.exerciseId = exerciseId;
		this.editEventId = editEventId;
		this.studentId = studentId;
	}

	public ToReflect(int exerciseId, int editEventId,
			String studentId) {
		super();
		this.isChecked = -1;
		this.exerciseId = exerciseId;
		this.editEventId = editEventId;
		this.studentId = studentId;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getEditEventId() {
		return editEventId;
	}

	public void setEditEventId(int editEventId) {
		this.editEventId = editEventId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


}
