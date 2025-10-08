package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.Map;

public interface IDifference {
	public void show();
	public Map<String,String> getDiffSurplusMap();
	public Map<String,String> getDiffNotHasMap();
}
