/**
 *
 */
package com.objetdirect.gwt.umldrawer.client;

/**
 * @author J10-8011
/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 *
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 *
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.user.UserService;
import com.objetdirect.gwt.umldrawer.client.user.UserServiceAsync;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class AddUserPanel extends VerticalPanel {


	private final LoadingScreen loadingScreen;
	final Label  idLabel = new Label("ID");
	final Label  Label = new Label("初期パスワードはIDと同じになります");
	final TextBox idBox= new TextBox();

	final Button add = new Button("登録");
	/**
	 * Constructor of the {@link AddUserPanel}
	 *
	 * @param isFromHistory
	 */
	public AddUserPanel() {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

//TODO
		this.add(this.idLabel);
		this.add(this.idBox);
		this.add(this.Label);
		this.add(this.add);

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				if(idBox.getText().equals("")){
					Window.alert("ユーザIDを入力してください！");
				}
				else{

					UserServiceAsync async = (UserServiceAsync)GWT.create(UserService.class);
					ServiceDefTarget entryPoint = (ServiceDefTarget) async;
					String entryURL = GWT.getModuleBaseURL() + "addUser";
					entryPoint.setServiceEntryPoint(entryURL);

					@SuppressWarnings("rawtypes")
					AsyncCallback callback = new AsyncCallback(){
						public void onSuccess(Object result){
							if(((Boolean)result).booleanValue()){
								Window.alert("New User was added!");
								History.newItem("Login");
							}else {
								Window.alert("The ID is already used!");
							}
							AddUserPanel.this.idBox.setText("");
						}
						public void onFailure(Throwable caught){
							Window.alert("User adding was Failured!");
						}
					};
					async.addUser(AddUserPanel.this.idBox.getText(), callback);
				}
			}
		});


		this.loadingScreen.hide();
	}


}
