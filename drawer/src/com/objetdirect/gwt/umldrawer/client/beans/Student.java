package com.objetdirect.gwt.umldrawer.client.beans;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author J10-8011
 *
 */

public class Student implements IsSerializable{
	private String studentId;
	private String password;
	private int type;

	public Student(){

	}
	/**
	 * @return studentId
	 */

	public String getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId セットする studentId
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password セットする password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
