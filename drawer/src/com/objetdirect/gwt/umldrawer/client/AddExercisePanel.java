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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.exercise.ExerciseService;
import com.objetdirect.gwt.umldrawer.client.exercise.ExerciseServiceAsync;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class AddExercisePanel extends VerticalPanel {


	private final LoadingScreen loadingScreen;
	final Label  pageTitle = new Label("Check Item Manager");
	final Label  title = new Label("Title");
	final Label  titleNote = new Label("演習タイトルを入力してください（必須）");
	final TextBox titleBox= new TextBox();

	final Label  task = new Label("Task");
	final Label  taskNote = new Label("演習課題の問題記述を入力してください（必須）");
	final TextArea taskArea= new TextArea();

	final Button addButton = new Button("登録");
	/**
	 * Constructor of the {@link AddExercisePanel}
	 *
	 * @param isFromHistory
	 */
	public AddExercisePanel() {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

//TODO
		this.setSpacing(5);
		this.add(this.pageTitle);
		this.add(this.titleNote);
		titleBox.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				titleBox.setCursorPos(titleBox.getText().length());
			}
		});
		this.add(this.titleBox);
		this.add(this.task);
		this.add(this.taskNote);
		this.add(this.taskArea);
		taskArea.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				taskArea.setCursorPos(taskArea.getText().length());
			}
		});
		this.add(this.addButton);
		this.taskArea.setSize("500px", "500px");

		addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				if(titleBox.getText().isEmpty() || taskArea.getText().isEmpty()){
					Window.alert("必須項目を入力してください！");
				}
				else{

					ExerciseServiceAsync async = (ExerciseServiceAsync)GWT.create(ExerciseService.class);
					ServiceDefTarget entryPoint = (ServiceDefTarget) async;
					String entryURL = GWT.getModuleBaseURL() + "addExercise";
					entryPoint.setServiceEntryPoint(entryURL);

					@SuppressWarnings("rawtypes")
					AsyncCallback callback = new AsyncCallback(){
						public void onSuccess(Object result){
							Window.alert("Successed to add new exercise!");
							AddExercisePanel.this.titleBox.setText("");
							AddExercisePanel.this.taskArea.setText("");
						}
						public void onFailure(Throwable caught){
							Window.alert("Failured!");
						}
					};
					async.addExercise( Session.diagramType.getName(), titleBox.getText(), taskArea.getText(), callback);//type: "class" or "misnusecase"
				}
			}
		});


		this.loadingScreen.hide();
	}


}
