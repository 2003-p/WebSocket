package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SourceCodeChangeStateServiceAsync {

	void getSourceCodeChangeState(AsyncCallback<Boolean> callback);
	void confirmedDiff(AsyncCallback<Void> callback);
}
