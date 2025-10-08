package com.objetdirect.gwt.umlapi.client.gfx;

import java.util.logging.Logger;

import com.allen_sauer.gwt.log.client.Log;


 class Ellipse extends IncubatorGfxObject{

	private int radiusX = 0;
	private int radiusY =0;

	public Ellipse(final int radiusX,final int radiusY){
		super();
		this.radiusX= radiusX;
		this.radiusY= radiusY;
	}

	@Override
	public void draw() {
		if (!this.isVisible) {
			Logger.getGlobal().info(this + " is not visible");
			return;
		}
		if (this.canvas == null) {
			Logger.getGlobal().info("canvas is null for " + this);
		}

		Logger.getGlobal().info("Drawing " + this);
		this.canvas.saveContext();
		if (this.fillColor != null) {
			this.canvas.setFillStyle(this.fillColor);
		}
		if (this.strokeColor != null) {
			this.canvas.setStrokeStyle(this.strokeColor);
		}
		if (this.strokeWidth != 0) {
			this.canvas.setLineWidth(this.strokeWidth);
		}
//		this.canvas.fillEllipse(this.getX(), this.getY(), this.radiusX, this.radiusY);
//		this.canvas.strokeEllipse(this.getX(), this.getY(), this.radiusX, this.radiusY);
//		this.canvas.restoreContext();

	}

	@Override
	public int getHeight() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public boolean isPointed(int xp, int yp) {
		return (xp > this.getX()) && (xp < this.getX() + this.radiusX) && (yp > this.getY()) && (yp < this.getY() + this.radiusY);
	}


}