package com.objetdirect.gwt.umldrawer.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.Comment;
import com.objetdirect.gwt.umldrawer.client.comment.CommentService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class CommentServiceImpl extends RemoteServiceServlet implements CommentService{

	@Override
	public Comment addComment(int editEventId, String studentId, int exerciseId, String comment) {
		Dao dao = new Dao();
		return dao.addComment(editEventId, studentId, exerciseId, comment);
	}

	@Override
	public List<Comment> getCommentList(String studentId, int exerciseId) {
		Dao dao = new Dao();
		return dao.getCommentList(studentId, exerciseId);
	}

}
