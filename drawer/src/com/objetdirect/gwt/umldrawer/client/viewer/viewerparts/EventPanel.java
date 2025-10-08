package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;

public class EventPanel extends FocusPanel{
	private EventListPanel eventListPanel;
	private VerticalPanel base;
	private Label indexLabel;
	private Label beforeLabel;
	private Label afterLabel;
	private Label dateTimeLabel;
	private int width;
	private int heignt;
	private boolean isSelected;
	private EditEvent editEvent;
	private String backgroundColor;

	public EventPanel(){
	}

	public EventPanel(EventListPanel eventListPanel, EditEvent editEvent, int width, int heignt) {
		super();
		DragController dragController = eventListPanel.getViewerForStudentBase().getDragController();
		dragController.makeDraggable(this);

		this.eventListPanel = eventListPanel;
		this.base = new VerticalPanel();
		this.add(base);
		this.width = width;
		this.heignt = heignt;
		this.isSelected = false;
		this.editEvent = editEvent;
		this.setPixelSize(this.width, this.heignt);
		base.setPixelSize(this.width, this.heignt);
		String eventType;
		if(editEvent.getEventType().equals("Remove") || editEvent.getEventType().equals("RemoveArtifacts")){
			eventType  = "Remove";
		}
		else{
			eventType = editEvent.getEventType();
		}
		indexLabel = new Label( eventListPanel.getEditEventList().indexOf(this.editEvent)+"     "+eventType );
		base.add( indexLabel );
		beforeLabel = new Label( abbriviate(editEvent.getBeforeEdit(), 10, true) );
		base.add( beforeLabel );
		afterLabel = new Label("=> " + abbriviate(editEvent.getAfterEdit(), 10, true) );
		base.add( afterLabel );
		dateTimeLabel = new Label( abbriviate(editEvent.getEditDatetime().toString(), 19, false) );
		base.add(dateTimeLabel);

		this.setBackgroundColor(editEvent.getEventType());

		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(isSelected) {
					deselect();
				}
				else {
					select(false);
				}
			}
		});
//		this.addDoubleClickHandler(new DoubleClickHandler() {
//			@Override
//			public void onDoubleClick(DoubleClickEvent event) {
//				select(false);
//				ReflectionEditor editor;
//				ReflectionListPanel reflectionListPanel = EventPanel.this.eventListPanel.getViewerForStudentBase().getReflectionListPanel();
//				Reflection reflection=new Reflection();
//				ReflectionPanel reflectionPanel=null;
//				boolean exist = false;
//				for( Reflection r : reflectionListPanel.getReflectionList() ){
//					if(r.getOccurrencePoint().getEditEventId() == EventPanel.this.editEvent.getEditEventId()){
//						reflection = r;
//						reflectionPanel = reflectionListPanel.getReflectionPanelByReflection(r);
//						exist = true;
//					}
//				}
//				if(exist){
//					editor = new ReflectionEditor(reflection, reflectionPanel, 300, 500);
//				}
//				else{
//					reflection.setOccurrencePoint(EventPanel.this.editEvent);
//					reflectionPanel = reflectionListPanel.addReflection(reflection);
//				}
//				editor = new ReflectionEditor(reflection, reflectionPanel, 300, 500);
//				editor.setAutoHideOnHistoryEventsEnabled(true);
//				editor.setPopupPosition(350, 10);
//				editor.show();
//			}
//		});
	}

	public void select(boolean withScroll) {
		eventListPanel.selectionChange( this.eventListPanel.getEditEventList().indexOf(this.editEvent) , withScroll);
		base.getElement().getStyle().setBackgroundColor("#00ffff");
		this.isSelected = true;
	}

	public void deselect(){
		base.getElement().getStyle().setBackgroundColor( this.backgroundColor );
		this.isSelected = false;
	}

	public boolean isSeledted(){
		return this.isSelected;
	}

	private String abbriviate(String s, int n, boolean withPoint){
		if( s != null){
			if ( s.length() > n){
				s = (String) s.subSequence(0, n);
				if( withPoint ) s=s+"...";
			}

		}
		return s;
	}

	private void setBackgroundColor( String eventType ){
		if(eventType.equals("Create")){
			this.backgroundColor = "#99cc00";
		} else if(eventType.equals("Remove") || eventType.equals("RemoveArtifacts")){
			this.backgroundColor = "#ffd700";
		} else if(eventType.equals("Edit")){
			this.backgroundColor = "#DD6633";
		} else if(eventType.equals("Submit")){
			this.backgroundColor = "#ff4500";
		} else if(eventType.equals("Undo")){
			this.backgroundColor = "#ee82ee";
		} else if(eventType.equals("Check")){
			this.backgroundColor = "#ff8888";
		}
		else{
			this.backgroundColor = "#DDDDDD";
		}
		base.getElement().getStyle().setBackgroundColor( this.backgroundColor );
	}

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeignt() {
		return heignt;
	}
	public void setHeignt(int heignt) {
		this.heignt = heignt;
	}

	public EventPanel updateByNewReflection(Reflection reflection){
		beforeLabel.setText( reflection.getImagedSituation() );
		return this;
	}

	public EventListPanel getReflectionListPanel() {
		return eventListPanel;
	}

	public void setEventListPanel(EventListPanel parent) {
		this.eventListPanel = parent;
	}

	public EditEvent getEditEvent() {
		return editEvent;
	}

	public void setEditEvent(EditEvent event) {
		this.editEvent = event;
	}



}
