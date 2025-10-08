package com.objetdirect.gwt.umldrawer.server;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class PatternServiceImpl extends RemoteServiceServlet implements PatternService {
	public List<String> getPatternNameList() {
		Dao dao = new Dao();
		return dao.getPatternNameList();
	}
	public List<String> getPatternSpotList(int listIndex) {
		Dao dao = new Dao();
		return dao.getPatternSpotList(listIndex);
	}
    public List<String> getUMLClassDiagramList(String studentId, int exercisesId) {
    	Dao dao = new Dao();
    	return dao.getUMLClassDiagramList(studentId, exercisesId);
    }
    
    public String getCombineUrl(String studentId, int exercisesId, int index, String selectClass, String spotName) {
    	Dao dao = new Dao();
    	return dao.getCombineUrl(studentId, exercisesId, index, selectClass, spotName);
    }
    
    
    public String getCombineUrl2(String studentId, int exercisesId, int index, Map<String, String> combineMap) {
    	Dao dao = new Dao();
    	return dao.getCombineUrl2(studentId, exercisesId, index, combineMap);
    }
    
    public String getPatternList(String studentId, int exercisesId, int listIndex) {
    	Dao dao = new Dao();
    	return dao.getPatternList(studentId, exercisesId, listIndex);
    }
    
    public String savePatternList(String studentId, int exercisesId, String patternName, int patternId, List<String> spotList) {
    	Dao dao = new Dao();
    	return dao.savePatternList(studentId, exercisesId, patternName, patternId, spotList);
    }
    

}
