package com.objetdirect.gwt.umldrawer.client.beans;

public class TargetPart {
	private int partId;
	private String partType;
	private String other;

	public TargetPart(int partId, String partType, String other) {
		super();
		this.partId = partId;
		this.partType = partType;
		this.other = other;
	}

	public TargetPart(String partType, String other) {
		super();
		this.partId = -1;
		this.partType = partType;
		this.other = other;
	}

	public int getPartId() {
		return partId;
	}

	public void setPartId(int partId) {
		this.partId = partId;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}


}
