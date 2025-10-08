package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class GroundAbsolutePositionDropController extends AbsolutePositionDropController{

	public GroundAbsolutePositionDropController(AbsolutePanel dropTarget) {
		super(dropTarget);
	}

	@Override
	public void onDrop(DragContext context) {
		// Do nothing
	}

}
