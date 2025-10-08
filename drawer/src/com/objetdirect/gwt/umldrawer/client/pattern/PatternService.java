package com.objetdirect.gwt.umldrawer.client.pattern;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface PatternService extends RemoteService {
	@SuppressWarnings("serial")
	public List<String> getPatternNameList();
	@SuppressWarnings("serial")
	public List<String> getPatternSpotList(int listIndex);
	@SuppressWarnings("serial")
	public List<String> getUMLClassDiagramList(String studentId, int exercisesId);
	@SuppressWarnings("serial")
	public String getCombineUrl(String studentId, int exercisesId, int index, String selectClass, String spotName);
	@SuppressWarnings("serial")
	public String getCombineUrl2(String studentId, int exercisesId, int index, Map<String, String> combineMap);
	@SuppressWarnings("serial")
	public String getPatternList(String studentId, int exercisesId, int listIndex);
	@SuppressWarnings("serial")
	public String savePatternList(String studentId, int exercisesId, String patternName, int patternId, List<String> spotList);
}
