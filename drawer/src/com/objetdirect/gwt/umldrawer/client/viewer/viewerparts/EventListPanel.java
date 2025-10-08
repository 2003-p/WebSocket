package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerForStudentBase;

public class EventListPanel extends ScrollPanel{
	private int eventPanelHeight;
	private int eventPanelListSpacing;
	private List<EditEvent> editEventList;
	private DrawerPanelForViewer drawerPanelForViewer;
	private int selectedIdx;
	private VerticalPanel editEventCellList;
	private int width;
	private int height;
	private List<Integer> scrollPositionList;
	private ViewerForStudentBase viewerForStudentBase;
	private boolean scrollWithSelect;


	public EventListPanel(ViewerForStudentBase viewerForStudentBase, List<EditEvent> editEventList, DrawerPanelForViewer drawerPanelForViewer, int width, int height) {
		super();
		this.viewerForStudentBase = viewerForStudentBase;
		this.width = width;
		this.height = height;
		this.setPixelSize(width, height);
		this.eventPanelHeight = 75;
		this.eventPanelListSpacing = 2;
		this.drawerPanelForViewer = drawerPanelForViewer;
		this.editEventList = new ArrayList<EditEvent>();

		this.scrollPositionList = new ArrayList<Integer>();
		for(int i=0, j = (this.eventPanelHeight+this.eventPanelListSpacing)/2; i < editEventList.size() ; i++, j+=this.eventPanelHeight+this.eventPanelListSpacing-1){
			this.scrollPositionList.add(j);
		}

		this.createEditEventCellList(editEventList);

		this.add(editEventCellList);

		this.addScrollHandler(new ScrollHandler() {
			@Override
			public void onScroll(ScrollEvent event) {
				int newIdx = -1;
				for(int i =0; i < scrollPositionList.size() ; i++){
					if(getVerticalScrollPosition() <=  scrollPositionList.get(i) ){
						newIdx  = i;
						break;
					}
				}
				if(scrollWithSelect){
					if(newIdx == -1) EventListPanel.this.selectedIdx = EventListPanel.this.editEventList.size() - 1;
					else EventListPanel.this.selectedIdx = newIdx;
					EventPanel ep = (EventPanel) ( editEventCellList.getWidget(selectedIdx) );
					ep.select(false);
				}
				scrollWithSelect = true;
			}
		});

		this.selectedIdx = editEventList.size() - 1;
		EventPanel ep = (EventPanel)( this.editEventCellList.getWidget(this.selectedIdx) );
		ep.select(true);
	}

	private void createEditEventCellList(List<EditEvent> editEventList) {
		this.editEventCellList = new VerticalPanel();
		this.editEventCellList.setSpacing(this.eventPanelListSpacing);
		if( editEventList == null) return;
		for(EditEvent ev :  editEventList ){
			this.addEditEvent(ev);
		}
	}

	public void repleyByStep(boolean isFoward) { //isFoward true:進む false:戻る
		if(isFoward){
			if( selectedIdx < editEventList.size()-1) {
				selectedIdx += 1;
			}
		}
		else{
			if( selectedIdx > 0) {
				selectedIdx -= 1;
			}
		}
		EditEvent ev = editEventList.get(selectedIdx);
		String url = ev.getCanvasUrl();
		drawerPanelForViewer.clearCanvas();
		this.fromURL(drawerPanelForViewer, url, false);
		EventPanel ep =(EventPanel) (editEventCellList.getWidget(selectedIdx) );
		ep.select(true);
	}

	public void selectionChange(int selectedIndex, boolean withScroll){
		for(int i = 0 ; i < editEventCellList.getWidgetCount() ; i++){
			if( i == selectedIndex ) continue;
			EventPanel ep = (EventPanel) editEventCellList.getWidget(i);
			ep.deselect();
		}
		EditEvent ev = editEventList.get(selectedIndex);
	//ReflectionListとCommentListの選択を解除（targetが登録されないように）
		viewerForStudentBase.getReflectionListPanel().selectByEventId(-1, true, false);
		viewerForStudentBase.getCommentListPanel().selectByEventId(-1, true, false);
	//Canvasを更新
		drawerPanelForViewer.clearCanvas();
		String url = ev.getCanvasUrl();
		this.fromURL(drawerPanelForViewer, url, false);
		if(ev.getTargetId() >= 0){
			UMLArtifact artifact = UMLArtifact.getArtifactById(ev.getTargetId());
			drawerPanelForViewer.getUMLCanvas().selectArtifact(artifact);
		}
		this.selectedIdx = selectedIndex;

		//ReflectionListとCommentListの選択を更新
		viewerForStudentBase.getReflectionListPanel().selectByEventId(ev.getEditEventId(), true, false);
		viewerForStudentBase.getCommentListPanel().selectByEventId(ev.getEditEventId(), true, false);

		//TODO FIXME
		if(withScroll){
			if(selectedIndex == 0) this.setVerticalScrollPosition(0);
			else
				this.setVerticalScrollPosition(this.scrollPositionList.get(selectedIndex-1));
		}
	}
	private void fromURL(DrawerPanelForViewer drawerPanelForViewer,String url,boolean isForPasting){
		Session.setMode("replay");
		drawerPanelForViewer.fromURL(url, isForPasting);
		Session.setMode("viewer");
	}

	public EventPanel addEditEvent(EditEvent event){
		this.editEventList.add(event);
		EventPanel ep = new EventPanel(this, event, this.width-25, this.eventPanelHeight);
		this.editEventCellList.add( ep );
		return ep;
	}

	public EventPanel addEditEvent(int index, EditEvent event){
		this.editEventList.add(index, event);
		EventPanel rp = new EventPanel(this, event, this.width-25, this.eventPanelHeight);
		if(index <= 0){
			this.editEventCellList.add( rp );
		}else{
			this.editEventCellList.insert( rp, index - 1);
		}
		return rp;
	}

	public void removeEditEvent(int index){
		this.editEventList.remove(index);
		this.editEventCellList.remove(index);
	}

	public EventPanel getEventPanel(int index){
		return (EventPanel) this.editEventCellList.getWidget(index);
	}

	public EditEvent getEditEvent(int index){
		return this.editEventList.get(index);
	}

	public void setEditEvent(int index, EditEvent event){
		this.editEventList.set(index, event);
		this.editEventCellList.insert( new EventPanel(this, event, this.width-25, this.eventPanelHeight), index);
		this.editEventCellList.remove(index);
	}

	public int size(){
		return this.editEventList.size();
	}

	public List<EditEvent> getEditEventList() {
		return editEventList;
	}

	public void setEditEventList(List<EditEvent> editEventList) {
		this.editEventList = editEventList;
	}

	public int getSelectedIdx() {
		return selectedIdx;
	}

	public void setSelectedIdx(int selectedIdx) {
		this.selectedIdx = selectedIdx;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ViewerForStudentBase getViewerForStudentBase() {
		return viewerForStudentBase;
	}

	public void setViewerForStudentBase(ViewerForStudentBase viewerForStudentBase) {
		this.viewerForStudentBase = viewerForStudentBase;
	}

	public EventPanel getEventPanelByEditEventId(int occurrencePointId) {
		EventPanel ep;
		EditEvent event;
		int i=0;
		for(EditEvent e: this.editEventList){
			if(e.getEditEventId() == occurrencePointId){
				event = e;
				break;
			}
			i++;
		}

		return this.getEventPanel(i);
	}

	public boolean isScrollWithSelect() {
		return scrollWithSelect;
	}

	public void setScrollWithSelect(boolean scrollWithSelect) {
		this.scrollWithSelect = scrollWithSelect;
	}

}

