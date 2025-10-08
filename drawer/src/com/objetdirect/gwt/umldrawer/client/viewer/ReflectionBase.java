/**
 *
 */
package com.objetdirect.gwt.umldrawer.client.viewer;

/**
 * @author tanaka
 *
 */

import static com.objetdirect.gwt.umldrawer.client.DrawerTextResource.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.DrawerPanel;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.client.comment.CommentServiceAsync;
import com.objetdirect.gwt.umldrawer.client.difficulty.SetDifficultyService;
import com.objetdirect.gwt.umldrawer.client.difficulty.SetDifficultyServiceAsync;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

public class ReflectionBase extends DockPanel {
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
	//Labels
	private Label difficultyLabel;

	//difficulty
	private RadioButton difficulty1;
	private RadioButton difficulty2;
	private RadioButton difficulty3;
	private RadioButton difficulty4;

	//Parts
	private DrawerPanel drawerPanel;

	private SingleSelectionModel<EditEvent> selectionModelForEditEventCellList;
	private EditEventCell editEventCell;
	private CellList<EditEvent> editEventCellList;
	private VerticalPanel commentPanel;
	private TextArea commentArea;
	private Button updateCommentButton;
	ScrollPanel eventScrollPanel = new ScrollPanel();



	public ReflectionBase() {
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

		this.setDifficultyInit();

		this.createCommentMap(DrawerSession.student.getStudentId(), Session.exerciseId);

		this.add(leftSideBar, DockPanel.WEST);
		leftSideBar.setSize("310px", "800px");
		this.add(rightSideBar, DockPanel.EAST);
		this.add(northBar, DockPanel.NORTH);
		this.add(southBar, DockPanel.SOUTH);
		this.add(drawerPanel, DockPanel.CENTER);
	}

	private void buttonsInit(){
		//Buttons Init

		this.forward = new Button(FOWERD.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				EditEvent EV = ReflectionBase.this.editEventList.get(ReflectionBase.this.idxOfeditEventList);
				ReflectionBase.this.editEventCellList.getSelectionModel().setSelected(EV, false);
				System.out.println("forward"+ReflectionBase.this.idxOfeditEventList);
				if( ReflectionBase.this.idxOfeditEventList < editEventList.size()-1) {
					ReflectionBase.this.idxOfeditEventList = ReflectionBase.this.idxOfeditEventList+1;
				}
				EV = ReflectionBase.this.editEventList.get(ReflectionBase.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				ReflectionBase.this.drawerPanel.clearCanvas();
				ReflectionBase.this.drawerPanel.fromURL(url, false);
				ReflectionBase.this.editEventCellList.getSelectionModel().setSelected(EV, true);
				switch(EV.getDifficulty()){
				case 1:
					ReflectionBase.this.difficulty1.setValue(true);
					break;
				case 2:
					ReflectionBase.this.difficulty2.setValue(true);
					break;
				case 3:
					ReflectionBase.this.difficulty3.setValue(true);
					break;
				case 4:
					ReflectionBase.this.difficulty4.setValue(true);
					break;
				default:
					System.out.println("Eligal Difficulty");
				}
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
				EditEvent EV = ReflectionBase.this.editEventList.get(ReflectionBase.this.idxOfeditEventList);
				ReflectionBase.this.editEventCellList.getSelectionModel().setSelected(EV, false);
				System.out.println("back"+ReflectionBase.this.idxOfeditEventList);
				if( ReflectionBase.this.idxOfeditEventList > 0) {
					ReflectionBase.this.idxOfeditEventList = ReflectionBase.this.idxOfeditEventList-1;
				}
				EV = ReflectionBase.this.editEventList.get(ReflectionBase.this.idxOfeditEventList);
				String url = EV.getCanvasUrl();
				System.out.println(ReflectionBase.this.idxOfeditEventList);
				ReflectionBase.this.drawerPanel.clearCanvas();
				ReflectionBase.this.drawerPanel.fromURL(url, false);
				ReflectionBase.this.editEventCellList.getSelectionModel().setSelected(EV, true);
				switch(EV.getDifficulty()){
				case 1:
					ReflectionBase.this.difficulty1.setValue(true);
					break;
				case 2:
					ReflectionBase.this.difficulty2.setValue(true);
					break;
				case 3:
					ReflectionBase.this.difficulty3.setValue(true);
					break;
				case 4:
					ReflectionBase.this.difficulty4.setValue(true);
					break;
				default:
					System.out.println("Eligal Difficulty");
				}

				if(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId())!=null){
					commentArea.setText(commentMap.get(editEventList.get(idxOfeditEventList).getEditEventId()).getComment());
				}else{
					commentArea.setText("");
				}
				eventScrollPanel.setVerticalScrollPosition(idxOfeditEventList);
			}
		});
		ReflectionBase.this.leftSideBar.add(forward);
		forward.setSize("88px", "39px");
		ReflectionBase.this.leftSideBar.add(back);
		back.setSize("88px", "39px");
	}

	private void partsInit(){
		this.drawerPanel = new DrawerPanel();
		this.editEventCell = new EditEventCell();
		this.editEventCellList = new CellList<EditEvent>(this.editEventCell);

		this.selectionModelForEditEventCellList = new SingleSelectionModel<EditEvent>();
		this.createEditEventCellList();
		this.loadCanvas();
		this.difficultyLabel = new Label(DIFFICULTY.getMessage());
		this.commentPanel = new VerticalPanel();
		this.commentArea = new TextArea();
		this.commentArea.setSize("300px", "200px");
		this.commentArea.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				commentArea.setCursorPos(commentArea.getText().length());
			}
		});
		this.updateCommentButton = new Button("コメント登録");
		this.updateCommentButton.addClickHandler( new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addComment(editEventList.get(idxOfeditEventList).getEditEventId(), DrawerSession.student.getStudentId(), Session.exerciseId, commentArea.getText()  );
			}
		});
		this.commentPanel.add(commentArea);
		this.commentPanel.add(updateCommentButton);

		this.commentMap = new HashMap<Integer, Comment>();
	}

	@SuppressWarnings("unchecked")
	private void setDifficultyInit(){
		this.difficulty1 = new RadioButton("setDificulty","1");
		this.difficulty2 = new RadioButton("setDificulty","2");
		this.difficulty3 = new RadioButton("setDificulty","3");
		this.difficulty4 = new RadioButton("setDificulty","4");

		final Map<RadioButton,Integer> radioToId = new HashMap<RadioButton,Integer>();
		radioToId.put(difficulty1, 1);
		radioToId.put(difficulty2, 2);
		radioToId.put(difficulty3, 3);
		radioToId.put(difficulty4, 4);

		this.difficulty1.setValue(true);

		ValueChangeHandler<Boolean>  handler= new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue() == true){
					//現在のリストを更新
					ReflectionBase.this.editEventList.get(idxOfeditEventList).setDifficulty(radioToId.get(event.getSource()));

					//TODO 現在選択されているEditEventListの要素<EditEvent>のIDと現在選択されているラジオボタンの番号でsetDifficultyする
					SetDifficultyServiceAsync async = (SetDifficultyServiceAsync)GWT.create(SetDifficultyService.class);
					ServiceDefTarget entryPoint = (ServiceDefTarget) async;
					String entryURL = GWT.getModuleBaseURL() + "setDifficulty";
					entryPoint.setServiceEntryPoint(entryURL);
					@SuppressWarnings("rawtypes")
					AsyncCallback callback = new AsyncCallback(){
						public void onSuccess(Object result){
							System.out.println( result);

						}
						public void onFailure(Throwable caught){
						}
					};
					int difficulty = radioToId.get(event.getSource());
					async.setDifficulty(ReflectionBase.this.editEventList.get(idxOfeditEventList).getEditEventId(), difficulty, callback);
				}
				else {
				}
			}
		};
		this.difficulty1.addValueChangeHandler(handler);
		this.difficulty2.addValueChangeHandler(handler);
		this.difficulty3.addValueChangeHandler(handler);
		this.difficulty4.addValueChangeHandler(handler);

		FlowPanel difficultyPanel = new FlowPanel();
		difficultyPanel.add(this.difficulty1);
		difficultyPanel.add(this.difficulty2);
		difficulty2.setSize("36px", "29px");
		difficultyPanel.add(this.difficulty3);
		difficultyPanel.add(this.difficulty4);
		this.leftSideBar.add(difficultyLabel);
		difficultyLabel.setHeight("16px");
		this.leftSideBar.add(difficultyPanel);
	}

	private void createEditEventCellList() {
		ReflectionBase.this.editEventCellList.setSelectionModel(ReflectionBase.this.selectionModelForEditEventCellList);
		ReflectionBase.this.selectionModelForEditEventCellList.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				ReflectionBase.this.drawerPanel.clearCanvas();
				EditEvent EV = selectionModelForEditEventCellList.getSelectedObject();
				String url = EV.getCanvasUrl();
				ReflectionBase.this.drawerPanel.fromURL(url,false);
				ReflectionBase.this.idxOfeditEventList = ReflectionBase.this.editEventList.indexOf(EV);
				switch(EV.getDifficulty()){
				case 1:
					ReflectionBase.this.difficulty1.setValue(true);
					break;
				case 2:
					ReflectionBase.this.difficulty2.setValue(true);
					break;
				case 3:
					ReflectionBase.this.difficulty3.setValue(true);
					break;
				case 4:
					ReflectionBase.this.difficulty4.setValue(true);
					break;
				default:
					System.out.println("Eligal Difficulty");
				}
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
				ReflectionBase.this.editEventList = (List<EditEvent>) result;

				//ムズイイベントとCanvasLoad
				for(EditEvent ev : ReflectionBase.this.editEventList){
					if(ev.getEventType().equals("Difficult") || ev.getEventType().equals("CanvasLoad") || ev.getEventType().equals("CanvasCloseOrRefresh")){
						ReflectionBase.this.editEventList.remove(ev);

					}
				}
				ReflectionBase.this.drawerPanel.clearCanvas();
				ReflectionBase.this.drawerPanel.fromURL(ReflectionBase.this.editEventList.get(ReflectionBase.this.editEventList.size()-1).getCanvasUrl(),false);
				ReflectionBase.this.idxOfeditEventList = ReflectionBase.this.editEventList.size()-1;
				ReflectionBase.this.editEventCellList.setRowData( (List<EditEvent>)result);

				ReflectionBase.this.leftSideBar.add(eventScrollPanel);
				eventScrollPanel.add(ReflectionBase.this.editEventCellList);
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
		AsyncCallback<Comment> callback = new AsyncCallback<Comment>(){
			public void onSuccess(Comment result){
				if(result==null){
					Window.alert("Connection Error!");
				}
				else{
					Comment newComment = result;
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
