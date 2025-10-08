package com.objetdirect.gwt.umldrawer.client.example;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.ReflectionPanel;
/**
 * Sample SimpleDropController which discards draggable widgets which are dropped on it.
 */
final public class ReflectionPanelDropController extends SimpleDropController {

	private ReflectionPanel reflectionPanel;

	public ReflectionPanelDropController(ReflectionPanel reflectionPanel) {
		super(reflectionPanel);
		this.reflectionPanel = reflectionPanel;
	}

	@Override
	public void onDrop(DragContext context) {
		super.onDrop(context);
		reflectionPanel.getElement().getStyle().setBackgroundColor("#ABCDEF");
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		reflectionPanel.getElement().getStyle().setBackgroundColor("#FF0000");
	}

	@Override
	public void onLeave(DragContext context) {
		super.onLeave(context);
		reflectionPanel.getElement().getStyle().setBackgroundColor("#00FF00");

	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
		reflectionPanel.getElement().getStyle().setBackgroundColor("#0000FF");
	}
}
