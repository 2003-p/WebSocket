package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.beans.Student;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;
import com.objetdirect.gwt.umldrawer.client.user.GetUserService;
import com.objetdirect.gwt.umldrawer.client.user.GetUserServiceAsync;
import com.objetdirect.gwt.umldrawer.client.viewer.ViewerForStudentBase;

public class LeftButtonPanel extends VerticalPanel{
	private Button stepFoward;
	private Button stepBack;
	private Button addReflection;
	private Button removeReflection;
	private Button refComSwitchButton;
	private EventListPanel eventListPanel;
	private ReflectionListPanel reflectionListPanel;
	private DeckPanel buttonDeckPanel;
	private ViewerForStudentBase viewer;
	private PopupPanel userListPanel = new PopupPanel();
	private Label idLabel;
	private List<Student> studentListForReplay;

	public LeftButtonPanel( boolean isTeacher, ViewerForStudentBase viewer, EventListPanel eventListPanel, ReflectionListPanel reflectionListPanel ) {
		this.viewer = viewer;
		this.reflectionListPanel = reflectionListPanel;
		this.eventListPanel = eventListPanel;

		HorizontalPanel hp1 = new HorizontalPanel();
		this.add(hp1);
		HorizontalPanel hp2 = new HorizontalPanel();
		this.add(hp2);


		if(isTeacher){
			//
			GetUserServiceAsync async = (GetUserServiceAsync)GWT.create(GetUserService.class);
			ServiceDefTarget entryPoint = (ServiceDefTarget) async;
			String entryURL = GWT.getModuleBaseURL() + "getUserListForReplay";
			entryPoint.setServiceEntryPoint(entryURL);

			AsyncCallback<List<Student>> callback = new AsyncCallback<List<Student>>(){
				public void onSuccess(List<Student> result){
					LeftButtonPanel.this.studentListForReplay = result;
				}
				public void onFailure(Throwable caught){
				}
			};
			async.getUserListForReplay(LeftButtonPanel.this.viewer.getExerciseId(), callback);
			//

			hp1.setSpacing(5);
			Button studentListButton = new Button(DrawerTextResource.LEARNER_LIST.getMessage());
			studentListButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					userListPanel = new PopupPanel(true);
					ScrollPanel sp = new ScrollPanel();
					sp.setPixelSize(200, 500);
					userListPanel.add(sp);
					VerticalPanel vp = new VerticalPanel();
					vp.setSpacing(2);
					sp.add(vp);
					for(Student st : LeftButtonPanel.this.studentListForReplay){
						String id = st.getStudentId();
						final Button b = new Button(id);
						if(id.equals(LeftButtonPanel.this.viewer.getStudentId())){
							b.setFocus(true);
						}
						b.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								LeftButtonPanel.this.viewer.updateViewer(b.getText(), Session.exerciseId);
								userListPanel.hide();
							}
						});
						vp.add(b);
					}
					userListPanel.setPopupPosition(0, 50);
					userListPanel.show();
				}
			});
			hp1.add(studentListButton);

			Button nextStudentButton = new Button(DrawerTextResource.NEXT_LEARNER.getMessage());
			nextStudentButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					GetUserServiceAsync async = (GetUserServiceAsync)GWT.create(GetUserService.class);
					ServiceDefTarget entryPoint = (ServiceDefTarget) async;
					String entryURL = GWT.getModuleBaseURL() + "getUserListForReplay";
					entryPoint.setServiceEntryPoint(entryURL);

					AsyncCallback<List<Student>> callback = new AsyncCallback<List<Student>>(){
						public void onSuccess(List<Student> result){
							for(int i=0; i<result.size()-1 ; i++){
								if(result.get(i).getStudentId().equals(LeftButtonPanel.this.viewer.getStudentId())){
									LeftButtonPanel.this.viewer.updateViewer(result.get(i+1).getStudentId(), Session.exerciseId);
									return;
								}
							}
							Window.alert("最後の学生( "+result.get(result.size()-1)+" )です。");
						}
						public void onFailure(Throwable caught){
						}
					};
					async.getUserListForReplay(LeftButtonPanel.this.viewer.getExerciseId(), callback);
				}
			});
			hp1.add(nextStudentButton);

			idLabel = new Label(viewer.getStudentId());
			hp1.add(idLabel);

			this.refComSwitchButton = new Button(DrawerTextResource.REFLECTION_COMMENT.getMessage(), new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					int idx = LeftButtonPanel.this.viewer.getReflectionDeckPanel().getVisibleWidget();
					LeftButtonPanel.this.viewer.getReflectionDeckPanel().showWidget((idx+1)%2);
					LeftButtonPanel.this.buttonDeckPanel.showWidget((idx+1)%2);
				}
			});
			hp1.add(this.refComSwitchButton);

		}

		this.stepFoward = new Button(DrawerTextResource.FOWERD.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LeftButtonPanel.this.eventListPanel.repleyByStep(true);
			}
		});
		this.stepBack = new Button(DrawerTextResource.BACK.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LeftButtonPanel.this.eventListPanel.repleyByStep(false);
			}
		});

		hp2.setSpacing(5);
		hp2.add(stepFoward);
		hp2.add(stepBack);

		this.addReflection = new Button(DrawerTextResource.ADD_REFLECTION.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int selectedIdx = LeftButtonPanel.this.eventListPanel.getSelectedIdx();
				EditEvent selectedEvent = LeftButtonPanel.this.eventListPanel.getEditEvent(selectedIdx);
				ReflectionListPanel reflectionListPanel = LeftButtonPanel.this.reflectionListPanel;
				Reflection reflection=new Reflection();
				reflection.setExerciseId(LeftButtonPanel.this.viewer.getExerciseId());
				reflection.setStudentId(LeftButtonPanel.this.viewer.getStudentId());
				ReflectionPanel reflectionPanel=null;
				boolean exist = false;
				for( Reflection r : reflectionListPanel.getReflectionList() ){
					if(r.getOccurrencePoint().getEditEventId() == selectedEvent.getEditEventId()){
						reflection = r;
						reflectionPanel = reflectionListPanel.getReflectionPanelByReflection(r);
						exist = true;
						System.out.println("Exist at left button");
					}
				}

				if(exist){
					//editor = new ReflectionEditor(reflection, reflectionPanel, 300, 500);
				}
				else{
					reflection.setOccurrencePoint(selectedEvent );
					reflectionPanel = reflectionListPanel.addReflection(reflection);
				}
				ReflectionEditor reflectionEditor = new ReflectionEditor(reflection, reflectionPanel, 300, 500);
				reflectionEditor.setAutoHideOnHistoryEventsEnabled(true);
				reflectionEditor.setPopupPosition(350, 100);
				reflectionEditor.show();
			}
		});

		this.removeReflection = new Button(DrawerTextResource.REMOVE_REFLECTION.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final int selectedIdx = LeftButtonPanel.this.reflectionListPanel.getSelectedIndex();
				final Reflection toRemove = LeftButtonPanel.this.reflectionListPanel.getReflection(selectedIdx);
				System.out.println("Remove:"+selectedIdx);
				if( selectedIdx >= 0){
					ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
					ServiceDefTarget entryPoint = (ServiceDefTarget) async;
					String entryURL = GWT.getModuleBaseURL() + "removeReflection";
					entryPoint.setServiceEntryPoint(entryURL);
					AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>(){
						public void onSuccess(Boolean result){
							if(result){
								LeftButtonPanel.this.reflectionListPanel.removeReflection(selectedIdx);
							}
						}
						public void onFailure(Throwable caught){
						}
					};
					async.removeReflection(toRemove, callback );

				}

			}
		});

		this.buttonDeckPanel =new DeckPanel();
		hp2.add(this.buttonDeckPanel);

		HorizontalPanel hp3 = new HorizontalPanel();
		this.buttonDeckPanel.add(hp3);
		hp3.add(addReflection);
		hp3.add(removeReflection);

		HorizontalPanel hp4 = new HorizontalPanel();
		this.buttonDeckPanel.add(hp4);

		this.buttonDeckPanel.showWidget(0);
	}



}
