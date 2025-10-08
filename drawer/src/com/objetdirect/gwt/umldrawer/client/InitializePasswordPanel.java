package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.user.UserService;
import com.objetdirect.gwt.umldrawer.client.user.UserServiceAsync;

public class InitializePasswordPanel extends PopupPanel{
	VerticalPanel base = new VerticalPanel();
	TextBox textBox = new TextBox();
	Button submitButton = new Button("パスワード初期化");

	public InitializePasswordPanel(){
		this.add(base);
		base.add(textBox);
		base.add(submitButton);
		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UserServiceAsync async = (UserServiceAsync)GWT.create(UserService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "initializePassword";
				entryPoint.setServiceEntryPoint(entryURL);
				AsyncCallback<Void> callback = new AsyncCallback<Void>(){
					public void onSuccess(Void result){
						Window.alert("Password of "+textBox.getText()+" was initialized.");
						textBox.setText("");
					}
					public void onFailure(Throwable caught){
						Window.alert( caught.toString());
					}
				};
				async.initializePassword( textBox.getText(), callback);
			}
		});
	}
}
