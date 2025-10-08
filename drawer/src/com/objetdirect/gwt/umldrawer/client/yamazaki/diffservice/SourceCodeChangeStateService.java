package com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SourceCodeChangeStateService extends RemoteService {
	public boolean getSourceCodeChangeState();
	public void confirmedDiff();
}
