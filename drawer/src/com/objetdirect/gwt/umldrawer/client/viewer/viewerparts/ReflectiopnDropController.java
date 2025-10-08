package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;


import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

/**
 * Sample SimpleDropController which discards draggable widgets which are dropped on it.
 */
final public class ReflectiopnDropController extends SimpleDropController {

	private ReflectionPanel reflectionPanel;
	private VerticalPanel reflectionPanelBase;

	public ReflectiopnDropController(VerticalPanel reflectionPanelBase, ReflectionPanel reflectionPanel){
		super(reflectionPanelBase);
		this.reflectionPanelBase = reflectionPanelBase;
		this.reflectionPanel = reflectionPanel;
	}


	@Override
	public void onDrop(DragContext context) {
		super.onDrop(context);
		EventPanel eep= (EventPanel)( context.draggable );
		List<EditEvent> modificationEventList = reflectionPanel.getReflection().getModificationEventList();
		if(DrawerSession.student.getStudentId().equals(reflectionPanel.getReflection().getStudentId())){
			if( ! modificationEventList.contains(eep.getEditEvent() ) ){
				reflectionPanel.getReflection().getModificationEventList().add(eep.getEditEvent());
			}
			reflectionPanel.updateByNewReflection(reflectionPanel.getReflection());
			reflectionPanelBase.getElement().getStyle().setBackgroundColor("#ABCDEF");
		}
	}

	@Override
	public void onEnter(DragContext context) {
		super.onEnter(context);
		reflectionPanelBase.getElement().getStyle().setBackgroundColor("#FF0000");
	}

	@Override
	public void onLeave(DragContext context) {
		super.onLeave(context);
		reflectionPanelBase.getElement().getStyle().setBackgroundColor("#00FF00");

	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		super.onPreviewDrop(context);
		reflectionPanelBase.getElement().getStyle().setBackgroundColor("#0000FF");
	}
}
