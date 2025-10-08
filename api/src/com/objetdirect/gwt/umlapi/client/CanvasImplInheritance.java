package com.objetdirect.gwt.umlapi.client;

import gwt.canvas.client.CanvasImpl;


//2015/5/3
class CanvasImplInheritance extends CanvasImpl{

	protected native void init() /*-{
	@com.objetdirect.gwt.umlapi.client.CanvasImplInheritance::context = this.@@com.objetdirect.gwt.umlapi.client.CanvasImplInheritance::element.getContext("2d");
}-*/;

	public native void clearEllipse(double x, double y, double w, double h)/*-{
	this.@com.objetdirect.gwt.umlapi.client.CanvasImplInheritance::context.clearEllipse(x, y, w, h);
}-*/;

	public native void fillEllipse(double x, double y ,double w, double h) /*-{
	this.@com.objetdirect.gwt.umlapi.client.CanvasImplInheritance::context.fillEllipse(x, y, w, h);
}-*/;

	public native void strokeEllipse(double x, double y, double w, double h) /*-{
	this.@com.objetdirect.gwt.umlapi.client.CanvasImplInheritance::context.strokeEllipse(x, y, w, h);
}-*/;


}