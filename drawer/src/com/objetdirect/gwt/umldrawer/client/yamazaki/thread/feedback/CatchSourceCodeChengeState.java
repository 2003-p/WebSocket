package com.objetdirect.gwt.umldrawer.client.yamazaki.thread.feedback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.SourceCodeChangeStateService;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.SourceCodeChangeStateServiceAsync;

public class CatchSourceCodeChengeState {

	private IDrawerBaseConectThread drawerbase_;
	private final SourceCodeChangeStateServiceAsync asyncSourceCodeState = GWT.create(SourceCodeChangeStateService.class);


	public CatchSourceCodeChengeState(IDrawerBaseConectThread  drawerbase)
	{
		this.drawerbase_ = drawerbase;
		ServiceDefTarget entryPoint = (ServiceDefTarget) asyncSourceCodeState;
		String entryURL = GWT.getModuleBaseURL() + "changecode";
		entryPoint.setServiceEntryPoint(entryURL);
	}

	public void isChangeState() {
		// TODO 自動生成されたメソッド・スタブ
		final SourceCodeChangeStateServiceAsync asyncSourceCodeState = GWT.create(SourceCodeChangeStateService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) asyncSourceCodeState;
		String entryURL = GWT.getModuleBaseURL() + "changecode";
		entryPoint.setServiceEntryPoint(entryURL);
		asyncSourceCodeState.getSourceCodeChangeState(new AsyncCallback<Boolean>()
		{
			@Override
			public void onFailure(Throwable failure) {
				// TODO 自動生成されたメソッド・スタブ
				Window.alert("通知用のRPC getSourceCodeChangeStateでonFailure");
			}

			@Override
			public void onSuccess(Boolean isChangeState) {
				// TODO 自動生成されたメソッド・スタブ
				if(isChangeState)
					drawerbase_.setDiffButtonDisplayedSentence("変更が加わったよ");
			}
		});
	}

	public void changedCodeStatetoTrue()
	{
		final SourceCodeChangeStateServiceAsync asyncSourceCodeState = GWT.create(SourceCodeChangeStateService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) asyncSourceCodeState;
		String entryURL = GWT.getModuleBaseURL() + "changecode";
		entryPoint.setServiceEntryPoint(entryURL);
		asyncSourceCodeState.confirmedDiff(new AsyncCallback<Void>()
		{
			@Override
			public void onFailure(Throwable failure) {
				// TODO 自動生成されたメソッド・スタブ
				Window.alert("通知用のRPC changedCodeState_TrueでonFailure");
			}

			@Override
			public void onSuccess(Void arg0) {
				// TODO 自動生成されたメソッド・スタブ
				// 一方的にTrueにしたいだけなので戻り値は何もなし
			}
		});
	}
}
