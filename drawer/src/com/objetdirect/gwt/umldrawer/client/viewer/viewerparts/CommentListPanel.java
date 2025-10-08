package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.client.comment.CommentServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerForStudentBase;

public class CommentListPanel extends ScrollPanel{
	private List<Comment> commentList;
	private VerticalPanel verticalPanel;
	private int width;
	private int height;
	private ViewerForStudentBase viewerForStudentBase;
	private int panelHeight = 200;
	private int panelSpacing = 2;

	public CommentListPanel(ViewerForStudentBase viewerForStudentBase, String studentId, int exerciseId, int width, int height) {
		super();
		this.viewerForStudentBase = viewerForStudentBase;
		this.width = width;
		this.height = height;
		this.setSize(Integer.toString(this.width), Integer.toString(this.height));
		this.verticalPanel = new VerticalPanel();
		this.verticalPanel.setSize(Integer.toString(this.width-20), Integer.toString(this.height));
		this.verticalPanel.setSpacing(2);
		this.add(this.verticalPanel);
		getCommentList(studentId, exerciseId);
	}

	private void getCommentList(String studentId, int exerciseId){
		CommentServiceAsync async = (CommentServiceAsync)GWT.create(CommentService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getCommentList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<Comment>> callback = new AsyncCallback<List<Comment>>(){
			public void onSuccess(List<Comment> result){
				commentList = result;
				for(Comment c : commentList){
					CommentPanel cp = new CommentPanel(CommentListPanel.this, c, width-25,  panelHeight);
					verticalPanel.add( cp );
				}
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getCommentList(studentId, exerciseId, callback );
	}


	public CommentPanel addComment(Comment Comment){
		CommentPanel cp = new CommentPanel(this, Comment, this.width-25, panelHeight);
		this.commentList.add(Comment);
		System.out.println("@addComment"+Comment.getCommentId());
		this.verticalPanel.add( cp );
		//Call after add CommentPanel to the parent
		return cp;
	}
	public CommentPanel addComment(int index, Comment Comment){
		CommentPanel cp = new CommentPanel(this, Comment, this.width-25, panelHeight);
		this.commentList.add(index, Comment);
		if(index <= 0){
			this.verticalPanel.add( cp );
		}else{
			this.verticalPanel.insert( cp, index - 1);
		}
		//Call after add CommentPanel to the parent
		return cp;
	}
	public void removeComment(int index){
		this.commentList.remove(index);
		CommentPanel cp = (CommentPanel)(this.verticalPanel.getWidget(index) );
		this.verticalPanel.remove(index);
	}
	public CommentPanel getCommentPanel(int index){
		return (CommentPanel) this.verticalPanel.getWidget(index);
	}
	public Comment getComment(int index){
		return this.commentList.get(index);
	}
	public void setComment(int index, Comment comment){
		this.commentList.set(index, comment);
		CommentPanel cp = new CommentPanel(this, comment, this.width-25, panelHeight);
		this.verticalPanel.insert(cp , index);
		//Call after add CommentPanel to the parent
		cp = (CommentPanel)(this.verticalPanel.getWidget(index) );
		this.verticalPanel.remove(index);

	}
	public int size(){
		return this.commentList.size();
	}

	public void selectionChange(int selectedIndex, boolean withScroll){
		for(int i = 0 ; i < verticalPanel.getWidgetCount() ; i++){
			if( i == selectedIndex ) continue;
			CommentPanel cp = (CommentPanel) verticalPanel.getWidget(i);
			cp.deselect();
		}
		if(withScroll){
			if(selectedIndex == 0) this.setVerticalScrollPosition(0);
			else
				this.setVerticalScrollPosition(selectedIndex*(panelHeight+panelSpacing));
		}
	}

	public int indexOfCommentId(int CommentId){
		System.out.println("@indexOfCommentId CommentList.size="+this.commentList.size()+" CommentId="+CommentId);
		for (Comment c : this.commentList){
			System.out.println(c.getCommentId());
			if (c.getCommentId()==CommentId) return  this.commentList.indexOf(c);
		}
		return -1;
	}


	public CommentPanel getCommentPanelByComment(Comment c){
		CommentPanel cp;
		int indexOfC = this.commentList.indexOf(c);
		cp = (CommentPanel) this.verticalPanel.getWidget(indexOfC);
		return cp;
	}

	public boolean isExistCommentForEvent(EditEvent e){
		boolean exist= false;
		for( Comment c : this.commentList ){
			if(c.getEditEventId() == e.getEditEventId()){
				exist = true;
			}
		}
		return exist;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> CommentList) {
		this.commentList = CommentList;
	}

	public ViewerForStudentBase getViewerForStudentBase() {
		return viewerForStudentBase;
	}

	public void setViewerForStudentBase(ViewerForStudentBase viewerForStudentBase) {
		this.viewerForStudentBase = viewerForStudentBase;
	}

	public void selectByEventId(int eventId, boolean withScroll, boolean withSelectEvent) {
		for(Comment c : this.commentList){
			if(c.getEditEventId() == eventId){
				this.getCommentPanelByComment(c).select(withScroll, withSelectEvent);
				return;
			}
		}
		this.selectionChange(-1, false);

	}
}
