package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.core.client.GWT;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

class GWTCancasInheritance extends GWTCanvas{

	private final GWTCanvasImplInheritance impl = GWT.create(GWTCanvasImplInheritance.class);


	public void fillEllipse(double startX, double startY, double width, double height){
		impl.fillEllipse(startX, startY, width , height);
	}

}
