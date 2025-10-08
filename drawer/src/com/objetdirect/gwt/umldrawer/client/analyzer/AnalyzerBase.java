package com.objetdirect.gwt.umldrawer.client.analyzer;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.objetdirect.gwt.umldrawer.client.user.GetUserService;
import com.objetdirect.gwt.umldrawer.client.user.GetUserServiceAsync;

public class AnalyzerBase extends DockPanel {
	private SimplePanel centerPanel;
	private VerticalPanel leftSideBar;
	private VerticalPanel rightSideBar;
	private HorizontalPanel northBar;
	private HorizontalPanel southBar;


	//パーツ
//	private AnalyzerPanel analyzerPanel = new AnalyzerPanel();

	public AnalyzerBase() {
		super();
		Window.alert("イニシャライズ");
		this.centerPanel = new SimplePanel();
		this.leftSideBar = new VerticalPanel();
		this.rightSideBar = new VerticalPanel();
		this.northBar = new HorizontalPanel();
		this.southBar = new HorizontalPanel();

		createUserCellList();//ユーザリストを初期化

		//*********************************************************************************************************
		//this.add(analyzerPanel, DockPanel.CENTER);
		this.add(leftSideBar, DockPanel.WEST);
		this.add(rightSideBar, DockPanel.EAST);
		this.add(northBar, DockPanel.NORTH);
		this.add(southBar, DockPanel.SOUTH);


	}

	private void createUserCellList(){
		ButtonCell userCell = new ButtonCell()	;
		final CellList<String> userCellList = new CellList<String>(userCell);
		final SingleSelectionModel<String> selectionModelForUserCellList = new SingleSelectionModel<String>();


		GetUserServiceAsync async = (GetUserServiceAsync)GWT.create(GetUserService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getUserList";
		entryPoint.setServiceEntryPoint(entryURL);

		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback(){
			@SuppressWarnings({ "unchecked" })
			public void onSuccess(Object result){
				userCellList.setSelectionModel(selectionModelForUserCellList);
				selectionModelForUserCellList.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						if( !analyze( selectionModelForUserCellList.getSelectedObject() ) ){
							Window.alert("通信エラー");
						}
					}
				});
				//ユーザセルリストに取得したユーザを追加
				userCellList.setRowData( (List<String>)result);
				leftSideBar.add(userCellList);
			}
			public void onFailure(Throwable caught){
				System.out.println("ユーザ取得時にエラー！");
			}
		};
		async.getUserList(callback);
	}

	private boolean analyze( String studentId) {
		AnalysisServiceAsync async = (AnalysisServiceAsync)GWT.create(AnalysisService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "analyze";
		entryPoint.setServiceEntryPoint(entryURL);
		@SuppressWarnings("rawtypes")
		AsyncCallback callback = new AsyncCallback() {

			@Override
			public void onSuccess(Object result) {
				Window.alert("分析完了");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("分析失敗");
			}

		};

		async.analyze(studentId, 1, callback);
		return true;
	}


//	private VerticalPanel createEditIntervalPanel(List<EditEvent> editEventList){
//		VerticalPanel panel = new VerticalPanel();
//		List<EditEvent> data = new ArrayList<EditEvent>();
//		for(EditEvent e : editEventList){
//			data.add(e);
//		}
//		List<Long> eventIntervalList =new ArrayList<Long>();
//		for(EditEvent ev : data){
//			if( ev.getEventType().equals("Place") && ev.getCanvasUrl()==null){
//				data.remove(ev);
//			}
//		}
//
//		//Placeだけど場所が動いていないイベント（フォーカスのみ）を除去
//
//		for(EditEvent ev : data){
//			if(ev.getEventType().equals("Place")){
//				if(ev.getCanvasUrl()==null){
//					data.remove(ev);
//				}
//			}
//		}
//
//		eventIntervalList.add(0l);
//		for( int i=1 ; i<data.size() ; i++ ){
//			long eventInterval=0;
//			EditEvent preEvent = data.get(i-1);
//			EditEvent event = data.get(i);
//
//				eventInterval = ( ( event.getEditDatetime() ).getTime() - ( preEvent.getEditDatetime() ).getTime() );
//				eventInterval/=1000;
//
//			eventIntervalList.add( eventInterval);
//		}
//		panel.add(new Label("編集間隔"));
//		int c=1;
//		for(int i = 0 ; i<data.size() ; i++){
//			EditEvent e = data.get(i);
//			HorizontalPanel hp = new HorizontalPanel();
//			//hp.add( new Label( c+" :"+eventString.get(i)+": " ) );
//			hp.add( new Label( c +" : ") );
//			hp.add( new Label( " "+data.get(i).getEventType()+" : ") );
//			hp.add( new Label( " "+eventIntervalList.get(i).toString() ) );
//
//			//panel.add(hp);
//			panel.add( hp );
//			c++;
//		}
//
//		return panel;
//	}
//
//
//	private VerticalPanel createNumberOfEditPanel(List<EditEvent> editEventList){
//		VerticalPanel panel = new VerticalPanel();
//		List<EditEvent> data = new ArrayList<EditEvent>();
//		for(EditEvent e : editEventList){
//			data.add(e);
//		}
//		HashMap<Integer,Integer> objectIdMapOfEdit = new HashMap<Integer,Integer>();
//		HashMap<Integer,Integer> objectIdMapOfPlace = new HashMap<Integer,Integer>();
//
//
//		String ArtifactId = null;
//		for(EditEvent ev : data){
//			if( ev.getEventType().equals("Create")||ev.getEventType().equals("Edit")||ev.getEventType().equals("Remove")){
//				int targetId = ev.getTargetId();
//				if(objectIdMapOfEdit.containsKey(targetId)){
//					objectIdMapOfEdit.put(targetId, objectIdMapOfEdit.get(targetId)+1);
//				}else{
//					objectIdMapOfEdit.put(targetId, 1);
//				}
//			}
//		}
//
//		panel.add(new Label("　編集回数"));
//
//		Iterator<Map.Entry<Integer, Integer>> i = objectIdMapOfEdit.entrySet().iterator() ;
//		int c=1;
//		while( i.hasNext()){
//			HorizontalPanel hp = new HorizontalPanel();
//			String artifactName = "none";
//
//			Entry<Integer, Integer> e = (Map.Entry<Integer, Integer>) i.next();
//			System.out.println(e.getKey()+":"+e.getValue());
//
//
//			EditEvent lastEvent = data.get(data.size()-1);
//			int j = 1;
//			while(lastEvent.getCanvasUrl()==null){
//				lastEvent = data.get(data.size()-1-j);
//				j++;
//			}
//			List<UMLArtifact> artifactList = fromURL(lastEvent.getCanvasUrl(), false);
//
//			UMLArtifact artifact = UMLArtifact.getArtifactById(e.getKey());
//
//			Window.alert(artifact.getClass().getName());
//			String className = artifact.getClass().getName();
//
//			if(className.equals("com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact")){
//				artifactName = ( (ClassArtifact)artifact ).getName();
//			}
//
//
//
//			Label keyLabel = new Label("　|"+c+": "+e.getKey()+":");
//			Label classNameLabel = new Label(className+":");
//			Label nameLabel = new Label(artifactName+":");
//			Label valuelabel = new Label(e.getValue().toString());
//			hp.add(keyLabel);
//			hp.add(nameLabel);
//			hp.add(valuelabel);
//			panel.add(hp);
//			c++;
//		}
//
//
//		return panel;
//	}
//
//
//	public List<UMLArtifact> fromURL(final String url, final boolean isForPasting) {
//		List<UMLArtifact> artifactList = new ArrayList<UMLArtifact>();
//		//try {
//		if (!url.equals("AA==")) {
//			String diagram = isForPasting ? url : GWTUMLDrawerHelper.decodeBase64(url);
//			Point pasteShift =   Point.getOrigin();
//
//			diagram = diagram.substring(0, diagram.lastIndexOf(";"));
//			final String[] diagramArtifacts = diagram.split(";");
//
//			for (final String artifactWithParameters : diagramArtifacts) {
//				if (!artifactWithParameters.equals("")) {
//					final String[] artifactAndParameters = artifactWithParameters.split("\\$");
//					if (artifactAndParameters.length > 1) {
//						final String[] artifactAndId = artifactAndParameters[0].split("]");
//						final String[] parameters = artifactAndParameters[1].split("!", -1);
//						final String artifact = artifactAndId[1];
//						int id = 0;
//						try {
//							id = Integer.parseInt(artifactAndId[0].replaceAll("[<>]", ""));
//						} catch (final Exception ex) {
//							Logger.getGlobal().severe("Parsing url, artifact id is NaN : " + artifactWithParameters + " : " + ex);
//						}
//						UMLArtifact newArtifact = null;
//						if (artifact.equals("Class")) {
//							newArtifact = new ClassArtifact(UMLClass.parseNameOrStereotype(parameters[1]), UMLClass.parseNameOrStereotype(parameters[2]));
//							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
//							if (parameters[3].length() > 1) {
//								final String[] classAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
//								for (final String attribute : classAttributes) {
//									((ClassArtifact) newArtifact).addAttribute(UMLClassAttribute.parseAttribute(attribute));
//								}
//							}
//							if (parameters[4].length() > 1) {
//								final String[] classMethods = parameters[4].substring(0, parameters[4].lastIndexOf("%")).split("%");
//								for (final String method : classMethods) {
//									((ClassArtifact) newArtifact).addMethod(UMLClassMethod.parseMethod(method));
//								}
//							}
//
//						} else if (artifact.equals("Object")) {
//							newArtifact = new ObjectArtifact(UMLObject.parseName(parameters[1]).get(0), UMLObject.parseName(parameters[1]).get(1),
//									UMLObject.parseStereotype(parameters[2]));
//							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
//							if (parameters[3].length() > 1) {
//								final String[] objectAttributes = parameters[3].substring(0, parameters[3].lastIndexOf("%")).split("%");
//								for (final String attribute : objectAttributes) {
//									((ObjectArtifact) newArtifact).addAttribute(UMLObjectAttribute.parseAttribute(attribute));
//								}
//							}
//
//						} else if (artifact.equals("LifeLine")) {
//							newArtifact = new LifeLineArtifact(UMLLifeLine.parseName(parameters[1]).get(1), UMLLifeLine.parseName(parameters[1]).get(0));
//							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
//
//						} else if (artifact.equals("Note")) {
//							newArtifact = new NoteArtifact(parameters[1]);
//							newArtifact.setLocation(Point.add(Point.parse(parameters[0]), pasteShift));
//
//						} else if (artifact.equals("LinkNote")) {
//							Integer noteId = 0;
//							Integer targetId = 0;
//							try {
//								noteId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								targetId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new LinkNoteArtifact((NoteArtifact) UMLArtifact.getArtifactById(noteId), UMLArtifact.getArtifactById(targetId));
//
//						} else if (artifact.equals("LinkClassRelation")) {
//							Integer classId = 0;
//							Integer relationId = 0;
//							try {
//								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								relationId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new LinkClassRelationArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
//									(ClassRelationLinkArtifact) UMLArtifact.getArtifactById(relationId));
//
//						} else if (artifact.equals("ClassRelationLink")) {
//							Integer classLeftId = 0;
//							Integer classRigthId = 0;
//							try {
//								classLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								classRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new ClassRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classLeftId),
//									(ClassArtifact) UMLArtifact.getArtifactById(classRigthId), LinkKind.getRelationKindFromName(parameters[2]));
//							((ClassRelationLinkArtifact) newArtifact).setName( parameters[3]);
//							((ClassRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
//							((ClassRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
//							((ClassRelationLinkArtifact) newArtifact).setLeftCardinality(parameters[6]);
//							((ClassRelationLinkArtifact) newArtifact).setLeftConstraint(parameters[7]);
//							((ClassRelationLinkArtifact) newArtifact).setLeftRole(parameters[8]);
//							((ClassRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[9]));
//							((ClassRelationLinkArtifact) newArtifact).setRightCardinality(parameters[10]);
//							((ClassRelationLinkArtifact) newArtifact).setRightConstraint(parameters[11]);
//							((ClassRelationLinkArtifact) newArtifact).setRightRole(parameters[12]);
//
//						} else if (artifact.equals("ObjectRelationLink")) {
//							Integer objectLeftId = 0;
//							Integer objectRigthId = 0;
//							try {
//								objectLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								objectRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new ObjectRelationLinkArtifact((ObjectArtifact) UMLArtifact.getArtifactById(objectLeftId),
//									(ObjectArtifact) UMLArtifact.getArtifactById(objectRigthId), LinkKind.getRelationKindFromName(parameters[2]));
//							((ObjectRelationLinkArtifact) newArtifact).setName(parameters[3]);
//							((ObjectRelationLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
//							((ObjectRelationLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
//							((ObjectRelationLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));
//
//						} else if (artifact.equals("MessageLink")) {
//							Integer lifeLineLeftId = 0;
//							Integer lifeLineRigthId = 0;
//							try {
//								lifeLineLeftId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								lifeLineRigthId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new MessageLinkArtifact((LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineLeftId),
//									(LifeLineArtifact) UMLArtifact.getArtifactById(lifeLineRigthId), LinkKind.getRelationKindFromName(parameters[2]));
//							((MessageLinkArtifact) newArtifact).setName(parameters[3]);
//							((MessageLinkArtifact) newArtifact).setLinkStyle(LinkStyle.getLinkStyleFromName(parameters[4]));
//							((MessageLinkArtifact) newArtifact).setLeftAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[5]));
//							((MessageLinkArtifact) newArtifact).setRightAdornment(LinkAdornment.getLinkAdornmentFromName(parameters[6]));
//
//						} else if (artifact.equals("InstantiationRelationLink")) {
//							Integer classId = 0;
//							Integer objectId = 0;
//							try {
//								classId = Integer.parseInt(parameters[0].replaceAll("[<>]", ""));
//								objectId = Integer.parseInt(parameters[1].replaceAll("[<>]", ""));
//							} catch (final Exception ex) {
//								Logger.getGlobal().severe("Parsing url, id is NaN : " + artifactWithParameters + " : " + ex);
//							}
//							newArtifact = new InstantiationRelationLinkArtifact((ClassArtifact) UMLArtifact.getArtifactById(classId),
//									(ObjectArtifact) UMLArtifact.getArtifactById(objectId), LinkKind.INSTANTIATION);
//						}
//						if (newArtifact != null) {
//							newArtifact.setId(id);
//							artifactList.add(newArtifact);
//						}
//
//					}
//				}
//			}
//		}
//		return artifactList;
//	}










	/**
	 * @return centerPanel
	 */
	public SimplePanel getCenterPanel() {
		return centerPanel;
	}
	/**
	 * @param centerPanel セットする centerPanel
	 */
	public void setCenterPanel(SimplePanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	/**
	 * @return leftSideBar
	 */
	public VerticalPanel getLeftSideBar() {
		return leftSideBar;
	}
	/**
	 * @param leftSideBar セットする leftSideBar
	 */
	public void setLeftSideBar(VerticalPanel leftSideBar) {
		this.leftSideBar = leftSideBar;
	}
	/**
	 * @return rightSideBar
	 */
	public VerticalPanel getRightSideBar() {
		return rightSideBar;
	}
	/**
	 * @param rightSideBar セットする rightSideBar
	 */
	public void setRightSideBar(VerticalPanel rightSideBar) {
		this.rightSideBar = rightSideBar;
	}
	/**
	 * @return northBar
	 */
	public HorizontalPanel getNorthBar() {
		return northBar;
	}
	/**
	 * @param northBar セットする northBar
	 */
	public void setNorthBar(HorizontalPanel northBar) {
		this.northBar = northBar;
	}
	/**
	 * @return southBar
	 */
	public HorizontalPanel getSouthBar() {
		return southBar;
	}
	/**
	 * @param southBar セットする southBar
	 */
	public void setSouthBar(HorizontalPanel southBar) {
		this.southBar = southBar;
	}
}