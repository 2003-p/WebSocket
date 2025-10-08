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
package com.objetdirect.gwt.umldrawer.client;

import static com.objetdirect.gwt.umldrawer.client.DrawerTextResource.*;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class MenuForMaster extends VerticalPanel {

	final Button				addExercise			= new Button("Add Exercise");
	final Button				logViewer				= new Button("Log Viewer");
	final Button				analysisViewer       = new Button("Analysis Viewer");
	final Button				viewerForSdudent = new Button("Viewer For Student");
	final Button				progressView = new Button("Progress View");
	final Button				userAdd					= new Button("Add User");
	final Button				changePassword					= new Button("Change Password");
	final Button				addAdmin					= new Button("Add Admin");
	final Button				initializePassword					= new Button("Initialize Passoword");
	final Button				manageCheckItem					= new Button("Manage Check Items");
	final Button				languageButton = new Button();

	private final LoadingScreen	loadingScreen;

	/**
	 * Constructor of the {@link MenuForMaster}
	 *
	 * @param isFromHistory
	 */
	public MenuForMaster(final boolean isFromHistory) {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();
		Logger.getGlobal().info("Starting App");
		System.out.println("Starting@StartPanel");

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);
		this.add(new Label("Menu"));

		//this.add(this.logoImg);
		final Button selectExerciseBtn = new Button(SELECT_EXERCISE.getMessage());
		this.add(selectExerciseBtn);
		selectExerciseBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("SelectExercise");
			}
		});

		//TODO
		this.add(this.addExercise);
		addExercise.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("AddExercise");
			}
		});

		this.add(this.viewerForSdudent);
		viewerForSdudent.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("SelectExerciseForViewerForStudent");
			}
		});

//		this.add(this.progressView);
//		progressView.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(final ClickEvent event) {
//				History.newItem("SelectExerciseForProgressView");
//			}
//		});

		this.add(this.userAdd);
		userAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("UserAdd");
			}
		});

		this.add(this.changePassword);
		changePassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("ChangePassword");
			}
		});

		this.add(this.addAdmin);
		addAdmin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				AddAdminPanel addAdminPanel = new AddAdminPanel();
				addAdminPanel.setPopupPosition(0, 0);
				addAdminPanel.setAutoHideEnabled(true);
				addAdminPanel.show();
			}
		});

		this.add(this.initializePassword);
		initializePassword.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				InitializePasswordPanel initializePasswordPane = new InitializePasswordPanel();
				initializePasswordPane.setPopupPosition(0, 0);
				initializePasswordPane.setAutoHideEnabled(true);
				initializePasswordPane.show();
			}
		});

		this.add(this.manageCheckItem);
		manageCheckItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("CheckItemManager");
			}
		});

		String lgText;
		if(Session.language.equals("jp")){
			lgText = "English mode";
		}
		else{
			lgText = "日本語モード";
		}
		languageButton.setText(lgText);
		languageButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(Session.language.equals("jp")){
					Session.language = "en";
					DrawerSession.language = "en";
				}
				else{
					Session.language = "jp";
					DrawerSession.language = "jp";
				}
				History.newItem("");
			}
		});
		this.add(languageButton);

		this.loadingScreen.hide();
	}

}
