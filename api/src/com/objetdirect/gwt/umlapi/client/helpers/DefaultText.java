package com.objetdirect.gwt.umlapi.client.helpers;

public enum DefaultText {
	RELATION_CARDINALITY("None"),
	RELATION_NAME("Name"),
	;

	private String text;
	private DefaultText(String text) {
		this.text = text;
	}

	public String getMessage() {
			return text;
	}
}