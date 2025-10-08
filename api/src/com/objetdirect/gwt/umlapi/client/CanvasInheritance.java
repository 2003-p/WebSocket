package com.objetdirect.gwt.umlapi.client;
import gwt.canvas.client.Canvas;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.DOM;

//2015/5/3
public class CanvasInheritance extends Canvas{

	private CanvasImplInheritance impl = (CanvasImplInheritance)GWT.create(CanvasImplInheritance.class);
//	private CanvasImpl impl = (CanvasImpl)GWT.create(CanvasImpl.class);


	//2015/5/3
	public CanvasInheritance(){
		this(300,150);
	}

	//2015/5/3
	public CanvasInheritance(int width, int height) {
		setElement(DOM.createElement("canvas"));
		impl.init(this);
		setStyleName("gwt-Canvas");
		setBackgroundColor("#fff");
		setWidth(width);
		setHeight(height);
	}


	//2015/5/3
	public void clearEllipse(double x,double y, double w, double h){
		String gco = impl.getGlobalCompositeOperation();
		String bgc = impl.getBackgroundColor();
		if(!gco.equalsIgnoreCase(Canvas.SOURCE_OVER) || bgc.equals(Canvas.TRANSPARENT)){
			throw new IllegalStateException();
		}
	}

	//2015/5/3
	public void fillEllipse(double x,double y,double w ,double h){
		impl.fillEllipse(x,y,w,h);
	}

	//2015/5/3
	public void strokeEllipse(double x, double y, double w, double h){
		impl.strokeEllipse(x, y, w, h);
	}

}