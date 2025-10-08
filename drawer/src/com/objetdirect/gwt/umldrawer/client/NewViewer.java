/**
 *
 */
package com.objetdirect.gwt.umldrawer.client;

/**
 * @author tanaka
 *
 */

import static com.objetdirect.gwt.umldrawer.client.DrawerTextResource.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.client.comment.CommentServiceAsync;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.viewer.EditEventCell;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerService;
import com.objetdirect.gwt.umldrawer.client.viewer.LogViewerServiceAsync;

public class NewViewer extends DockPanel {
	private VerticalPanel leftSideBar;
	private VerticalPanel rightSideBar;
	private HorizontalPanel northBar;
	private HorizontalPanel southBar;

	//Buttons
	private List<EditEvent> editEventList;
	private Integer idxOfeditEventList;
	private Button forward;
	private Button back;
	private HashMap<Integer, Comment> commentMap;

	//Parts
	private DrawerPanel drawerPanel;

	private SingleSelectionModel<EditEvent> selectionModelForEditEventCellList;
	private EditEventCell editEventCell;
	private CellList<EditEvent> editEventCellList;
	private VerticalPanel commentPanel;
	private TextArea commentArea;
	ScrollPanel eventScrollPanel = new ScrollPanel();



	public NewViewer() {
		super();
		//System.out.println("++ID="+Session.getStudent().getStudentId());
		this.leftSideBar = new VerticalPanel();
		this.rightSideBar = new VerticalPanel();
		this.northBar = new HorizontalPanel();
		this.southBar = new HorizontalPanel();

		//*********************************************************************************************************
		//Param Init
		this.editEventList = new ArrayList<EditEvent>();
		this.idxOfeditEventList = -1;
		//ButtonInit
		this.buttonsInit();

		//Parts Init
		this.partsInit();

		this.createCommentMap(DrawerSession.student.getStudentId(), Session.exerciseId);

		this.add(leftSideBar, DockPanel.WEST);
		leftSideBar.setSize("330px", "800px");
		this.add(rightSideBar, DockPanel.EAST);
		this.add(northBar, DockPanel.NORTH);
		this.add(southBar, DockPanel.SOUTH);
		// 修正後（例）
		this.drawerPanel = new DrawerPanel(350, 10); // 新しく追加した「空っぽのレシピ」を呼び出す！
		this.add(this.drawerPanel, DockPanel.CENTER); 
	}

	private void buttonsInit(){
		//Buttons Init

		this.forward = new Button(FOWERD.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEvent EV = NewViewer.this.editEventList.get(NewViewer.this.idxOfeditEventList);
				NewViewer.this.editEventCellList.getSelectionModel().setSelected(EV, false);
				System.out.println("forward"+NewViewer.this.idxOfeditEventList);
				if( NewViewer.this.idxOfeditEventList < editEventList.size()-1) {
					NewViewer.this.idxOfeditEventList = NewViewer.this.idxOfeditEventList+1;
				}
				EV = NewViewer.this.editEventList.get(NewViewer.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				NewViewer.this.drawerPanel.clearCanvas();
				NewViewer.this.drawerPanel.fromURL(url, false);
				NewViewer.this.editEventCellList.getSelectionModel().setSelected(EV, true);

				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
				eventScrollPanel.setVerticalScrollPosition(idxOfeditEventList);
			}
		});
		this.back = new Button(BACK.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEvent EV = NewViewer.this.editEventList.get(NewViewer.this.idxOfeditEventList);
				NewViewer.this.editEventCellList.getSelectionModel().setSelected(EV, false);
				System.out.println("back"+NewViewer.this.idxOfeditEventList);
				if( NewViewer.this.idxOfeditEventList > 0) {
					NewViewer.this.idxOfeditEventList = NewViewer.this.idxOfeditEventList-1;
				}
				EV = NewViewer.this.editEventList.get(NewViewer.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				System.out.println(NewViewer.this.idxOfeditEventList);
				NewViewer.this.drawerPanel.clearCanvas();
				NewViewer.this.drawerPanel.fromURL(url, false);
				NewViewer.this.editEventCellList.getSelectionModel().setSelected(EV, true);

				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
				eventScrollPanel.setVerticalScrollPosition(idxOfeditEventList);
			}
		});
		NewViewer.this.leftSideBar.add(forward);
		forward.setSize("88px", "39px");
		NewViewer.this.leftSideBar.add(back);
		back.setSize("88px", "39px");
	}

	private void partsInit(){

		this.editEventCell = new EditEventCell();
		this.editEventCellList = new CellList<EditEvent>(this.editEventCell);

		this.selectionModelForEditEventCellList = new SingleSelectionModel<EditEvent>();
		this.createEditEventCellList();
		this.loadCanvas();
		this.commentPanel = new VerticalPanel();
		this.commentArea = new TextArea();
		this.commentArea.setSize("300px", "200px");
		this.commentArea.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				commentArea.setCursorPos(commentArea.getText().length());
			}
		});
		this.commentArea.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				addComment(editEventList.get(idxOfeditEventList).getEditEventId(), DrawerSession.student.getStudentId(), Session.exerciseId, commentArea.getText()  );

			}
		});
		this.commentPanel.add(commentArea);

		this.commentMap = new HashMap<Integer, Comment>();
	}

	private void createEditEventCellList() {
		NewViewer.this.editEventCellList.setSelectionModel(NewViewer.this.selectionModelForEditEventCellList);
		NewViewer.this.selectionModelForEditEventCellList.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				NewViewer.this.drawerPanel.clearCanvas();
				EditEvent EV = selectionModelForEditEventCellList.getSelectedObject();
				String url = EV.getCanvasUrl();
				NewViewer.this.drawerPanel.fromURL(url,false);
				NewViewer.this.idxOfeditEventList = NewViewer.this.editEventList.indexOf(EV);
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}
		});
	}

	private void loadCanvas(){

		LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getEditEventList";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result){
				System.out.println((List<EditEvent>) result);
				NewViewer.this.editEventList = (List<EditEvent>) result;

				//ムズイイベントとCanvasLoad
				for(EditEvent ev : NewViewer.this.editEventList){
					if(ev.getEventType().equals("Difficult") || ev.getEventType().equals("CanvasLoad") || ev.getEventType().equals("CanvasCloseOrRefresh")){
						NewViewer.this.editEventList.remove(ev);

					}
				}
				NewViewer.this.drawerPanel.clearCanvas();
				NewViewer.this.drawerPanel.fromURL(NewViewer.this.editEventList.get(NewViewer.this.editEventList.size()-1).getCanvasUrl(),false);
				NewViewer.this.idxOfeditEventList = NewViewer.this.editEventList.size()-1;
				NewViewer.this.editEventCellList.setRowData( (List<EditEvent>)result);

				NewViewer.this.leftSideBar.add(eventScrollPanel);
				eventScrollPanel.add(NewViewer.this.editEventCellList);
				eventScrollPanel.setHeight("300px");
				eventScrollPanel.scrollToBottom();

				leftSideBar.add(commentPanel);
				//ViewerForStudentBase.this.leftSideBar.add(ViewerForStudentBase.this.editEventCellList);
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getEditEventList(DrawerSession.student.getStudentId(), Session.exerciseId, callback);
	}

	private boolean addComment(int editEventId, String studentId, int exerciseId, String comment){
		CommentServiceAsync async = (CommentServiceAsync)GWT.create(CommentService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "addComment";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings("unchecked")
			public void onSuccess(Object result){
				if(result==null){
					Window.alert("Connection Error!");
				}
				else{
					Comment newComment = (Comment)result;
					commentMap.put(newComment.getEditEventId(), newComment);
					Window.alert("コメントを登録しました。");
				}
			}
			public void onFailure(Throwable caught){
			}
		};
		async.addComment(editEventId, studentId, exerciseId, comment, callback);
		return false;
	}

	private void createCommentMap(String studentId, int exerciseId){
		CommentServiceAsync async = (CommentServiceAsync)GWT.create(CommentService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		entryPoint.setServiceEntryPoint(GWT.getModuleBaseURL()+"getCommentList");
		AsyncCallback<List<Comment>> callback = new AsyncCallback<List<Comment>>() {

			@Override
			public void onSuccess(List<Comment> result) {
				for(Comment c : result){
					commentMap.put(c.getEditEventId(), c);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Comment Error");
			}
		};
		async.getCommentList(studentId, exerciseId, callback);
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



}
