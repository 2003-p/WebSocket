package com.objetdirect.gwt.umlapi.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.objetdirect.gwt.umlapi.server.yamazaki.thread.ThreadAcceptfromUMLDS;




/**
 * @author J10-8011
 *
 */
public class Dao extends DriverAccessor{

	private Connection connection;
	
	public static final String UTF_8 = "UTF-8";
	public static final String MS932 = "MS932";
	public String ClassDiagramURL = new String();

	public Dao(){
		final ThreadAcceptfromUMLDS threadUMLDS_ = ThreadAcceptfromUMLDS.getInstance();
	}

//	student_id varchar(32),
//	exercise_id int,
//	pre_event_id int,
//	edit_event text,
//	event_type text,
//	target_type text,
//	target_id int,
//	linkkind text,
//	right_object_id int,
//	left_object_id int,
//	target_part text,
//	before_edit text,
//	after_edit text,
//	canvas_id int,
//	canvas_url text,
//	difficulty int,
//	edit_datetime datetime);

	public boolean registEditEvent(String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
												  String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
												  String targetPart, String beforeEdit, String afterEdit, int canvasId, String canvasUrl,
												  int defaultDifficulty, int umlArtifactId) {
		System.err.println("registEditEvent");
		 this.connection= this.createConnection();
		try{

			String sql = "insert into edit_event ( student_id ,exercises_id, pre_event_id, edit_event, event_type, target_type, target_id, linkkind, right_object_id, left_object_id, target_part, before_edit, after_edit, canvas_id, canvas_url, difficulty, edit_datetime, umlartifact_id )values(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,   ?, ?, ?, ?, ?,  ?, now(), ?)";
//			student_id ,exercises_id, pre_event_id, edit_event, event_type, target_type, target_id, linkkind, right_object_id, left_object_id, target_part, before_edit, after_edit, canvas_id, canvas_url, difficulty, edit_datetime datetime);

			PreparedStatement stmt = this.connection.prepareStatement(sql);

			stmt.setString(1, studentId);
			stmt.setInt(2, exercisesId);
			stmt.setInt(3,  preEventId);
			stmt.setString(4, editEvent);
			stmt.setString(5, eventType);

			stmt.setString(6, targetType);
			stmt.setInt(7, targetId);
			stmt.setString(8, linkKind);
			stmt.setInt(9, rightObjectId);
			stmt.setInt(10, leftObjectId);

			stmt.setString(11, targetPart);
			stmt.setString(12, beforeEdit);
			stmt.setString(13, afterEdit);
			stmt.setInt(14, canvasId);
			stmt.setString(15, canvasUrl);

			stmt.setInt(16, defaultDifficulty);
			stmt.setInt(17, umlArtifactId);

			stmt.executeUpdate();

		}catch(SQLException e){
			this.closeConnection(connection);
			e.printStackTrace();
			return false;

		} finally {
		}
		this.closeConnection(connection);
		return true;
	}
	
	
}



