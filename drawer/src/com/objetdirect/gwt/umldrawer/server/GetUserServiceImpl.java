package com.objetdirect.gwt.umldrawer.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.client.user.GetUserService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class GetUserServiceImpl extends RemoteServiceServlet implements GetUserService{

	@Override
	public List<String> getUserList() {
		List<String> userList = new ArrayList<String>();

		Dao dao = new Dao();
		userList = dao.getUserList();

		return userList;
	}

	@Override
	public List<Student> getUserListForReplay(int exerciseId) {
		List<Student> userList = new ArrayList<Student>();

		Dao dao = new Dao();
		userList = dao.getUserListForReplay(exerciseId);

		return userList;
	}

}
