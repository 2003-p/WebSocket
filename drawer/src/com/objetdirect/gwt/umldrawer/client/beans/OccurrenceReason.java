package com.objetdirect.gwt.umldrawer.client.beans;

import com.google.gwt.user.client.rpc.IsSerializable;


public class OccurrenceReason implements IsSerializable{
	int reasonId;
	String reason;
	String other;

	public OccurrenceReason(){
	}

	public OccurrenceReason(int reasonId, String reason, String other) {
		super();
		this.reasonId = reasonId;
		this.reason = reason;
		this.other = other;
	}

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}
