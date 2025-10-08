package com.objetdirect.gwt.umldrawer.client.beans;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.IsSerializable;

public class EditEvent implements IsSerializable{
	int editEventId;
	String studentId;
	int exercisesId;
	int preEditEventId;
	String editEvent;
	String eventType;
	String targetType;
	int targetId;
	String linkKind;
	int rightObjectId;
	int leftObjectId;
	String targetPart;
	String beforeEdit;
	String afterEdit;
	int canvasId;
	String canvasUrl;
	int difficulty;
	Timestamp editDatetime;
	int umlArtifactId;


	public EditEvent(){

	}

	/**
	 * @return editEventId
	 */
	public int getEditEventId() {
		return editEventId;
	}

	/**
	 * @param editEventId セットする editEventId
	 */
	public void setEditEventId(int editEventId) {
		this.editEventId = editEventId;
	}

	/**
	 * @return studentId
	 */
	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId セットする studentId
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return exercisesId
	 */
	public int getExercisesId() {
		return exercisesId;
	}

	/**
	 * @param exercisesId セットする exercisesId
	 */
	public void setExercisesId(int exercisesId) {
		this.exercisesId = exercisesId;
	}

	/**
	 * @return editEvent
	 */
	public String getEditEvent() {
		return editEvent;
	}

	/**
	 * @param editEvent セットする editEvent
	 */
	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}


	/**
	 * @return canvasUrl
	 */
	public String getCanvasUrl() {
		return canvasUrl;
	}

	/**
	 * @param canvasUrl セットする canvasUrl
	 */
	public void setCanvasUrl(String canvasUrl) {
		this.canvasUrl = canvasUrl;
	}

	public int getPreEditEventId() {
		return preEditEventId;
	}

	public void setPreEditEventId(int preEditEventId) {
		this.preEditEventId = preEditEventId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getLinkKind() {
		return linkKind;
	}

	public void setLinkKind(String linkKind) {
		this.linkKind = linkKind;
	}

	public int getRightObjectId() {
		return rightObjectId;
	}

	public void setRightObjectId(int rightObjectId) {
		this.rightObjectId = rightObjectId;
	}

	public int getLeftObjectId() {
		return leftObjectId;
	}

	public void setLeftObjectId(int leftObjectId) {
		this.leftObjectId = leftObjectId;
	}

	public String getTargetPart() {
		return targetPart;
	}

	public void setTargetPart(String targetPart) {
		this.targetPart = targetPart;
	}

	public String getBeforeEdit() {
		return beforeEdit;
	}

	public void setBeforeEdit(String beforeEdit) {
		this.beforeEdit = beforeEdit;
	}

	public String getAfterEdit() {
		return afterEdit;
	}

	public void setAfterEdit(String afterEdit) {
		this.afterEdit = afterEdit;
	}

	public int getCanvasId() {
		return canvasId;
	}

	public void setCanvasId(int canvasId) {
		this.canvasId = canvasId;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public Timestamp getEditDatetime() {
		return editDatetime;
	}

	public void setEditDatetime(Timestamp editDatetime) {
		this.editDatetime = editDatetime;
	}

	/**
	 * @return umlArtifactId
	 */
	public int getUmlArtifactId() {
		return umlArtifactId;
	}

	/**
	 * @param umlArtifactId セットする umlArtifactId
	 */
	public void setUmlArtifactId(int umlArtifactId) {
		this.umlArtifactId = umlArtifactId;
	}



}
