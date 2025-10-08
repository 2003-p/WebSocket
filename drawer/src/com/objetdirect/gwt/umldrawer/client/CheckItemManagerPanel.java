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

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;

/**
 * This class is the index panel displaying drawer options and logo
 *
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class CheckItemManagerPanel extends VerticalPanel {


	private final LoadingScreen loadingScreen;
	final Label  pageTitle = new Label("Check Item Manager");
	final Label  title = new Label("Check Item Manager");
	VerticalPanel vp = new VerticalPanel();

	private List<CheckItem> checkItemList;


	final Button updateButton = new Button("Update");
	final Button addButton = new Button("Add");
	/**
	 * Constructor of the {@link CheckItemManagerPanel}
	 *
	 * @param isFromHistory
	 */
	public CheckItemManagerPanel() {
		super();
		this.loadingScreen = new LoadingScreen();
		this.loadingScreen.show();

		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setSpacing(20);

		this.getCheckItemList();

//TODO
		this.setSpacing(5);
		this.add(this.pageTitle);

		this.addButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				checkItemList.add(new CheckItem(-1, "1"));
				vp.clear();
				createCheckItemEditor(checkItemList);
			}
		});

		this.updateButton.addClickHandler( new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				checkItemList = new LinkedList<CheckItem>();
				for(int i = 0 ; i < vp.getWidgetCount() ; i++){
					checkItemList.add( new CheckItem(-1, ((TextBox)((HorizontalPanel)vp.getWidget(i)).getWidget(1)).getText()));
				}
				ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
				ServiceDefTarget entryPoint = (ServiceDefTarget) async;
				String entryURL = GWT.getModuleBaseURL() + "updateCheckItem";
				entryPoint.setServiceEntryPoint(entryURL);
				AsyncCallback<Void> callback = new AsyncCallback<Void>(){
					public void onSuccess(Void result){
						Window.alert("Updated!");
						getCheckItemList();
					}
					public void onFailure(Throwable caught){
						Window.alert("Error");
					}
				};
				async.updateCheckItem( checkItemList, callback );
			}
		});

		this.loadingScreen.hide();
	}
	private void getCheckItemList() {
		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getAllCheckItemList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<CheckItem>> callback = new AsyncCallback<List<CheckItem>>(){
			public void onSuccess(List<CheckItem> result){
				CheckItemManagerPanel.this.checkItemList = result;
				CheckItemManagerPanel.this.createCheckItemEditor(checkItemList);
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getAllCheckItemList( callback );
	}
	protected void createCheckItemEditor(final List<CheckItem> CIList) {
		vp.clear();
		for(int i = 0 ; i < CIList.size() ; i++){
			final HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(5);
			hp.add(new Label(CIList.get(i).getCheckItemId()+""));
			TextBox tb = new TextBox();
			tb.setText(CIList.get(i).getCheckItem());
			tb.setWidth("900px");
			hp.add(tb);
			Button removeButton = new Button("Remove");
			removeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					checkItemList.remove(vp.getWidgetIndex(hp));
					hp.removeFromParent();
				}
			});
			hp.add(removeButton);
			vp.add(hp);
		}
		this.add(vp);
		this.add(addButton);
		this.add(updateButton);
	}


}
