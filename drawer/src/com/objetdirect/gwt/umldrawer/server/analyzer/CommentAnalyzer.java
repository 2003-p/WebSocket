package com.objetdirect.gwt.umldrawer.server.analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class CommentAnalyzer {

	public static void main(String[] args) {
		List<StudentInfo> studentInfoList = makeStudentInfoList(13);
		int sum=0;
		int sum2=0;
		int studentNum2=0;
		//		for(StudentInfo si : studentInfoList){
		//			sum+=si.getCommentCount();
		//			if(si.getCommentCount()>1){
		//				System.out.print(studentInfoList.indexOf(si)+",");
		//				System.out.println(si.toString());
		//				sum2+=si.getCommentCount();
		//				studentNum2++;
		//			}
		//		}
		//		System.out.println("合計:"+sum+", 平均:"+sum/studentInfoList.size());
		//		System.out.println("合計:"+sum2+", 平均:"+sum2/studentNum2);

		for(StudentInfo si : studentInfoList){
			if(si.getCommentCount()<2
				|| (!si.getStudent().getStudentId().startsWith("1145") && !si.getStudent().getStudentId().startsWith("c1145") )
					){
				studentInfoList.set(studentInfoList.indexOf(si) , null)	;
			}
		}
		studentInfoList.removeAll(Collections.singleton(null));

		int n = studentInfoList.size();
		double avg = 0;
		double d = 0;
		for(StudentInfo si : studentInfoList){
			sum+=si.getCommentCount();
		}
		//平均を出す
		avg = (double)sum/n;
		System.out.println("Avg: "+avg);
		sum = 0;
		for(StudentInfo si : studentInfoList){
			if(si.getCommentCount() - avg > 0){
				System.out.println(si.getStudent().getStudentId()+", "+si.getCommentCount());
			}

			sum+=Math.pow(si.getCommentCount()-avg, 2);
		}
		d = (double)sum/n;
		System.out.println("標準偏差:"+Math.sqrt(d));
	}

	public static List<StudentInfo> makeStudentInfoList(int exerciseId){
		List<StudentInfo> studentInfoList = new ArrayList<StudentInfo>();
		Dao dao = new Dao();
		List<Student> studentList = dao.getUserListForReplay(exerciseId);
		for(Student s : studentList){
			int c = dao.getCommentList(s.getStudentId(), exerciseId).size();
			StudentInfo st = new StudentInfo(s, c);
			studentInfoList.add(st);
		}

		return studentInfoList;
	}

	static class StudentInfo{
		private Student student;
		private int commentCount;

		public StudentInfo(Student student, int commentCount) {
			super();
			this.student = student;
			this.commentCount = commentCount;
		}
		public Student getStudent() {
			return student;
		}
		public void setStudent(Student student) {
			this.student = student;
		}
		public int getCommentCount() {
			return commentCount;
		}
		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}

		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append(this.student.getStudentId());
			sb.append(",");
			sb.append(this.commentCount);
			return sb.toString();
		}
	}
}
