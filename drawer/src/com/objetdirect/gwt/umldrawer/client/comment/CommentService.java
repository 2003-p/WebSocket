package com.objetdirect.gwt.umldrawer.client.comment;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;

public interface CommentService extends RemoteService{
	public Comment addComment(int editEventId, String studentId, int exerciseId, String comment);
	public List<Comment> getCommentList( String studentId, int exerciseId);
}
