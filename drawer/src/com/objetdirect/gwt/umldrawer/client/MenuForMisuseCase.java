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
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class MenuForMisuseCase extends VerticalPanel {

	final Button 				addExercise			= new Button("Add Exercise");
	final Button				logViewer				= new Button(LOG_VIEWER.getMessage());
	final Button				analysisViewer       = new Button(ANALYSIS_VIEWER.getMessage());
	final Button				viewerForSdudent = new Button(VIEWER_FOR_STUDENT.getMessage());
	final Button				userAdd					= new Button(ADD_USER.getMessage());
	final Button				changePassword					= new Button(CHANGE_PASSWORD.getMessage());
	final Button				load						= new Button("Load diagram");

	private final LoadingScreen	loadingScreen;

	/**
	 * Constructor of the {@link MenuForMisuseCase}
	 *
	 * @param isFromHistory
	 */
	public MenuForMisuseCase(final boolean isFromHistory) {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();
		Logger.getGlobal().info("Starting App");
		System.out.println("Starting@MenuForMisuseCasePanel");

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

		this.add(new Label("Menu for Misusecase"));
		//this.add(this.logoImg);
		final Button selectExerciseBtn = new Button("Select Exercise");
		this.add(selectExerciseBtn);
		selectExerciseBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				History.newItem("SelectExercise");
			}
		});

//TODO

			this.add(this.viewerForSdudent);
			viewerForSdudent.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					History.newItem("ViewerForStudent");
				}
			});

			changePassword.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					History.newItem("ChangePassword");
				}
			});
		this.add(this.changePassword);


		//管理者用
		if(DrawerSession.student.getType()==1){
			//TODO
			this.add(this.addExercise);
			addExercise.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					setOptions("AddExercise", Session.diagramType);
				}
			});
			this.add(this.logViewer);
			logViewer.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					setOptions("Viewer", Session.diagramType);
				}
			});
			this.add(this.analysisViewer);
			analysisViewer.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(final ClickEvent event) {
					setOptions("Analysis", Session.diagramType);
				}
			});

		}

		this.loadingScreen.hide();
	}


	private void setOptions(final String newHistoryToken, final Type type) {
		if( newHistoryToken.equals("analysis")){
			History.newItem(newHistoryToken + "?" + OptionsManager.toURL());
		}
		else {
			if (OptionsManager.get("Advanced") == 1) {

				int w = 800;
				int h = 600;

				OptionsManager.set("Width", w);
				OptionsManager.set("Height", h);
			}
			History.newItem(newHistoryToken + "?" + OptionsManager.toURL());
		}
	}
}
