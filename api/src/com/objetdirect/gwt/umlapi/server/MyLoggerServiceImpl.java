package com.objetdirect.gwt.umlapi.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerService;
import com.objetdirect.gwt.umlapi.server.dao.Dao;





public class MyLoggerServiceImpl extends RemoteServiceServlet implements MyLoggerService{

	public MyLoggerServiceImpl(){
	}

//	public void registEditEvent(String logData, String canvasUrl, String step) {
//		//System.out.println("start operationLog...");
//
//		Dao dao = new Dao();
//		HttpServletRequest request = getThreadLocalRequest();
//		HttpSession session = request.getSession(false);
//		String studentId = (String)session.getAttribute("studentId");
//		int exercisesId = (Integer) session.getAttribute("exercisesId");
//		int defaultDifficulty = 1;
//		if(dao.registEditEvent(studentId, exercisesId, logData, defaultDifficulty, canvasUrl, step)){
//			System.out.println("DB is OK");
//		}else {
//			System.out.println("DB is NG");
//		}
//	}

	public boolean registEditEvent(String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
												String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
												String targetPart, String beforeEdit, String afterEdit, String canvasUrl, int umlArtifactId) {
		Dao dao = new Dao();

		int defaultDifficulty = 1;
		int canvasId=1;

//		public boolean registEditEvent(String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
//				  String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
//				  String targetPart, String beforeEdit, String afterEdit, int canvasId, String canvasUrl,
//				  int defaultDifficulty)

		if( dao.registEditEvent( studentId,  exercisesId, preEventId, editEvent, eventType,
											  targetType, targetId,  linkKind,  rightObjectId, leftObjectId,
											  targetPart,  beforeEdit, afterEdit, canvasId, canvasUrl,
											  defaultDifficulty, umlArtifactId) ){
			System.out.println("DB is OK");
			return true;
		}else {
			System.out.println("DB is NG");
			return false;
		}
	}
}

		/*
		try{
			File file = new File("operationLog.txt");

			try{
			    file.createNewFile();
			}catch(IOException e){
			    System.out.println(e);
			}

			if(checkBeforeWritefile(file)){
			//if (true){
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

				pw.println(logData);
				pw.close();

			}else{
				System.out.println("operationLog.txtに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
		*/

	/*
	public void canvasLog(String canvasToUrl){
		System.out.println("canvasLog...");
		try{
			File file = new File("canvasLog.txt");
			try{
			    file.createNewFile();
			}catch(IOException e){
			    System.out.println(e);
			}

			if (checkBeforeWritefile(file)){
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));

				pw.println(canvasToUrl);
				pw.close();
//TODO
				Dao dao = Dao.getDao();

			}else{
				System.out.println("canvasLog.txtに書き込めません");
			}
		}catch(IOException e){
			System.out.println(e);
		}
	}

private static boolean checkBeforeWritefile(File file){
	if (file.exists()){
		if (file.isFile() && file.canWrite()){
			return true;
		}
	}

	return false;
}
*/




