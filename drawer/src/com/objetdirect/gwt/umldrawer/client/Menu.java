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


public class Menu extends VerticalPanel {

	final Button				logViewer				= new Button(LOG_VIEWER.getMessage());
	final Button				analysisViewer       = new Button(ANALYSIS_VIEWER.getMessage());
	final Button				viewerForSdudent = new Button(VIEWER_FOR_STUDENT.getMessage());
	final Button				userAdd					= new Button(ADD_USER.getMessage());
	final Button				changePassword					= new Button(CHANGE_PASSWORD.getMessage());
	final Button				languageButton = new Button();
	private final LoadingScreen	loadingScreen;


	public Menu(final boolean isFromHistory) {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();
		Logger.getGlobal().info("Starting App");
		System.out.println("Starting@StartPanel");

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

		this.add(new Label("Menu"));
		final Button selectExerciseBtn = new Button(SELECT_EXERCISE.getMessage());
		this.add(selectExerciseBtn);
		selectExerciseBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
//		        
//		        DrawerBase drawerInstance = DrawerBase.getInstance();
//		        drawerInstance.timeCountStop(); 
				History.newItem("SelectExercise");
			}
		});

			this.add(this.viewerForSdudent);
			viewerForSdudent.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					History.newItem("SelectExerciseForViewerForStudent");
					
					
				}
			});

			changePassword.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					History.newItem("ChangePassword");
				}
			});
		this.add(this.changePassword);


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
