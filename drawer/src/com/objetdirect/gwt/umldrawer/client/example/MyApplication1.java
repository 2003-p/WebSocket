/*
 * Copyright 2009 Fred Sauer
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.objetdirect.gwt.umldrawer.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Panel;

/**
 * Illustrative example.
 */
public class MyApplication1 extends AbsolutePanel{
	public MyApplication1(){

    // ensure the document BODY has dimensions in standards mode
	this.setPixelSize(600, 600);
	this.getElement().getStyle().setBackgroundColor("#AAAA00");

    // create a DragController to manage drag-n-drop actions
    // note: This creates an implicit DropController for the boundary panel
    PickupDragController dragController = new PickupDragController(this, true);

    // add a new image to the boundary panel and make it draggable
    Panel sp  = new FocusPanel();
    sp.setPixelSize(30, 30);
    sp.getElement().getStyle().setBorderColor("#0000FF");
    sp.getElement().getStyle().setBackgroundColor("#00FFFF");
    this.add(sp, 40, 30);
    dragController.makeDraggable(sp);
  }
}
