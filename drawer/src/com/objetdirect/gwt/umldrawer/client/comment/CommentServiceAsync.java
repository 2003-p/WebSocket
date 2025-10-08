package com.objetdirect.gwt.umldrawer.client.comment;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;

public interface CommentServiceAsync {
	public void addComment(int editEventId, String studentId, int exerciseId, String comment, AsyncCallback<Comment> async);
	//@gwt.typeArgs <com.objetdirect.gwt.umldrawer.client.beans.Comment[]>
	public void getCommentList( String studentId, int exerciseId, AsyncCallback<List<Comment>> async);
}
