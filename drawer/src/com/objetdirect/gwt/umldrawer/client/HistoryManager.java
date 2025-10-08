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

import java.util.HashMap;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.analyzer.AnalyzerBase;
import com.objetdirect.gwt.umldrawer.client.analyzer.GraphTestPanel;
import com.objetdirect.gwt.umldrawer.client.example.MyApplication1;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.progress.ProgressViewPanel;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerBase;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerForStudentBase;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class HistoryManager implements ValueChangeHandler<String> {
	private static String					lastHistoryAnchor			= "";
	private static HashMap<String, String>	lastHistoryParametersList	= new HashMap<String, String>();
	private static AbsolutePanel				applicationPanel			= new AbsolutePanel();
	private static String					urlDiagram					= "";

	static void upgradeDiagramURL(final String url) {
		String historyToken = HistoryManager.lastHistoryAnchor + "?" + OptionsManager.toURL();
		if (!historyToken.endsWith("&")) {
			historyToken += "&";
		}
		historyToken += "diagram64=" + url;
		History.newItem(historyToken, false);
	}

	/**
	 * Initialize the history management and therefore the application
	 *
	 * @param appRootPanel
	 *            The panel on which we can put the pages
	 */
	public void initApplication(final DockPanel appRootPanel) {
		History.addValueChangeHandler(this);
		appRootPanel.add(HistoryManager.applicationPanel, DockPanel.CENTER);
		HistoryManager.applicationPanel.setSize("100%", "100%");
		this.parseHistoryToken(History.getToken());
		this.processHistory();
//				Window.addCloseHandler(new CloseHandler<Window>() {
//		
//					@Override
//					public void onClose(final CloseEvent<Window> event) {
//						if (HistoryManager.lastHistoryAnchor.equals("Drawer")) {
//							//HistoryManager.upgradeDiagramURL(Session.getActiveCanvas().toUrl());
//						}
//					}
//				});
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
	 */
	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		final String historyToken = event.getValue();
		System.out.println("onValueChange history:"+historyToken);
		this.parseHistoryToken(historyToken);
		this.processHistory();
	}

	private void parseHistoryToken(final String historyToken) {
		final String[] parts = historyToken.split("\\?");
		HistoryManager.lastHistoryAnchor = parts[0];
		HistoryManager.lastHistoryParametersList.clear();
		if (parts.length > 1) {
			final String[] params = parts[1].split("&");
			for (final String argument : params) {
				final String[] paramVar = argument.split("=", 2);
				if ((paramVar.length > 0) && (paramVar[0].length() > 0)) {
					if (!paramVar[0].equals("diagram64")) {
						HistoryManager.lastHistoryParametersList.put(paramVar[0], paramVar.length > 1 ? paramVar[1] : "");
					} else {
						HistoryManager.urlDiagram = paramVar.length > 1 ? paramVar[1] : "";
					}
				}
			}
		}
	}
	//TODO 画面
	private void processHistory() {
		HistoryManager.applicationPanel.clear();

		// DOM.setStyleAttribute(Log.getDivLogger().getWidget().getElement(), "display", "none");

		OptionsManager.setAllFromURL(HistoryManager.lastHistoryParametersList);
		UMLArtifact.getArtifactList().clear();

		HotKeyManager.setInputEnabled(false);

		if (HistoryManager.lastHistoryAnchor.equals("UserAdd")) {
			//TODO
			System.out.println("userAdding");
			final AddUserPanel addUserPanel= new AddUserPanel();
			Session.setMode("useradding");
			HistoryManager.applicationPanel.add(addUserPanel);
			System.out.println("userAdding");
		}else if(HistoryManager.lastHistoryAnchor.equals("Login")){
			HistoryManager.applicationPanel.add(new Login(false));
		}else if(DrawerSession.student == null){
			Session.setMode("");
			History.newItem("Login", true);
		}else if (HistoryManager.lastHistoryAnchor.equals("Drawer")) {
			//final DrawerPanel drawerPanel = new DrawerPanel();
			final DrawerBase drawerBase = new DrawerBase();
			HotKeyManager.setInputEnabled(true);
			Session.setMode("drawer");
			HistoryManager.applicationPanel.add(drawerBase);
		}else if (HistoryManager.lastHistoryAnchor.equals("Viewer")) {
			final ViewerBase viewer = new ViewerBase();
			Session.setMode("viewer");
			HistoryManager.applicationPanel.add(viewer);
		}else if (HistoryManager.lastHistoryAnchor.equals("Analysis")) {
			//TODO
			final AnalyzerBase analyzerBase = new AnalyzerBase();
			System.out.println("Initializing AnalyzerBase");
			Session.setMode("analysis");
			HistoryManager.applicationPanel.add(analyzerBase);
		} else if (HistoryManager.lastHistoryAnchor.equals("Menu")){
			HistoryManager.urlDiagram = "";
			if( DrawerSession.student.getType() == 1 ){
				System.out.println("StudentId:"+DrawerSession.student.getStudentId());
				HistoryManager.applicationPanel.add(new MenuForMaster(false));
			}
			else{
				HistoryManager.applicationPanel.add(new Menu(false));
			}
		} else if (HistoryManager.lastHistoryAnchor.equals("MenuForMisuseCase")){
			System.out.println("StudentId:"+DrawerSession.student.getStudentId());
			HistoryManager.applicationPanel.add(new MenuForMisuseCase(false));
		}else if (HistoryManager.lastHistoryAnchor.equals("ViewerForStudent")) {
			final ViewerForStudentBase viewer = new ViewerForStudentBase(DrawerSession.student.getStudentId(), Session.exerciseId);
			Session.setMode("viewer");
			HistoryManager.applicationPanel.add(viewer);
		}else if (HistoryManager.lastHistoryAnchor.equals("Top")) {
			final Top top = new Top();
			Session.setMode("");
			HistoryManager.applicationPanel.add(top);
		}else if (HistoryManager.lastHistoryAnchor.equals("AddExercise")) {
			System.out.println("AddExercise");
			final AddExercisePanel addExercisePanel= new AddExercisePanel();

			Session.setMode("useradding");
			HistoryManager.applicationPanel.add(addExercisePanel);
			System.out.println("AddExercise");
		}else if (HistoryManager.lastHistoryAnchor.equals("ChangePassword")) {
			System.out.println("ChangePassword");
			final ChangePasswordPanel changePasswordPanel= new ChangePasswordPanel();

			HistoryManager.applicationPanel.add(changePasswordPanel);
		}else if (HistoryManager.lastHistoryAnchor.equals("SelectExercise")) {
			System.out.println("SelectExercise");
			final SelectExercisePanel selectExercisePanel = new SelectExercisePanel(1);
			HistoryManager.applicationPanel.add(selectExercisePanel);

		}else if (HistoryManager.lastHistoryAnchor.equals("SelectExerciseForViewer")) {

			System.out.println("SelectExercise");
			final SelectExercisePanel selectExercisePanel = new SelectExercisePanel(2);
			HistoryManager.applicationPanel.add(selectExercisePanel);

		}else if (HistoryManager.lastHistoryAnchor.equals("SelectExerciseForViewerForStudent")) {
			//TODO
			System.out.println("SelectExercise");
			final SelectExercisePanel selectExercisePanel = new SelectExercisePanel(3);
			HistoryManager.applicationPanel.add(selectExercisePanel);

		}else if (HistoryManager.lastHistoryAnchor.equals("SelectExerciseForProgressView")) {
			//TODO
			System.out.println("SelectExercise");
			final SelectExercisePanel selectExercisePanel = new SelectExercisePanel(4);
			HistoryManager.applicationPanel.add(selectExercisePanel);

		}else if (HistoryManager.lastHistoryAnchor.equals("GraphTest")) {
			//TODO
			final GraphTestPanel graphTestPanel= new GraphTestPanel();

			HistoryManager.applicationPanel.add( graphTestPanel);
		}else if (HistoryManager.lastHistoryAnchor.equals("ProgressView")) {

			final ProgressViewPanel panel= new ProgressViewPanel();
			HistoryManager.applicationPanel.add( panel );
		}else if (HistoryManager.lastHistoryAnchor.equals("NewViewer")) {

			final NewViewer panel= new NewViewer();
			HistoryManager.applicationPanel.add( panel );
		}else if (HistoryManager.lastHistoryAnchor.equals("DropAndDrag")) {
			final MyApplication1 example = new MyApplication1();
			HistoryManager.applicationPanel.add( example );
		}else if (HistoryManager.lastHistoryAnchor.equals("CheckItemManager")) {
			final CheckItemManagerPanel panel = new CheckItemManagerPanel();
			HistoryManager.applicationPanel.add( panel );
		}
		else {
			//Login
			History.newItem("Login", true);
		}
	}
}