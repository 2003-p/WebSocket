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
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram.Type;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Top extends VerticalPanel {

	final Label	 title = new Label("Diagram Menu");
	final Button	toClassDiaramEditor= new Button(TextResource.CLASS_DIAGRAM.getMessage());
	final Button  toMisuseCaseDiagramEditor     = new Button(TextResource.MISUSECASE_DIAGRAM.getMessage());


	private final LoadingScreen	loadingScreen;

	/**
	 * Constructor of the {@link Top}
	 *
	 * @param isFromHistory
	 */



	public Top() {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();
		Logger.getGlobal().info("Starting App");
		System.out.println("Starting@NewTop");

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

		this.add(title);

		toClassDiaramEditor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				Top.this.setOptions("Drawer", UMLDiagram.Type.CLASS);
				Session.exerciseId = 1;
				Session.diagramType = UMLDiagram.Type.CLASS;
				History.newItem("Menu");
			}
		});
		this.add(toClassDiaramEditor);

		 toMisuseCaseDiagramEditor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				Top.this.setOptions("Drawer", UMLDiagram.Type.MISUSECASE);
				Session.exerciseId = 2;
				Session.diagramType = UMLDiagram.Type.MISUSECASE;
				History.newItem("MenuForMisuseCase");
			}
		});
		this.add( toMisuseCaseDiagramEditor);


		this.loadingScreen.hide();
	}

	private void setOptions(final String newHistoryToken, final Type type) {
		OptionsManager.set("DiagramType", type.getIndex());
		//History.newItem(newHistoryToken + "?" + OptionsManager.toURL());
	}
}
