package com.objetdirect.gwt.umldrawer.client.example;


import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Sample SimpleDropController which discards draggable widgets which are dropped on it.
 */
final public class MyDropController2 extends SimpleDropController {

	private VerticalPanel bin;
	private List<DropController> dclList;

	public MyDropController2(VerticalPanel bin, List<DropController> dclList) {
		super(bin);
		this.bin = bin;
		this.dclList = dclList;
	}

	@Override
	public void onDrop(DragContext context) {
		super.onDrop(context);
		bin.getWidget(0).getElement().getStyle().setBackgroundColor("#ABCDEF");
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		bin.getElement().getStyle().setBackgroundColor("#FF0000");
	}

	@Override
	public void onLeave(DragContext context) {
		super.onLeave(context);
		bin.getElement().getStyle().setBackgroundColor("#00FF00");

	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
		bin.getElement().getStyle().setBackgroundColor("#0000FF");
	}
}
