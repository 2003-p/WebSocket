/*
r * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
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

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;
import com.objetdirect.gwt.umlapi.client.helpers.HotKeyManager;
import com.objetdirect.gwt.umlapi.client.helpers.OptionsManager;

/**
 * Main class for gwtuml application. This class does some initialization and calls the start panel.
 *
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class GWTUMLDrawer implements EntryPoint {
	private final static DockPanel	appRootPanel	= new DockPanel();
	// private static Button log;

	/**
	 * Entry point of the application This class make a StartPanel and manage the history for it
	 */
	public void gwt_main() {
		System.out.print("gwt-main");
		// Log.setCurrentLogLevel(Log.LOG_LEVEL_WARN);
		OptionsManager.initialize();
		//appRootPanel.getElement().setAttribute("oncontextmenu", "preventDefault(); return false");
		HotKeyManager.forceStaticInit();
		final HistoryManager historyManager = new HistoryManager();
		historyManager.initApplication(GWTUMLDrawer.appRootPanel);

		DOM.setInnerHTML(RootPanel.get("loading-screen").getElement(), "");


		GWTUMLDrawer.appRootPanel.setSize("100%", "100%");
		// appRootPanel.add(log, DockPanel.SOUTH);//

		RootPanel.get().add(GWTUMLDrawer.appRootPanel);
		// Log.getDivLogger().moveTo(log.getAbsoluteLeft(),//
		// log.getAbsoluteTop() + log.getOffsetHeight() + 10);//
		//
	}

	/*
	 * Real gwt app entry point, this code allow GWT Log to catch exception and display it (non-Javadoc)
	 *
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	@SuppressWarnings("deprecation")
	public void onModuleLoad() {


		Log.setUncaughtExceptionHandler();
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				GWTUMLDrawer.this.gwt_main();
			}
		});

		//For Visualizer Test 2015/01/6
		Runnable onLoadCallback = new Runnable() {
		      public void run() {

		        }
		      };
		VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
		//For Visualizer Test 2015/01/6
	}
}
