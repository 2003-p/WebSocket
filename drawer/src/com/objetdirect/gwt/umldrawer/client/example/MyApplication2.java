///*
// * Copyright 2009 Fred Sauer
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
// * in compliance with the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License
// * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
// * or implied. See the License for the specific language governing permissions and limitations under
// * the License.
// */
//package com.objetdirect.gwt.umldrawer.client.example;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.allen_sauer.gwt.dnd.client.PickupDragController;
//import com.allen_sauer.gwt.dnd.client.drop.DropController;
//import com.google.gwt.user.client.ui.AbsolutePanel;
//import com.google.gwt.user.client.ui.DockPanel;
//import com.google.gwt.user.client.ui.FocusPanel;
//import com.google.gwt.user.client.ui.ScrollPanel;
//import com.google.gwt.user.client.ui.SimplePanel;
//import com.google.gwt.user.client.ui.VerticalPanel;
//import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.ReflectionPanel;
//
///**
// * Illustrative example.
// */
//public class MyApplication2 extends AbsolutePanel {
//	private AbsolutePanel groundPanel;
//	private PickupDragController dragController;
//	private DropController dropController;
//	private List<DropController> myDCList;
//
//	public MyApplication2() {
//		// Create a boundary panel to constrain all drag operations
//		this.setPixelSize(700, 600);
//		this.groundPanel = new AbsolutePanel();
//		this.groundPanel.setPixelSize(700, 600);
//		this.getElement().getStyle().setBackgroundColor("#FFFFFF");
//		this.getElement().getStyle().setBorderColor("#000000");
//
//		DockPanel dp = new DockPanel();
//		this.add(dp);
//		// Create a drop target on which we can drop labels
////		AbsolutePanel targetPanel = new AbsolutePanel();
////		targetPanel.setPixelSize(0, 0);
////		targetPanel.getElement().getStyle().setBackgroundColor("#007700");
//
//		VerticalPanel skin = new VerticalPanel();
//		skin.setPixelSize(100, 100);
//		skin.add(new SimplePanel());
//
//		ReflectionPanel myBin = new ReflectionPanel(null, null, 100, 100);
//		myBin.setPixelSize(100, 100);
//		myBin.getElement().getStyle().setBackgroundColor("#FF77FF");
//
//		FocusPanel myBin2 = new FocusPanel();
//		myBin2.setPixelSize(100, 100);
//		myBin2.getElement().getStyle().setBackgroundColor("#FF77FF");
//
//		// Add both panels to the root panel
//		VerticalPanel binList = new VerticalPanel();
//		binList.setSpacing(2);
//		ScrollPanel spBin = new ScrollPanel();
//		spBin.setPixelSize(110, 150);
//		binList.add(myBin);
//		binList.add(myBin2);
//
//		spBin.add(binList);
//		dp.add(spBin, DockPanel.WEST);
//		this.add(dp, 100,100);
//
//		this.add(skin, 100, 100);
//		// Create a DragController for each logical area where a set of draggable
//		// widgets and drop targets will be allowed to interact with one another.
//		dragController = new PickupDragController(this, true);
//		// Positioner is always constrained to the boundary panel
//		// Use 'true' to also constrain the draggable or drag proxy to the boundary panel
//		dragController.setBehaviorConstrainedToBoundaryPanel(false);
//		// Allow multiple widgets to be selected at once using CTRL-click
//		dragController.setBehaviorMultipleSelection(true);
//
//		// create a DropController for each drop target on which draggable widgets
//		// can be dropped
//		myDCList = new ArrayList<DropController>();
//		dropController = new MyAbsolutePositionDropController(this);
//		myDCList.add( new ReflectionPanelDropController(myBin.getBase()) );
//		myDCList.add( new MyDropController2(myBin2) );
//
//		// Don't forget to register each DropController with a DragController
//		dragController.registerDropController(dropController);
//		dragController.registerDropController(myDCList.get(0));
//		dragController.registerDropController(myDCList.get(1));
//
//
//		// create a few randomly placed draggable labels
//		VerticalPanel labelList = new VerticalPanel();
//		labelList.setSpacing(2);
//		ScrollPanel sp = new ScrollPanel();
//		sp.setPixelSize(40, 100);
//		for (int i = 1; i <= 5; i++) {
//			// create a label and give it style
//			FocusPanel label = new FocusPanel();
//			label.setPixelSize(30,30);
//			label.getElement().getStyle().setBackgroundColor("#FF0000");
//
//			// add it to the DOM so that offset width/height becomes available
//			labelList.add(label);
//			// determine random label location within target panel
//			// move the label
//
//			dragController.setBehaviorDragProxy(true);
//			// make the label draggable
//			dragController.makeDraggable(label);
//		}
//		sp.add(labelList);
//		this.add(sp);
//	}
//}
