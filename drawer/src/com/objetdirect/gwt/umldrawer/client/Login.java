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
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Login extends VerticalPanel {


	private final LoadingScreen loadingScreen;
	final Label  titleLabel = new Label("KIfU 4.0");
	final Label  idLabel = new Label("ID");
	final Label  passwordLabel = new Label("Password");
	final TextBox idBox= new TextBox();
	final PasswordTextBox passwordBox= new PasswordTextBox();
	final Button userAdd = new Button("ユーザ登録");
	Label notification = new Label("asdasd");

	final Button login = new Button("Login");
	/**
	 * Constructor of the {@link Login}
	 *
	 * @param isFromHistory
	 */
	public Login(final boolean isFromHistory) {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();
		Logger.getGlobal().info("Starting App");
		System.out.println("Starting@StartPanel");

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

//TODO
		this.add(this.titleLabel);
		setFontSize(titleLabel, 20);
		this.add(this.idLabel);
		this.add(this.idBox);
		this.add(this.passwordLabel);
		this.add(this.passwordBox);
		this.add(this.login);
		this.add(this.userAdd);
		this.add(this.notification);


		login.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				//Login.this.setOptions("StartPanel");
				//login処理
				LoginServiceAsync async = (LoginServiceAsync)GWT.create(LoginService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "login";
				entryPoint.setServiceEntryPoint(entryURL);

				@SuppressWarnings("rawtypes")
				AsyncCallback callback = new AsyncCallback(){
					public void onSuccess(Object result){
						//System.out.println( (boolean) result);
						if( result != null ){
							Student student = (Student) result ;
							DrawerSession.student = student;
							Session.mode = "login";
							Session.studentId = student.getStudentId();
							Session.exerciseId = -1;
							MyLoggerExecute.registEditEvent(-1,"Login", "Login",
							null, -1, null, -1, -1,
							null, null, null, null, -1);
							Session.mode = "none";
//					int preEventId, String editEvent, String eventType,
//					String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//					String targetPart, String beforeEdit, String afterEdit, String canvasUrl
							OptionsManager.set("QualityLevel", 1);
							OptionsManager.set("DiagramType", UMLDiagram.Type.CLASS.getIndex());
							Session.exerciseId = 1;
							Session.diagramType = UMLDiagram.Type.CLASS;
							History.newItem("Menu",true);
						}else{
							Window.alert("ログインに失敗しました。");
						}
					}
					public void onFailure(Throwable caught){
						Window.alert("エラーによりログインに失敗しました。");
					}
				};
				async.login(Login.this.idBox.getText(), Login.this.passwordBox.getText(), callback);
				//History.newItem("Top", true);

			}
		});

		userAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("UserAdd", true);
			}
		});


		this.sinkEvents(Event.ONCONTEXTMENU);
	    this.addHandler(
	      new ContextMenuHandler() {
	        @Override
	        public void onContextMenu(ContextMenuEvent event) {
	          event.preventDefault();
	          event.stopPropagation();
	    }
	    }, ContextMenuEvent.getType());



		this.loadingScreen.hide();
	}
	private void setFontSize(Label label, double pt) {
		  label.getElement().getStyle().setFontSize(pt, Unit.PT);
		}

}
