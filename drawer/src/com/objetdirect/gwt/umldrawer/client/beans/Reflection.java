package com.objetdirect.gwt.umldrawer.client.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Reflection implements IsSerializable{
	private int reflectionId;
	private String studentId;
	private int exerciseId;
	private EditEvent occurrencePoint;
	private List<OccurrenceReason> reasonList = new ArrayList<OccurrenceReason>();
	private String otherReason;
	private List<CheckItem> checkedItemList = new ArrayList<CheckItem>();
	private List<Integer> targetPartIdList = new ArrayList<Integer>();
	private String imagedSituation;
	private boolean isThereProblem;
	private String discoveredProblems;
	private List<EditEvent> modificationEventList = new ArrayList<EditEvent>();
	private Timestamp entryDatetime;

	public Reflection(){
	}

	public Reflection(int reflectionId, String studentId, int exerciseId,
			EditEvent occurrencePoint, List<OccurrenceReason> reasonList, String otherReason,
			List<CheckItem> checkedItemList, List<Integer> targetPartIdList,
			String imagedSituation, boolean isThereProblem, String discoveredProblems,
			List<EditEvent> modificationEventList, Timestamp entryDatetime) {
		super();
		this.reflectionId = reflectionId;
		this.studentId = studentId;
		this.setExerciseId(exerciseId);
		this.occurrencePoint = occurrencePoint;
		this.reasonList = reasonList;
		this.setOtherReason(otherReason);
		this.setCheckedItemList(checkedItemList);
		this.setTargetPartIdList(targetPartIdList);
		this.imagedSituation = imagedSituation;
		this.isThereProblem = isThereProblem;
		this.discoveredProblems = discoveredProblems;
		this.setModificationEventList(modificationEventList);
		this.entryDatetime = entryDatetime;
	}


	public Reflection(int reflectionId, String studentId, EditEvent occurrencePoint,
			List<Integer> targetPartIdList, String imagedSituation, String discoveredProblems) {
		super();
		this.reflectionId = reflectionId;
		this.studentId = studentId;
		this.occurrencePoint = occurrencePoint;
		this.setTargetPartIdList(targetPartIdList);
		this.imagedSituation = imagedSituation;
		this.discoveredProblems = discoveredProblems;
	}

	public Reflection(String studentId, EditEvent occurrencePoint,
			List<Integer> targetPartIdList, String imagedSituation, String discoveredProblems) {
		super();
		this.reflectionId = -1;
		this.studentId = studentId;
		this.occurrencePoint = occurrencePoint;
		this.setTargetPartIdList(targetPartIdList);
		this.imagedSituation = imagedSituation;
		this.discoveredProblems = discoveredProblems;
	}

	public int getSelfCheckId() {
		return reflectionId;
	}

	public void setSelfCheckId(int selfCheckId) {
		this.reflectionId = selfCheckId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public EditEvent getOccurrencePoint() {
		return occurrencePoint;
	}

	public void setOccurrencePoint(EditEvent occurrencePoint) {
		this.occurrencePoint = occurrencePoint;
	}

	public String getImagedSituation() {
		return imagedSituation;
	}

	public void setImagedSituation(String imagedSituation) {
		this.imagedSituation = imagedSituation;
	}

	public String getDiscoveredProblems() {
		return discoveredProblems;
	}

	public void setDiscoveredProblems(String discoveredProblems) {
		this.discoveredProblems = discoveredProblems;
	}

	public List<CheckItem> getCheckedItemList() {
		return checkedItemList;
	}

	public void setCheckedItemList(List<CheckItem> checkedItemList) {
		if(checkedItemList == null) this.checkedItemList = new ArrayList<CheckItem>();
		else this.checkedItemList = checkedItemList;
	}



	public List<EditEvent> getModificationEventList() {
		return modificationEventList;
	}



	public void setModificationEventList(List<EditEvent> modificationEventList) {
		if(modificationEventList == null) this.modificationEventList = new ArrayList<EditEvent>();
		else this.modificationEventList = modificationEventList;
	}

	public Timestamp getEntryDatetime() {
		return entryDatetime;
	}

	public void setEntryDatetime(Timestamp entryDatetime) {
		this.entryDatetime = entryDatetime;
	}

	public int getReflectionId() {
		return reflectionId;
	}

	public void setReflectionId(int reflectionId) {
		this.reflectionId = reflectionId;
	}

	public List<OccurrenceReason> getReasonList() {
		return reasonList;
	}

	public void setReasonList(List<OccurrenceReason> reasonList) {
		if(reasonList == null) this.reasonList = new ArrayList<OccurrenceReason>();
		else this.reasonList = reasonList;
	}

	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

	public boolean isThereProblem() {
		return isThereProblem;
	}

	public void setIsThereProblem(boolean isThereProblem) {
		this.isThereProblem = isThereProblem;
	}

	public List<Integer> getTargetPartIdList() {
		return targetPartIdList;
	}

	public void setTargetPartIdList(List<Integer> targetPartIdList) {
		if(targetPartIdList == null) this.targetPartIdList = new ArrayList<Integer>();
		else this.targetPartIdList = targetPartIdList;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}




}
