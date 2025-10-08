package com.objetdirect.gwt.umldrawer.client.analyzer;

import com.google.gwt.user.client.rpc.RemoteService;

public interface AnalysisService extends RemoteService{
	boolean analyze(String studentId, int exerciseId);
}
