package com.objetdirect.gwt.umldrawer.client.drawerparts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umlapi.client.mylogger.MyLoggerExecute;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.CheckedItemPanel;
import com.objetdirect.gwt.umldrawer.client.viewer.viewerparts.WhyReflectPanel;

public class CheckPanel extends PopupPanel{
	private VerticalPanel base;
	private WhyReflectPanel whyReflectPanel;
	private CheckedItemPanel checkedItemPanel;
	private Reflection reflection;


	public CheckPanel(){
		super();
		this.reflection = new Reflection();
		base = new VerticalPanel();
		base.setSpacing(2);
		base.setWidth("350px");
		this.setWidth("350px");
		this.add(base);
		whyReflectPanel = new WhyReflectPanel(reflection);
		checkedItemPanel = new CheckedItemPanel(reflection);
		//base.add(whyReflectPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(3);
		base.add(checkedItemPanel);
		hp.add(makeSendButton());
		hp.add(makeCloseButton());
		base.add(hp);

	}

	public CheckPanel(Reflection reflection){
		super();
		this.reflection = reflection;
		base = new VerticalPanel();
		this.add(base);
		whyReflectPanel = new WhyReflectPanel(reflection);
		checkedItemPanel = new CheckedItemPanel(reflection);
		//base.add(whyReflectPanel);
		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(3);
		base.add(checkedItemPanel);
		hp.add(makeSendButton());
		hp.add(makeCloseButton());
		base.add(hp);
	}

	private Button makeSendButton(){
		Button b = new Button(DrawerTextResource.RECORD.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				MyLoggerExecute.preEditEventType="Check";
				reflection.setReasonList(whyReflectPanel.getCheckedReasonList());
				reflection.setCheckedItemList(checkedItemPanel.getCheckedItemList());
				sendNewReflection(reflection);
				CheckPanel.this.hide();
			}
		});
		return b;
	}

	private Button makeCloseButton(){
		Button b = new Button(DrawerTextResource.CLOSE.getMessage());
		b.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				CheckPanel.this.hide();
			}
		});
		return b;
	}

	public void sendNewReflection(Reflection reflection){
		reflection.setStudentId(Session.studentId);
		reflection.setExerciseId(Session.exerciseId);
		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "registerReflectionEventAndReflection";
		entryPoint.setServiceEntryPoint(entryURL);

		AsyncCallback callback = new AsyncCallback(){
			public void onSuccess(Object result){
			}
			public void onFailure(Throwable caught){
				System.out.println(caught);
			}
		};
		//		public void registerReflectionEventAndReflection(Reflection reflection, String studentId, int exercisesId, int preEventId, String editEvent, String eventType,
		//				  String targetType, int targetId, String linkKind, int rightObjectId, int leftObjectId,
		//				  String targetPart, String beforeEdit, String afterEdit, String canvasUrl,
		//				  int umlArtifactId, AsyncCallback async);

		async.registerReflectionEventAndReflection(reflection, Session.studentId, Session.exerciseId, -1, "Check", "Check",
				null, -1, null, -1, -1,
				null, null, null,Session.getActiveCanvas().toUrl(), UMLArtifact.getIdCount(), callback);

	}
}


