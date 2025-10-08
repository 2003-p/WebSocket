/**
 *
 */
package com.objetdirect.gwt.umldrawer.client.viewer;

/**
 * @author tanaka
 *
 */

import java.util.ArrayList;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.CommentListPanel;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.DrawerPanelForViewer;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.EventListPanel;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.GroundAbsolutePositionDropController;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.LeftButtonPanel;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.ReflectionListPanel;

public class ViewerForStudentBase extends DockPanel {
	private String studentId;
	private int exerciseId;

	private VerticalPanel leftSideBar;
	private VerticalPanel rightSideBar;
	private HorizontalPanel northBar;
	private HorizontalPanel southBar;
	private VerticalPanel leftVertical;
	private HorizontalPanel leftHorizontal;

	//Parts
	private DrawerPanelForViewer drawerPanelForViewer;
	private LeftButtonPanel leftButtonPanel;
	private DeckPanel reflectionDeckPanel;
	private ReflectionListPanel reflectionListPanel;
	private CommentListPanel commentListPanel;
	private EventListPanel eventListPanel;
	private GroundAbsolutePositionDropController dropController;
	private PickupDragController dragController;



	public ViewerForStudentBase(String studentId, int exerciseId) {
		super();
		updateViewer(studentId, exerciseId);
	}

	public void updateViewer(String studentId, int exerciseId){
		this.clear();
		this.setHorizontalAlignment(ALIGN_LEFT);
		this.studentId = studentId;
		this.exerciseId = exerciseId;
		dragController = new MyPickupDragController(RootPanel.get(), true);
		dragController.setBehaviorConstrainedToBoundaryPanel(false);
		dragController.setBehaviorMultipleSelection(false);
		dragController.setBehaviorDragProxy(true);
		dropController = new GroundAbsolutePositionDropController(RootPanel.get());
		dragController.registerDropController(this.dropController);

		this.leftSideBar = new VerticalPanel();
		this.rightSideBar = new VerticalPanel();
		this.northBar = new HorizontalPanel();
		this.southBar = new HorizontalPanel();
		this.leftVertical = new VerticalPanel();;
		this.leftHorizontal = new HorizontalPanel();
		this.leftHorizontal.setSpacing(2);
		this.leftVertical.setSpacing(3);
		leftSideBar.add(leftVertical);

		//Parts Init
		this.partsInit();

		this.add(leftSideBar, DockPanel.WEST);
		leftSideBar.setSize("340px", (int)(Window.getClientHeight())+"px");
		this.add(rightSideBar, DockPanel.EAST);
		this.add(northBar, DockPanel.NORTH);
		this.add(southBar, DockPanel.SOUTH);
		this.add(drawerPanelForViewer, DockPanel.CENTER);

		this.loadCanvas();
	}

	private void partsInit(){
		this.drawerPanelForViewer = new DrawerPanelForViewer(370, 30);

	}

	private void loadCanvas(){

		reflectionDeckPanel = new DeckPanel();
		reflectionListPanel = new ReflectionListPanel(this, this.studentId, this.exerciseId, 160, (int)(Window.getClientHeight())-100);
		commentListPanel = new CommentListPanel(this, this.studentId, this.exerciseId, 160,  (int)(Window.getClientHeight())-100);
		reflectionDeckPanel.add(reflectionListPanel);
		reflectionDeckPanel.add(commentListPanel);

		LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getEditEventList";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result){
				GWT.log("ああああああああああああああああああああああああああああああああああああああああ");
				String str = String.valueOf((List<EditEvent>)result);
				GWT.log("str"+str);
				System.out.println((List<EditEvent>) result);
				List<EditEvent> editEventList = new ArrayList<EditEvent>();
				editEventList = (List<EditEvent>) result;

				drawerPanelForViewer.clearCanvas();
				//drawerPanel.fromURL(editEventList.get(editEventList.size()-1).getCanvasUrl(),false);
				eventListPanel = new EventListPanel(ViewerForStudentBase.this, editEventList, drawerPanelForViewer, 160, (int)(Window.getClientHeight())-100);
				leftHorizontal.add(eventListPanel);
				boolean isTeacher = DrawerSession.student.getType() == 1 ?  true:false;
				leftButtonPanel = new LeftButtonPanel(isTeacher, ViewerForStudentBase.this, eventListPanel, reflectionListPanel);
				leftVertical.add(leftButtonPanel);

				leftHorizontal.add(reflectionDeckPanel);
				reflectionDeckPanel.showWidget(0);
				leftVertical.add(leftHorizontal);
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getEditEventList(this.studentId, this.exerciseId, callback);
	}
	
	


	/**
	 * @return leftSideBar
	 */
	public VerticalPanel getLeftSideBar() {
		return leftSideBar;
	}
	/**
	 * @param leftSideBar セットする leftSideBar
	 */
	public void setLeftSideBar(VerticalPanel leftSideBar) {
		this.leftSideBar = leftSideBar;
	}
	/**
	 * @return rightSideBar
	 */
	public VerticalPanel getRightSideBar() {
		return rightSideBar;
	}
	/**
	 * @param rightSideBar セットする rightSideBar
	 */
	public void setRightSideBar(VerticalPanel rightSideBar) {
		this.rightSideBar = rightSideBar;
	}
	/**
	 * @return northBar
	 */
	public HorizontalPanel getNorthBar() {
		return northBar;
	}
	/**
	 * @param northBar セットする northBar
	 */
	public void setNorthBar(HorizontalPanel northBar) {
		this.northBar = northBar;
	}
	/**
	 * @return southBar
	 */
	public HorizontalPanel getSouthBar() {
		return southBar;
	}
	/**
	 * @param southBar セットする southBar
	 */
	public void setSouthBar(HorizontalPanel southBar) {
		this.southBar = southBar;
	}

	public PickupDragController getDragController() {
		return dragController;
	}

	public void setDragController(PickupDragController dragController) {
		this.dragController = dragController;
	}

	public EventListPanel getEventListPanel() {
		return eventListPanel;
	}

	public void setEventListPanel(EventListPanel eventListPanel) {
		this.eventListPanel = eventListPanel;
	}

	public ReflectionListPanel getReflectionListPanel() {
		return reflectionListPanel;
	}

	public void setReflectionListPanel(ReflectionListPanel reflectionListPanel) {
		this.reflectionListPanel = reflectionListPanel;
	}

	public DrawerPanelForViewer getDrawerPanelForViewer() {
		return drawerPanelForViewer;
	}

	public void setDrawerPanelForViewer(DrawerPanelForViewer drawerPanelForViewer) {
		this.drawerPanelForViewer = drawerPanelForViewer;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}

	public DeckPanel getReflectionDeckPanel() {
		return reflectionDeckPanel;
	}

	public void setReflectionDeckPanel(DeckPanel reflectionDeckPanel) {
		this.reflectionDeckPanel = reflectionDeckPanel;
	}

	public CommentListPanel getCommentListPanel() {
		return commentListPanel;
	}

	public void setCommentListPanel(CommentListPanel commentListPanel) {
		this.commentListPanel = commentListPanel;
	}



}
