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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.user.UserService;
import com.objetdirect.gwt.umldrawer.client.user.UserServiceAsync;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class ChangePasswordPanel extends VerticalPanel {


	private final LoadingScreen loadingScreen;
	final Label  nowPassLabel = new Label("現在のパスワード");
	final Label  newPassLabel1 = new Label("新しいパスワード");
	final Label  newPassLabel2 = new Label("新しいパスワード(確認)");
	final PasswordTextBox nowPassBox= new PasswordTextBox();
	final PasswordTextBox newPassBox1= new PasswordTextBox();
	final PasswordTextBox newPassBox2= new PasswordTextBox();
	final Button changePass = new Button("変更");

	/**
	 * Constructor of the {@link ChangePasswordPanel}
	 *
	 * @param isFromHistory
	 */
	public ChangePasswordPanel() {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

//TODO
		this.add(this.nowPassLabel);
		this.add(this.nowPassBox);
		this.add(this.newPassLabel1);
		this.add(this.newPassBox1);
		this.add(this.newPassLabel2);
		this.add(this.newPassBox2);

		changePass.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				System.out.println("PassChangeButtonPusshed");
				if(newPassBox1.getText().equals( newPassBox2.getText() )){
					if(newPassBox1.getText().equals("")){
						Window.alert("パスワードを入力してください！");
					}
					else{
						UserServiceAsync async = (UserServiceAsync)GWT.create(UserService.class);
						ServiceDefTarget entryPoint = (ServiceDefTarget) async;
						String entryURL = GWT.getModuleBaseURL() + "changePassword";
						entryPoint.setServiceEntryPoint(entryURL);

						@SuppressWarnings("rawtypes")
						AsyncCallback callback = new AsyncCallback(){
							public void onSuccess(Object result){
								ChangePasswordPanel.this.nowPassBox.setText("");
								ChangePasswordPanel.this.newPassBox1.setText("");
								ChangePasswordPanel.this.newPassBox2.setText("");
								Window.alert("パスワードが変更されました。次回ログイン時から有効になります。");
							}
							public void onFailure(Throwable caught){

							}
						};
						async.changePassword(ChangePasswordPanel.this.nowPassBox.getText(), ChangePasswordPanel.this.newPassBox1.getText(), callback);
					}
				}
				else
					System.out.println("else");
			}
		});
		this.add(changePass);
		this.loadingScreen.hide();
	}


}
