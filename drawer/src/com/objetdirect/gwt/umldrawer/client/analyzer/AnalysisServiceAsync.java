package com.objetdirect.gwt.umldrawer.client.analyzer;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AnalysisServiceAsync{
	void analyze(String student, int exercizeId, AsyncCallback callback);

}
