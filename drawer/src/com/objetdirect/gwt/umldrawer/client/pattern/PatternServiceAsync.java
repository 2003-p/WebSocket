package com.objetdirect.gwt.umldrawer.client.pattern;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PatternServiceAsync {
	void getPatternNameList(AsyncCallback callback);
	void getPatternSpotList(int listIndex, AsyncCallback callback);
	void getUMLClassDiagramList(String studentId, int exercisesId, AsyncCallback callback);
	void getCombineUrl(String studentId, int exercisesId, int index, String selectClass, String spotName, AsyncCallback callback);
	void getCombineUrl2(String studentId, int exercisesId, int index, Map<String, String> combineMap, AsyncCallback callback);
	void getPatternList(String studentId, int exercisesId, int listIndex, AsyncCallback callback);
	void savePatternList(String studentId, int exercisesId, String patternName, int patternId, List<String> spotList, AsyncCallback callback);
}
