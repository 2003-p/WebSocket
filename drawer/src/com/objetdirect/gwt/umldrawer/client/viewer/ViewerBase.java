/**
 *
 */
package com.objetdirect.gwt.umldrawer.client.viewer;

/**
 * @author tanaka
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.CanvasUrlManager;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLDiagram;
import com.objetdirect.gwt.umldrawer.client.DrawerPanel;
//import com.objetdirect.gwt.umldrawer.client.analyzer.GraphTestPanel;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.canvas.CanvasService;
import com.objetdirect.gwt.umldrawer.client.canvas.CanvasServiceAsync;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.client.comment.CommentServiceAsync;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;
import com.objetdirect.gwt.umldrawer.client.user.GetUserService;
import com.objetdirect.gwt.umldrawer.client.user.GetUserServiceAsync;

public class ViewerBase extends DockPanel {
	private VerticalPanel leftSideBar;
	private VerticalPanel rightSideBar;
	private HorizontalPanel northBar;
	private HorizontalPanel southBar;

	//Buttons
	private List<EditEvent> editEventList;
	private int	idxOfeditEventList;
	private Button forward;
	private Button fastForward;
	private Button back;
	private Button fastBack;

	//Parts
	private SingleSelectionModel<String> selectionModelForUserCellList;
	private ButtonCell userCell	;
	private CellList<String> userCellList;
	private ScrollPanel forUserCellList = new ScrollPanel();
	private DrawerPanel drawerPanel;
	private TextBox difficultyBox;
	private TextBox similarityBox;
	private HashMap<Integer, Comment> commentMap;

	private SingleSelectionModel<EditEvent> selectionModelForEditEventCellList;
	private EditEventCell editEventCell;
	private CellList<EditEvent> editEventCellList;
	private ScrollPanel forEventCellList = new ScrollPanel();
	private TextArea commentArea;
	private Button updateCommentButton;

	private List<UMLArtifact> answer;
	//For DEMO
	private Map<String, String> maskedId;

	public ViewerBase() {
		super();
		this.leftSideBar = new VerticalPanel();
		this.rightSideBar = new VerticalPanel();
		this.northBar = new HorizontalPanel();
		this.southBar = new HorizontalPanel();

		//*********************************************************************************************************
		//Param Init
		this.editEventList = new ArrayList<EditEvent>();
		this.idxOfeditEventList = -1;

		//Parts Init
		this.partsInit();

		//ButtonInit
		this.buttonsInit();


		leftSideBar.setWidth("160px");
		this.add(leftSideBar, DockPanel.WEST);
		this.add(northBar, DockPanel.NORTH);
		this.add(drawerPanel, DockPanel.CENTER);
		rightSideBar.setWidth("230px");
		this.add(rightSideBar, DockPanel.EAST);

		this.add(southBar, DockPanel.SOUTH);

	}

	private void buttonsInit(){
		//Buttons Init

		this.forward = new Button("foward", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("forward"+ViewerBase.this.idxOfeditEventList);
				if( ViewerBase.this.idxOfeditEventList < editEventList.size()-1) {
					ViewerBase.this.idxOfeditEventList = ViewerBase.this.idxOfeditEventList+1;
				}
				//Window.alert("No."+ViewerBase.this.idxOfeditEventList);
				//forEventCellList.setVerticalScrollPosition(idxOfeditEventList);
				EditEvent EV = ViewerBase.this.editEventList.get(ViewerBase.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				setDifficultyPanel(EV.getDifficulty());
				selectionModelForEditEventCellList.setSelected(EV, true);
				ViewerBase.this.drawerPanel.clearCanvas();
				ViewerBase.this.drawerPanel.fromURL(url, false);
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}
		});

		this.fastForward = new Button("foward>>", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEvent EV = ViewerBase.this.editEventList.get(ViewerBase.this.idxOfeditEventList);

				if( ViewerBase.this.idxOfeditEventList < editEventList.size()-1) {
					ViewerBase.this.idxOfeditEventList = ViewerBase.this.idxOfeditEventList+1;
					EV = editEventList.get(idxOfeditEventList);
					while( idxOfeditEventList < editEventList.size()-1 && !EV.getEventType().equals("Edit")){
						EV = editEventList.get(idxOfeditEventList);
						idxOfeditEventList = ViewerBase.this.idxOfeditEventList+1;
					}
				}

				String url = EV.getCanvasUrl();
				setDifficultyPanel(EV.getDifficulty());
				selectionModelForEditEventCellList.setSelected(EV, true);
				ViewerBase.this.drawerPanel.clearCanvas();
				ViewerBase.this.drawerPanel.fromURL(url, false);
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}
		});


		this.back = new Button("back", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if( ViewerBase.this.idxOfeditEventList > 0) {
					ViewerBase.this.idxOfeditEventList = ViewerBase.this.idxOfeditEventList-1;
				}
				EditEvent EV = ViewerBase.this.editEventList.get(ViewerBase.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				setDifficultyPanel(EV.getDifficulty());
				selectionModelForEditEventCellList.setSelected(EV, true);
				System.out.println(ViewerBase.this.idxOfeditEventList);
				ViewerBase.this.drawerPanel.clearCanvas();
				ViewerBase.this.drawerPanel.fromURL(url, false);
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}
		});

		this.fastBack = new Button("back<<", new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEvent EV = ViewerBase.this.editEventList.get(ViewerBase.this.idxOfeditEventList);

				if( ViewerBase.this.idxOfeditEventList > 0) {
					ViewerBase.this.idxOfeditEventList = ViewerBase.this.idxOfeditEventList-1;
					EV = editEventList.get(idxOfeditEventList);
					while( idxOfeditEventList > 0 && !EV.getEventType().equals("Edit")){
						EV = editEventList.get(idxOfeditEventList);
						idxOfeditEventList = ViewerBase.this.idxOfeditEventList-1;
					}

				}
				String url = EV.getCanvasUrl();
				setDifficultyPanel(EV.getDifficulty());
				selectionModelForEditEventCellList.setSelected(EV, true);
				System.out.println(ViewerBase.this.idxOfeditEventList);
				ViewerBase.this.drawerPanel.clearCanvas();
				ViewerBase.this.drawerPanel.fromURL(url, false);
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}
		});

		this.updateCommentButton = new Button("コメント登録");
		this.updateCommentButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addComment(editEventList.get(idxOfeditEventList).getEditEventId(), DrawerSession.student.getStudentId(), Session.exerciseId, commentArea.getText()  );
			}
		});


		this.difficultyBox = new TextBox();
		this.similarityBox = new TextBox();
		ViewerBase.this.leftSideBar.add(forward);
		ViewerBase.this.leftSideBar.add(back);
		ViewerBase.this.leftSideBar.add(fastForward);
		ViewerBase.this.leftSideBar.add(fastBack);
		ViewerBase.this.leftSideBar.add(new Label("Difficulty"));
		ViewerBase.this.leftSideBar.add(difficultyBox);
		ViewerBase.this.leftSideBar.add(new Label("Similarity"));
		ViewerBase.this.leftSideBar.add(similarityBox);
		ViewerBase.this.leftSideBar.add(updateCommentButton);
		difficultyBox.setWidth("37px");
		similarityBox.setWidth("70px");
	}

	private void partsInit(){
		this.drawerPanel = new DrawerPanel(400,50);
		this.selectionModelForUserCellList = new SingleSelectionModel<String>();
		this.userCell	 = new ButtonCell();
		this.userCellList = new CellList<String>(this.userCell);
		this.createUserCellList();
		this.editEventCell = new EditEventCell();
		this.editEventCellList = new CellList<EditEvent>(this.editEventCell);
		this.selectionModelForEditEventCellList = new SingleSelectionModel<EditEvent>();
		this.createEditEventCellList();

		this.getAnswer(Session.exerciseId);
		this.commentArea = new TextArea();
	}


	private void getAnswer(int exerciseId) {
		CanvasServiceAsync async = (CanvasServiceAsync)GWT.create(CanvasService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getAnswer";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			public void onSuccess(Object result){
				CanvasUrlManager cum = new CanvasUrlManager();
				answer = cum.fromURL( (String)result, false);
			}
			public void onFailure(Throwable caught){
				Window.alert( caught.toString());
			}
		};
		async.getAnswer( exerciseId, callback);

	}

	private void createEditEventCellList() {
		ViewerBase.this.editEventCellList.setSelectionModel(ViewerBase.this.selectionModelForEditEventCellList);
		ViewerBase.this.selectionModelForEditEventCellList.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				ViewerBase.this.drawerPanel.clearCanvas();
				EditEvent EV = selectionModelForEditEventCellList.getSelectedObject();
				String url = EV.getCanvasUrl();
				setDifficultyPanel(EV.getDifficulty());
				ViewerBase.this.idxOfeditEventList = ViewerBase.this.editEventList.indexOf(EV);
				ViewerBase.this.drawerPanel.fromURL(url,false);
				CanvasUrlManager cum = new CanvasUrlManager();
				SimilarityManager similarityManager = new SimilarityManager();
				if(answer != null){
					ViewerBase.this.similarityBox.setText( similarityManager.getClassSimilarity(cum.fromURL(url, false), answer)+"");
				}
				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
			}

		});
	}

	private void createUserCellList(){
		GetUserServiceAsync async = (GetUserServiceAsync)GWT.create(GetUserService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getUserList";
		entryPoint.setServiceEntryPoint(entryURL);

		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){

			@SuppressWarnings({ "unchecked" })
			public void onSuccess(Object result){
				ViewerBase.this.userCellList.setSelectionModel(ViewerBase.this.selectionModelForUserCellList);
				ViewerBase.this.selectionModelForUserCellList.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						LogViewerServiceAsync async = (LogViewerServiceAsync)GWT.create(LogViewerService.class);
						ServiceDefTarget entryPoint = (ServiceDefTarget) async;
						String entryURL = GWT.getModuleBaseURL() + "getEditEventListForReplay";
						entryPoint.setServiceEntryPoint(entryURL);
						@SuppressWarnings("rawtypes")
						AsyncCallback callback = new AsyncCallback(){
							public void onSuccess(Object result){
								System.out.println((List<EditEvent>) result);
								//GraphTest
								if(Session.diagramType == UMLDiagram.Type.CLASS){
									SimilarityManager sm = new SimilarityManager();
									ViewerBase.this.southBar.clear();
									if(answer != null){
										//ViewerBase.this.southBar.add(new GraphTestPanel(sm.createDataForGraph( (List<EditEvent>) result, answer), ViewerBase.this));
									}
								}
								ViewerBase.this.editEventList = (List<EditEvent>) result;
								ViewerBase.this.drawerPanel.clearCanvas();

								//
								int idx = getLastSubmitEventIdx(editEventList);
								ViewerBase.this.drawerPanel.fromURL(ViewerBase.this.editEventList.get(idx).getCanvasUrl(),false);
								ViewerBase.this.idxOfeditEventList = idx;
								ViewerBase.this.editEventCellList.setRowData( (List<EditEvent>)result);
								forEventCellList.clear();
								forEventCellList.add(editEventCellList);
								forEventCellList.setSize("230px", drawerPanel.getHeight()+"px");
								ViewerBase.this.rightSideBar.add(forEventCellList);


							}
							public void onFailure(Throwable caught){
							}
						};
						async.getEditEventListForReplay( selectionModelForUserCellList.getSelectedObject() , Session.exerciseId, callback);
						createCommentMap(selectionModelForUserCellList.getSelectedObject(), Session.exerciseId);
					}
				});
				//通常
				ViewerBase.this.userCellList.setRowData( (List<String>)result);
				//デモ用
				forUserCellList.add(userCellList);
				forUserCellList.setSize("100px", drawerPanel.getHeight()/2+"px");
				ViewerBase.this.leftSideBar.add(forUserCellList);
				ViewerBase.this.leftSideBar.add(commentArea);
				commentArea.setPixelSize(150, 200);
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getUserList(callback);
	}

	private int getLastSubmitEventIdx(List<EditEvent> eventList){
		int idx = 0;
		for( idx = eventList.size()-1 ;  idx >= 0 ; idx--){
			if( "Submit".equals( eventList.get(idx).getEventType() )){
				return idx;
			}
		}
		return eventList.size()-1;

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

	public void setDifficultyPanel(int dificulty){
		this.difficultyBox.setText(dificulty+"");
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
