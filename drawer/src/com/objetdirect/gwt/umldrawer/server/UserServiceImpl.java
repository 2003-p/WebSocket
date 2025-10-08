package com.objetdirect.gwt.umldrawer.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.helpers.PasswordEncryption;
import com.objetdirect.gwt.umldrawer.client.user.UserService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class UserServiceImpl extends RemoteServiceServlet implements UserService{
	@Override
	public Boolean addUser(String userId){
		Dao dao = new Dao();
		if(dao.getUserList().contains(userId) ){
			return false;
		}

		dao.addUser(userId, PasswordEncryption.getPassword_encryption(userId) );
		dao.prepareExperimentalData(userId);
		return true;
	}
//	@Override
//	public void changePassword(String studentId, String password) {
//		Dao dao = new Dao();
//		dao.changePassword(studentId, password);
//
//	}
	@Override
	public void changePassword(String password) {
		System.out.println("PasswordCangeImpl");
		Dao dao = new Dao();
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		String studentId = DrawerSession.student.getStudentId();
		dao.changePassword(studentId, PasswordEncryption.getPassword_encryption(password));
	}
	@Override
	public void changePassword(String nowPassword, String newPassword) {
		System.out.println("PasswordCangeImpl");
		Dao dao = new Dao();
		HttpServletRequest request = getThreadLocalRequest();
		HttpSession session = request.getSession(false);
		//String studentId = (String)(session.getAttribute("studentId"));
		System.out.println(DrawerSession.student);
		String studentId = (String)session.getAttribute("studentId");
		Student student = dao.getStudent(studentId);
		nowPassword = PasswordEncryption.getPassword_encryption(nowPassword);
		newPassword = PasswordEncryption.getPassword_encryption(newPassword);
		if(student.getPassword().equals(nowPassword)){
			dao.changePassword(studentId, newPassword);
		}

	}
	@Override
	public void setAdmin(String text, boolean isAdmin) {
		Dao dao = new Dao();
		dao.setAdmin(text, isAdmin);
	}

	@Override
	public void initializePassword(String studentId) {
		Dao dao = new Dao();
		dao.changePassword(studentId, PasswordEncryption.getPassword_encryption(studentId));
	}

}
