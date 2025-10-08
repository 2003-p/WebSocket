package com.objetdirect.gwt.umldrawer.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.InstantiationRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LifeLineArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkClassRelationArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkNoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.MessageLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.NoteArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ObjectRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.engine.Point;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLifeLine;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLLink.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObjectAttribute;
import com.objetdirect.gwt.umldrawer.client.analyzer.AnalysisService;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class AnalysisServiceImpl extends RemoteServiceServlet implements AnalysisService{

	public AnalysisServiceImpl(){

	}

	@Override
	public boolean analyze(String studentId, int exerciseId) {
		Map<String, Integer>  DAVNum = new  HashMap<String , Integer>();

		//individualAnalysis(studentId, exerciseId);
		Dao dao = new Dao();
		List<String> studentIdList = new ArrayList<String>();
		makeStudentList(studentIdList, DAVNum );

		System.out.print("No.,");
		System.out.print("学生ID,");
		System.out.print("Edit数,");
		System.out.print("クラス名または属性に対する上書きEdit数,");
		System.out.print("Place数,");
		System.out.print("総編集時間(秒)");
		System.out.println();
		int i=0;
		for( String id : studentIdList ){
			List<EditEvent> data = null;
			try {
				data = dao.getAllEditEventListDecoded(id, exerciseId);
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			//有効データだけを抽出
			for( EditEvent ev : data){
				if(ev!=null && (ev.getEditEventId() < DAVNum.get(id)) ){
					ev = null;
				}
			}
			data.removeAll(Collections.singleton(null));
//			//editTargetRetioByEventNum(id, 1);
//				try {
//					trialAndErrorNum(id,1);
//				} catch (UnsupportedEncodingException e) {
//					// TODO 自動生成された catch ブロック
//					e.printStackTrace();
//				}


			//Editのイベント数
			int editEventNum = 0;
			for(EditEvent ev : data){
				if(ev.getEventType().equals("Edit"))
					editEventNum++;
			}

			//クラス名または属性に対する上書きEdit数（NULLにする＝削除 も含む）
			double updateEditEventNum = 0;
			for(EditEvent ev : data){
				if( ev.getEventType().equals("Edit") &&
						( ev.getEditEvent().equals("ClassName") || ev.getEditEvent().equals("Attribute"))&&
						!(ev.getBeforeEdit().equals("#attribute : String") || ev.getBeforeEdit().startsWith("Class")) )
					updateEditEventNum++;

				if( ev.getEventType().equals("Edit") )
					updateEditEventNum++;
			}

			//LastEventを探す
			int classNum = 0;
			EditEvent lastEvent = null;
			for(int j = data.size()-1 ; j >= 0 ; j--){
				if(data.get(j).getCanvasUrl()!=null){
					lastEvent = data.get(j);
					break;
				}
			}
			if(lastEvent==null) continue;

			String canvas = lastEvent.getCanvasUrl();
			List<UMLArtifact> artifactList = fromURL(canvas);

//			for(UMLArtifact artifact : artifactList){
//				if( artifact!=null ){
//					Class<? extends UMLArtifact> artifactClass = artifact.getClass();
//					String artifactClassName = artifactClass.getSimpleName();
//
//					//print(artifactClassName);
//					if(artifactClassName.equals("ClassArtifact")){
//						classNum++;
//					}
//				}
//			}
			updateEditEventNum/=artifactList.size();


			//Placeのイベント数
			int PlaceEventNum = 0;
			for(EditEvent ev : data){
				if(ev.getEditEvent()==null){
				}
				else{
					if(ev.getEditEvent().equals("PlaceArtifacts"))
						PlaceEventNum++;
				}
			}

			//EditTime
			double editTime = 0;
			if(data.size()!=0){

				EditEvent firstEvent;
				//EditEvent lastEvent;
				int j=0;
				while(data.get(j).getEditEvent()==null){
					j++;
					if( j > data.size()-1 ){
						j=0;
						break;
					}
				}
				firstEvent = data.get(j);

				j=data.size()-1;
				while(data.get(j).getEditEvent()==null){
					j--;
					if( j < 0 ){
						j=0;
						break;
					}
				}
				lastEvent = data.get(j);

				editTime = ( lastEvent.getEditDatetime().getTime() - firstEvent.getEditDatetime().getTime() )/1000;
			}


			System.out.print(i);
			System.out.print(",");
			System.out.print(id);
			System.out.print(",");
			System.out.print(editEventNum);
			System.out.print(",");
			System.out.print(updateEditEventNum);
			System.out.print(",");
			System.out.print(PlaceEventNum);
			System.out.print(",");
			System.out.print(editTime);
			System.out.println();

			i++;
		}



		//********************************************************************************************************

		return true;
	}


	private void individualAnalysis(String studentId, int exerciseId){
		Dao dao = new Dao();
		List<EditEvent> data = dao.getAllEditEventList(studentId, exerciseId);
		List<EditEvent> data2 =dao.getAllEditEventList(studentId, exerciseId);

		List<Long> eventIntervalList =new ArrayList<Long>();

		//関連のドラッグを削除
		for (EditEvent ev : data) {
			if (ev.getEventType().equals("Place")
					&& ev.getCanvasUrl() != null) {
				boolean beRemove = false;
				for (EditEvent e : data) {
						if (ev.getEditDatetime()
								.equals(e.getEditDatetime())) {
							beRemove = true;
						}
				}
				if (beRemove) {
					data2.remove(ev);
				}
			}
		}
		//細かいPlaceを除去
		for (EditEvent ev : data) {
			if (ev.getEventType().equals("Place")
					&& ev.getCanvasUrl() == null) {
				data2.remove(ev);
			}
		}


		eventIntervalList.add(0l);
		for (int i = 1; i < data2.size(); i++) {
			long eventInterval = 0;
			EditEvent preEvent = data2.get(i - 1);
			EditEvent event = data2.get(i);

			eventInterval = ((event.getEditDatetime()).getTime() - (preEvent
					.getEditDatetime()).getTime());
			eventInterval /= 1000;

			eventIntervalList.add(eventInterval);
		}
		System.out.println(studentId);
		for (int i = 0; i < data2.size(); i++) {
			System.out.print(i);
			System.out.print(":");
			System.out.print(data2.get(i).getEditEventId());
			System.out.print(":");
			System.out.print(data2.get(i).getEventType());
			System.out.print(":");
			System.out.print(data2.get(i).getEditEvent());
			System.out.print(":");
			System.out.print(data2.get(i).getBeforeEdit());
			System.out.print(":");
			System.out.print(data2.get(i).getAfterEdit());
			System.out.print(":");
			System.out.print(eventIntervalList.get(i));
			System.out.println();

	}


		try {
			File file = new File("analysis\\"+studentId + ".csv");

			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new FileWriter(file)));
			int c = 1;
			pw.println(studentId);
			for (int i = 0; i < data.size(); i++) {
				pw.print(i);
				pw.print(",");
				pw.print(data.get(i).getEditEventId());
				pw.print(",");
				pw.print(data.get(i).getEventType());
				pw.print(",");
				pw.print( (data.get(i).getEditEvent() )==null ? null: ( data.get(i).getEditEvent() ).replaceAll("," , ":"));
				pw.print(",");
				pw.print( ( data.get(i).getBeforeEdit() )==null ? null: ( data.get(i).getBeforeEdit() ).replaceAll("," , ":"));
				pw.print(",");
				pw.print( ( data.get(i).getAfterEdit() )==null ? null: ( data.get(i).getAfterEdit() ).replaceAll("," , ":"));
				pw.print(",");
				pw.print(eventIntervalList.get(i));
				pw.println();
			}

			pw.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}





	private boolean editTargetRetioByEventNum(String studentId, int exerciseId){
		Dao dao = new Dao();
		List<EditEvent> eventList = dao.getAllEditEventList(studentId, exerciseId);
		EditEvent firstEvent;


		//細かいPlaceを除去
		for (int i=0 ; i < eventList.size() ; i++) {
			EditEvent ev = eventList.get(i);
			if (ev.getEventType().equals("Place")
					&& ev.getCanvasUrl() == null) {
				eventList.set(i,null);
			}
		}
		eventList.removeAll(Collections.singleton(null));

		int i=0;
		if(eventList.size()!=0){
			while(eventList.get(i).getEditEvent()==null){
				i++;
				if( i > eventList.size()-1 ){
					i=0;
					return false;
				}
			}
			firstEvent = eventList.get(i);
		}
		else {
			return false;
		}


		//Editのイベントのみ対象で、クラス名、属性、関連
		//全部対象で、クラス、関連のIDごと

		println(studentId);
		for(int j=0; j<eventList.size()-9 ;  j+=10){
			int createNum=0;
			int editNum=0;
			int removeNum=0;
			int placeNum=0;
			for( int k =j ; k<j+10 ; k++){

				if( k >= eventList.size()){
					return true;
				}
				else{
					if(eventList.get(k).getEventType().equals("Create")){
						createNum++;
					}
					if(eventList.get(k).getEventType().equals("Edit")){
						editNum++;
					}
					if(eventList.get(k).getEventType().equals("Remove")){
						removeNum++;
					}
					if(eventList.get(k).getEditEvent() != null && eventList.get(k).getEditEvent().equals("PlaceArtifacts")){
						placeNum++;
					}
//					print("開始イベント番号＝"+j+"  ");
//					println(eventList.get(k).getEventType() + " : "+ eventList.get(k).getEditEvent());
				}
			}
			print("開始イベント番号＝"+j+"  ");
			println(" Create : "+ createNum+", Edit : "+editNum + " , Remove : "+ removeNum+ " , Place : "+ placeNum);
		}

		println("");


			return true;
		}


	private boolean trialAndErrorNum(String studentId, int exerciseId) throws UnsupportedEncodingException{
		Dao dao = new Dao();
		List<EditEvent> eventList = dao.getAllEditEventListDecoded(studentId, exerciseId);//decodeされたcanvasUrlが入ってるイベントのリスト
		Map<Integer, Integer > updateEditNum = new HashMap<Integer, Integer>();
		Map<Integer, Integer > updatePlaceNum = new HashMap<Integer, Integer>();

		for( int i = 0 ; i < eventList.size() ; i++){
			EditEvent ev = eventList.get(i);

			if(!ev.getEventType().equals("Edit")) continue;

			int targetId = ev.getTargetId();

			if(targetId < 0) continue;

			if(updateEditNum.containsKey(targetId)){
				updateEditNum.put(targetId, updateEditNum.get(targetId)+1);
			}
			else{
				updateEditNum.put(targetId, 1);
			}
		}

		for( int i = 0 ; i < eventList.size() ; i++){
			EditEvent ev = eventList.get(i);

			if(!ev.getEventType().equals("Place")) continue;

			int targetId = ev.getTargetId();

			if(targetId < 0) continue;

			if(updatePlaceNum.containsKey(targetId)){
				updatePlaceNum.put(targetId, updatePlaceNum.get(targetId)+1);
			}
			else{
				updatePlaceNum.put(targetId, 1);
			}
		}





		EditEvent lastEvent = null;
		for(int i = eventList.size()-1 ; i >= 0 ; i--){
			if(eventList.get(i).getCanvasUrl()!=null){
				lastEvent = eventList.get(i);
				break;
			}
		}
		if(lastEvent==null) return false;

		String canvas = lastEvent.getCanvasUrl();
		List<UMLArtifact> artifactList = fromURL(canvas);
		println(studentId+"分析結果");
		for(Iterator<Integer> iterator = updateEditNum.keySet().iterator() ; iterator.hasNext();){
			int artifactId = iterator.next();
			print("ID = "+artifactId);

			String artifactName=getArtifactNameById(artifactList, artifactId);

			print("  名前 = "+artifactName);
			print("  編集回数 = "+updateEditNum.get(artifactId));
			if(updatePlaceNum.containsKey(artifactId)){
				print("  移動回数 = "+updatePlaceNum.get(artifactId));
			}
			println("");


		}





		return true;
	}




	private void print(Object o){
		System.out.print(o);
	}

	private void println(Object o){
		System.out.println(o);
	}


	public String getArtifactNameById(List<UMLArtifact>artifactList, int artifactId) {
		UMLArtifact artifact = getArtifactById(artifactList, artifactId);
		String artifactName=null;
		if( artifact!=null ){
			Class<? extends UMLArtifact> artifactClass = artifact.getClass();
			String artifactClassName = artifactClass.getSimpleName();

			//print(artifactClassName);
			if(artifactClassName.equals("ClassArtifact")){
				artifactName = "Class : "+((ClassArtifact) artifact).getName();
			}
			if(artifactClassName.equals("ClassRelationLinkArtifact")){
				artifactName = "ClassRelation : "+((ClassRelationLinkArtifact) artifact).getName();
			}
			if(artifactClassName.equals("RelationLinkArtifact")){
				artifactName = "RelationLink : "+((ClassRelationLinkArtifact) artifact).getName();
			}
		}
		else{
			artifactName="deleted";
		}
		return artifactName;
	}


	public List<UMLArtifact> fromURL(final String decodedUrl) {

		List<UMLArtifact> artifactList = new ArrayList<UMLArtifact>();
		//try {
		if (!decodedUrl.equals("AA==")) {
			String diagram = decodedUrl;
			Point pasteShift = Point.getOrigin();

			diagram = diagram.substring(0, diagram.lastIndexOf(";"));
			final String[] diagramArtifacts = diagram.split(";");

			for (final String artifactWithParameters : diagramArtifacts) {
				if (!artifactWithParameters.equals("")) {
					final String[] artifactAndParameters = artifactWithParameters.split("\\$");
					if (artifactAndParameters.length > 1) {
						final String[] artifactAndId = artifactAndParameters[0].split("]");
						final String[] parameters = artifactAndParameters[1].split("!", -1);
						final String artifact = artifactAndId[1];
						int id = 0;
						try {
							id = Integer.parseInt(artifactAndId[0].replaceAll("[<>]", ""));
						} catch (final Exception ex) {
							Logger.getGlobal().severe("Parsing url, artifact id is NaN : " + artifactWithParameters + " : " + ex);
						}
						UMLArtifact newArtifact = null;
						if (artifact.equals("Class")) {
							newArtifact = new ClassArtifact(("") +UMLClass.parseNameOrStereotype(parameters[1]), UMLClass.parseNameOrStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] classAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : classAttributes) {
									((ClassArtifact) newArtifact).addAttribute(UMLClassAttribute.parseAttribute(attribute));
								}
							}
							if (parameters[4].length() > 1) {
								final String[] classMethods = parameters[4].substring(0, parameters[4].lastIndexOf("%")).split("%");
								for (final String method : classMethods) {
									((ClassArtifact) newArtifact).addMethod(UMLClassMethod.parseMethod(method));
								}
							}

						} else if (artifact.equals("Object")) {
							newArtifact = new ObjectArtifact(UMLObject.parseName(parameters[1]).get(0), ("") + UMLObject.parseName(parameters[1]).get(1),
									UMLObject.parseStereotype(parameters[2]));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
							if (parameters[3].length() > 1) {
								final String[] objectAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
								for (final String attribute : objectAttributes) {
									((ObjectArtifact) newArtifact).addAttribute(UMLObjectAttribute.parseAttribute(attribute));
								}
							}

						} else if (artifact.equals("LifeLine")) {
							newArtifact = new LifeLineArtifact(("") + UMLLifeLine.parseName(parameters[1]).get(1), UMLLifeLine.parseName(parameters[1]).get(0));
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("Note")) {
							newArtifact = new NoteArtifact(parameters[1]);
							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));

						} else if (artifact.equals("LinkNote")) {
							Integer noteId = 0;
							Integer targetId = 0;
							try {
								noteId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkNoteArtifact((NoteArtifact) getArtifactById(artifactList, noteId), getArtifactById(artifactList, targetId));

						} else if (artifact.equals("LinkClassRelation")) {
							Integer classId = 0;
							Integer relationId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new LinkClassRelationArtifact((ClassArtifact) getArtifactById(artifactList, classId),
									(ClassRelationLinkArtifact) getArtifactById(artifactList, relationId));

						} else if (artifact.equals("ClassRelationLink")) {
							Integer classLeftId = 0;
							Integer classRigthId = 0;
							try {
								classLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								classRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ClassRelationLinkArtifact((ClassArtifact) getArtifactById(artifactList, classLeftId),
									(ClassArtifact) getArtifactById(artifactList, classRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ClassRelationLinkArtifact) newArtifact).setName(("") + parameters[3]);
							((ClassRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ClassRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ClassRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
							((ClassRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
							((ClassRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
							((ClassRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
							((ClassRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
							((ClassRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
							((ClassRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);

						} else if (artifact.equals("ObjectRelationLink")) {
							Integer objectLeftId = 0;
							Integer objectRigthId = 0;
							try {
								objectLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new ObjectRelationLinkArtifact((ObjectArtifact) getArtifactById(artifactList, objectLeftId),
									(ObjectArtifact) getArtifactById(artifactList, objectRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((ObjectRelationLinkArtifact) newArtifact).setName(("") + parameters[3]);
							((ObjectRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((ObjectRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((ObjectRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("MessageLink")) {
							Integer lifeLineLeftId = 0;
							Integer lifeLineRigthId = 0;
							try {
								lifeLineLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								lifeLineRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new MessageLinkArtifact((LifeLineArtifact) getArtifactById(artifactList, lifeLineLeftId),
									(LifeLineArtifact) getArtifactById(artifactList, lifeLineRigthId), LinkKind.getRelationKindFromName(parameters[2]));
							((MessageLinkArtifact) newArtifact).setName(("") + parameters[3]);
							((MessageLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
							((MessageLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
							((MessageLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));

						} else if (artifact.equals("InstantiationRelationLink")) {
							Integer classId = 0;
							Integer objectId = 0;
							try {
								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
								objectId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
							} catch (final Exception ex) {
								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
							}
							newArtifact = new InstantiationRelationLinkArtifact((ClassArtifact) getArtifactById(artifactList, classId),
									(ObjectArtifact) getArtifactById(artifactList, objectId), LinkKind.INSTANTIATION);
						}
						if (newArtifact != null) {
							newArtifact.setId(id);
							artifactList.add(newArtifact);
						}

					}
				}
			}
		}
		//} catch (final Exception ex) {
		//	Logger.getGlobal().severe("There was a problem reading diagram in url : ");
		//}
		return artifactList;
	}

	private UMLArtifact getArtifactById( List<UMLArtifact> artifactList, int id){
		for(UMLArtifact artifact : artifactList){
			if(artifact.getId()==id){
				return artifact;
			}
		}
		return null;
	}

	private void makeStudentList(List<String> studentIdList, Map<String, Integer>  DAVNum){
		//有効学生
		studentIdList.add("1135348");
		studentIdList.add("1135433");
		studentIdList.add("akiho66");
		studentIdList.add("c1135108");
		studentIdList.add("c1135109");
		studentIdList.add("c1135113");
		studentIdList.add("c1135122");
		studentIdList.add("c1135129");
		studentIdList.add("c1135135");
		studentIdList.add("c1135158");
		studentIdList.add("c1135162");
		studentIdList.add("c1135166");
		studentIdList.add("c1135209");
		studentIdList.add("c1135232");
		studentIdList.add("c1135246");
		studentIdList.add("c1135306");
		studentIdList.add("c1135313");
		studentIdList.add("c1135325");
		studentIdList.add("c1135346");
		studentIdList.add("c1135427");
		studentIdList.add("c1135445");
		studentIdList.add("c1135453");
		studentIdList.add("c1135457");
		studentIdList.add("c1135460");


	//無効データを含む
		studentIdList.add("c1135239");
		studentIdList.add("c1135237");
		studentIdList.add("1135444");
		studentIdList.add("c1135117");
		studentIdList.add("c1135120");
		studentIdList.add("c1135123");
		studentIdList.add("c1135159");
		studentIdList.add("c1135219");
		studentIdList.add("c1135363");
		studentIdList.add("c1135417");
		studentIdList.add("c1135425");
		studentIdList.add("c1135452");


		DAVNum.put("1135348",0);
		DAVNum.put("1135433",0);
		DAVNum.put("akiho66",0);
		DAVNum.put("c1135108",0);
		DAVNum.put("c1135109",0);
		DAVNum.put("c1135113",0);
		DAVNum.put("c1135122",0);
		DAVNum.put("c1135129",0);
		DAVNum.put("c1135135",0);
		DAVNum.put("c1135158",0);
		DAVNum.put("c1135162", 7757);
		DAVNum.put("c1135166",0);
		DAVNum.put("c1135209",9569);
		DAVNum.put("c1135232",0);
		DAVNum.put("c1135246",0);
		DAVNum.put("c1135306",0);
		DAVNum.put("c1135313",0);
		DAVNum.put("c1135325",8123);
		DAVNum.put("c1135346",4307);
		DAVNum.put("c1135427",4184);
		DAVNum.put("c1135445",4235);
		DAVNum.put("c1135453",3558);
		DAVNum.put("c1135457",0);
		DAVNum.put("c1135460",0);


			//無効データを含む
		DAVNum.put("c1135239", 6642);
		DAVNum.put("c1135237", 5001);
		DAVNum.put("1135444", 12593);
		DAVNum.put("c1135117", 13806);
		DAVNum.put("c1135120", 9048);
		DAVNum.put("c1135123", 4679);
		DAVNum.put("c1135159", 8456);
		DAVNum.put("c1135219", 6642);
		DAVNum.put("c1135363", 14493);
		DAVNum.put("c1135417", 3074);
		DAVNum.put("c1135425", 2762);
		DAVNum.put("c1135452", 5574);


	}

	private boolean isContainsDifficult(List<EditEvent> eventList) {
		for(EditEvent ev : eventList){
			if(ev.getEventType().equals("Difficult")){
				return true;
			}
		}
		return false;
	}


}


