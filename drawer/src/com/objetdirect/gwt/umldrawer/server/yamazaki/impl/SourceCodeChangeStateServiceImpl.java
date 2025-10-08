package com.objetdirect.gwt.umldrawer.server.yamazaki.impl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.server.yamazaki.thread.ThreadAcceptfromUMLDS;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.SourceCodeChangeStateService;




public class SourceCodeChangeStateServiceImpl extends RemoteServiceServlet  implements SourceCodeChangeStateService{

	final private ThreadAcceptfromUMLDS threadUMLDS_ = ThreadAcceptfromUMLDS.getInstance();

	@Override
	public boolean getSourceCodeChangeState() {
		// TODO 自動生成されたメソッド・スタブ
//		System.out.println("変更が加わったかのboolean in SourceCodeChangeStateServiceImpl" + threadUMLDS_.isState());
		return threadUMLDS_.isState();
	}

	@Override
	public void confirmedDiff() {
		// TODO 自動生成されたメソッド・スタブ
		threadUMLDS_.changedCodeState_False();
	}
}
