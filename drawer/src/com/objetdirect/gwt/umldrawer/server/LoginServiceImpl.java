package com.objetdirect.gwt.umldrawer.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.LoginService;
import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.client.helpers.Browser;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;
import com.objetdirect.gwt.umldrawer.client.helpers.PasswordEncryption;
//import com.objetdirect.gwt.umlapi.server.dao.Dao;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

	public LoginServiceImpl(){

	}

	public Student login(String studentId, String password){

		System.out.println("LoginServiceImpl");
		Dao dao = new Dao();
		Student student = dao.getStudent(studentId);
		password = PasswordEncryption.getPassword_encryption(password);

		HttpServletRequest r = getThreadLocalRequest();
		String browser = Browser.getBrowser(r);
		System.out.println("Brower" + browser);

		if(student == null || !student.getPassword().equals(password)){
			System.out.println("failured to login" );
			return null;
		}else if( student.getPassword().equals(password)){
			System.out.println("successed to login" );
			HttpServletRequest request = getThreadLocalRequest();
			HttpSession session = request.getSession(true);
			session.setAttribute("studentId", student.getStudentId());
			session.setAttribute("exercisesId", 1);
			Session.exerciseId=1; //TODO FIXME
			DrawerSession.student = student;
			//Cookies.setCookie("studentId", student.getStudentId());
			System.out.println("session of " + session.getAttribute("studentId")+":"+session.getAttribute("exercisesId"));
			System.out.println("sessionId:"+session.getId());
			return student;
		}else return null;

	}
}
