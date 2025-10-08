package com.objetdirect.gwt.umldrawer.client.viewer;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.EventPanel;

public class MyPickupDragController extends PickupDragController{

	public MyPickupDragController(AbsolutePanel boundaryPanel,
			boolean allowDroppingOnBoundaryPanel) {
		super(boundaryPanel, allowDroppingOnBoundaryPanel);
	}

	@Override
	public void dragStart() {
		super.dragStart();
	      for (Widget widget : context.selectedWidgets) {
	    	  if(widget instanceof EventPanel){
	    		   ((EventPanel) widget).select(false);
	    	  }
	        }
	}

}
