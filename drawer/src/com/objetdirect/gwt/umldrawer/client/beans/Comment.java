package com.objetdirect.gwt.umldrawer.client.beans;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Comment implements IsSerializable{
	int commentId;
	String studentId;
	int editEventId;
	int exerciseId;
	String comment;
	Date entryDatetime;

	public Comment() {

	}

	public Comment(String studentId, int editEventId,
			int exerciseId, String comment, Date entryDatetime) {
		super();
		this.studentId = studentId;
		this.editEventId = editEventId;
		this.exerciseId = exerciseId;
		this.comment = comment;
		this.entryDatetime = entryDatetime;
	}

	public Comment(int commentId, String studentId, int editEventId,
			int exerciseId, String comment, Date entryDatetime) {
		super();
		this.commentId = commentId;
		this.studentId = studentId;
		this.editEventId = editEventId;
		this.exerciseId = exerciseId;
		this.comment = comment;
		this.entryDatetime = entryDatetime;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getEditEventId() {
		return editEventId;
	}

	public void setEditEventId(int editEventId) {
		this.editEventId = editEventId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getEntryDatetime() {
		return entryDatetime;
	}

	public void setEntryDatetime(Date entryDatetime) {
		this.entryDatetime = entryDatetime;
	}

}
