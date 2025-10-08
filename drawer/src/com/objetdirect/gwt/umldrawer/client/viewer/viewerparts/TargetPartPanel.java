package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;

public class TargetPartPanel extends VerticalPanel{
	private Reflection refletion;
	private ReflectionPanel reflectionPanel;
	private EditEvent occurrencePointEvent;
	private List<Integer> targetIdList;

	public TargetPartPanel(Reflection refletion,
			ReflectionPanel reflectionPanel, EditEvent occurrencePointEvent) {
		super();
		this.refletion = refletion;
		this.reflectionPanel = reflectionPanel;
		this.occurrencePointEvent = occurrencePointEvent;
		this.setTargetIdList(refletion.getTargetPartIdList());
	}

	private void makeList(){
		HorizontalPanel hp;
		for(int targetId : this.targetIdList){
			hp = new HorizontalPanel();

			final RemoveButton removeButton = new RemoveButton("Del.",targetId);
			removeButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					TargetPartPanel.this.refletion.getTargetPartIdList().remove( removeButton.getId() );
					TargetPartPanel.this.updateByReflection(TargetPartPanel.this.refletion);
				}
			});
			add(removeButton);

			add(new Label( Integer.toString( targetId ) ) );
		}
	}

	class RemoveButton extends Button{
		private int id;
		public RemoveButton(int id){
			super();
			this.id = id;
		}
		public RemoveButton(String text, int id){
			super(text);
			this.id = id;
		}
		public int getId(){
			return this.id;
		}
	}

	private void updateByReflection(Reflection reflection){
		this.reflectionPanel.updateByNewReflection(reflection);
	}

	public Reflection getRefletion() {
		return refletion;
	}

	public void setRefletion(Reflection refletion) {
		this.refletion = refletion;
	}

	public ReflectionPanel getReflectionPanel() {
		return reflectionPanel;
	}

	public void setReflectionPanel(ReflectionPanel reflectionPanel) {
		this.reflectionPanel = reflectionPanel;
	}

	public EditEvent getOccurrencePointEvent() {
		return occurrencePointEvent;
	}

	public void setOccurrencePointEvent(EditEvent occurrencePointEvent) {
		this.occurrencePointEvent = occurrencePointEvent;
	}

	public List<Integer> getTargetIdList() {
		return targetIdList;
	}

	public void setTargetIdList(List<Integer> targetIdList) {
		if(targetIdList == null) this.targetIdList = new ArrayList<Integer>();
		else this.targetIdList = targetIdList;
	}




}
