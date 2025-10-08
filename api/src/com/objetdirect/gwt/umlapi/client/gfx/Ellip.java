package com.objetdirect.gwt.umlapi.client.gfx;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.gfx.GraphicObject;

public class Ellip extends GraphicObject{

	private double width;
	private double height;

	public Ellip(double width, double height){

		this.width=width;
		this.height=height;
	}

	public Ellip(Ellipse ellip){
		this(ellip.getWidth(),ellip.getHeight());
	}

	public double getWidth(){
		return this.width;
	}

	public double getHeight(){
		return this.height;
	}


	public void setWidth(int width){
		float factor = width / (float)getWidth();
		this.width = width;

		if(getShape() != null)
			scale(factor, 1.0F);
	}

	public void setHeight(int height){
		float factor = height/(float)getHeight();
		this.height = height;

		if(getShape() != null)
			scale(1.0F ,factor);
	}

	@Override
	protected JavaScriptObject createGfx(JavaScriptObject surface){
		return createGfx(surface , getWidth(), getHeight());
	}

	protected native JavaScriptObject createGfx(JavaScriptObject surface,double width, double height)/*-{
	 return surface.createEllipse({
	   cx :  (width/2)*10/10,
	   cy : height*1/2,
	   rx :  (width/2)*15/10,
	   ry : height*2/3
	   });
	  }-*/;
}