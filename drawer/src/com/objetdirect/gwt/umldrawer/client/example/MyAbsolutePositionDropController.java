package com.objetdirect.gwt.umldrawer.client.example;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class MyAbsolutePositionDropController extends AbsolutePositionDropController{

	public MyAbsolutePositionDropController(AbsolutePanel dropTarget) {
		super(dropTarget);
	}
	@Override
	public void onDrop(DragContext context) {
	}
}
