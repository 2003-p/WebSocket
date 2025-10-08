package com.objetdirect.gwt.umldrawer.client.saito;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SimService extends RemoteService{
	@SuppressWarnings("serial")
	public Map<String,Map<String,String>> urlToCompare(String studentId, int exerciseId);
	
	@SuppressWarnings("serial")
	public Map<String,Map<String,String>> removeRelation(String studentId, int exerciseId);
	
	@SuppressWarnings("serial")
	public Map<String,Map<String,String>> addColor(String studentId, int exerciseId);
	
	@SuppressWarnings("serial")
	public double getSim(String studentId, int exerciseId);
}
