package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.client.comment.CommentServiceAsync;

public class CommentPanel extends FocusPanel{
	private CommentListPanel commentListPanel;
	private VerticalPanel base;

	private int width;
	private int heignt;
	private boolean isSelected;
	private Comment comment;
	int index;

	private String DEFAULT_BG_COLOR = "#88ff88";
	private String SELECTED_BG_COLOR = "#88ffff";

	public CommentPanel(){
	}

	public CommentPanel(CommentListPanel commentListPanel, Comment comment, int width, int heignt) {
		super();
		this.commentListPanel = commentListPanel;
		this.base = new VerticalPanel();
		this.add(base);
		this.width = width;
		this.heignt = heignt;
		this.isSelected = false;
		this.comment = comment;
		this.setPixelSize(this.width, this.heignt);
		base.setPixelSize(this.width, this.heignt);

		base.getElement().getStyle().setBackgroundColor(DEFAULT_BG_COLOR);

		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(isSelected) {
					deselect();
				}
				else {
					select(false, true);
				}
			}
		});

		updateCommentPanel(CommentPanel.this.comment);
	}

	public void select(boolean withScroll, boolean withSelectEvent) {
		commentListPanel.selectionChange( this.commentListPanel.getCommentList().indexOf(this.comment), withScroll );
		EventListPanel elp = this.commentListPanel.getViewerForStudentBase().getEventListPanel();
		if(withSelectEvent){
			elp.setScrollWithSelect(false);
			elp.getEventPanelByEditEventId( this.comment.getEditEventId() ).select(true);
		}
		base.getElement().getStyle().setBackgroundColor(SELECTED_BG_COLOR);
		this.isSelected = true;

	}

	public void deselect(){
		base.getElement().getStyle().setBackgroundColor(DEFAULT_BG_COLOR);
		this.isSelected = false;

	}
	public boolean isSeledted(){
		return this.isSelected;
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

	private void updateCommentPanel(Comment comment){

		this.comment = comment;
		base.clear();
		base.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		index = commentListPanel.indexOfCommentId(comment.getCommentId());
		Label indexLabel = new Label(Integer.toString(index));
		base.add(indexLabel);
		Label bodyLabel = new Label(this.comment.getComment());
		base.add(bodyLabel);

	}

	public void updateByNewComment(Comment comment){
		comment.setStudentId(Session.studentId);
		comment.setExerciseId(Session.exerciseId);
		CommentServiceAsync async = (CommentServiceAsync)GWT.create(CommentService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "addComment";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<Comment> callback = new AsyncCallback<Comment>(){
			public void onSuccess(Comment result){
				int idx = commentListPanel.indexOfCommentId( CommentPanel.this.comment.getCommentId() );
				System.out.println("CommentPanel.this.comment.getCommentId() "+CommentPanel.this.comment.getCommentId()+"; "+idx);
				updateCommentPanel(result);
				if(idx >= 0){
					commentListPanel.setComment(idx, result);
					System.out.println("@updateByNewComment if"+result.getCommentId());
				}
				else{
					commentListPanel.addComment(result);
					System.out.println("@updateByNewComment else"+result.getCommentId());
				}

				System.out.println("updateBy "+result.getCommentId());
			}
			public void onFailure(Throwable caught){
			}
		};
		System.out.println("@beforSendComment "+comment.getCommentId());
		async.addComment(comment.getEditEventId(), comment.getStudentId(), comment.getExerciseId(), comment.getComment(), callback );

	}

	class TargetButton extends Button{
		private int id;

		public TargetButton(int id, String string) {
			super(string);
			setId(id);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

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

	public CommentListPanel getCommentListPanel() {
		return commentListPanel;
	}

	public void setCommentListPanel(CommentListPanel parent) {
		this.commentListPanel = parent;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public VerticalPanel getBase() {
		return base;
	}

	public void setBase(VerticalPanel base) {
		this.base = base;
	}



}
