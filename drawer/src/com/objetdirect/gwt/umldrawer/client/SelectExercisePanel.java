package com.objetdirect.gwt.umldrawer.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.beans.Exercise;
import com.objetdirect.gwt.umldrawer.client.exercise.ExerciseService;
import com.objetdirect.gwt.umldrawer.client.exercise.ExerciseServiceAsync;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

public class SelectExercisePanel extends VerticalPanel{
	private List<Exercise> exerciseList;

	private Label title = new Label("Select an Exercise");

	public SelectExercisePanel(final int mode){
		this.setWidth("100%");
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		ExerciseServiceAsync async = (ExerciseServiceAsync)GWT.create(ExerciseService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getExerciseList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<Exercise>> callback = new AsyncCallback<List<Exercise>>(){
			public void onSuccess(List<Exercise> result){
				exerciseList = (List<Exercise>) result;
				initButtons(mode);
				DrawerBase.countTimer.cancel();
			}
			public void onFailure(Throwable caught){
				Window.alert("Connection Error!");
			}
		};
		async.getExerciseList(Session.diagramType.getName(), callback);
	}


	private void initButtons(final int mode){
		this.clear();
		this.setSpacing(5);
		this.add(title);
		if(DrawerSession.student.getType() == 1){
			initButtonForTeachers(mode);
		}
		else{
			initButtonForStudents(mode);
		}
	}

	private void initButtonForTeachers(final int mode){
		int i=1;
		for(final Exercise ex : exerciseList){
			final Button btn = new Button();
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(5);

			btn.setText("No. "+i+", Title 「 "+ex.getTitle()+" 」");
			btn.setTitle(ex.getExerciseId()+"");
			btn.addClickHandler( new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Session.exerciseId = Integer.parseInt(btn.getTitle() );
					switch(mode){
					case 1:
						History.newItem("Drawer");
						break;
					case 2:
						History.newItem("Viewer");
						break;
					case 3:
						History.newItem("ViewerForStudent");
						break;
					case 4:
						History.newItem("ProgressView");
						break;
					default:
						History.newItem("Drawer");
						break;
					}
				}
			});
			hp.add(btn);

			if(mode == 1){
				Button removeButton = new Button();
				if(ex.getIsRemoved() == 0){ //TODO fix the type of isRemoved
					removeButton.setText(DrawerTextResource.SET_TO_INVISIBLE.getMessage());
					removeButton.addClickHandler( new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							ex.setIsRemoved(1);//非表示
							setExerciseRemoved(ex.getExerciseId(), true);
							initButtons(mode);
						}
					});
				}
				else{
					removeButton.setText(DrawerTextResource.SET_TO_VISIBLE.getMessage());

					removeButton.addClickHandler( new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							ex.setIsRemoved(0);//表示
							setExerciseRemoved(ex.getExerciseId(), false);
							initButtons(mode);
						}
					});
				}
				hp.add(removeButton);
			}
			this.add(hp);
			i++;
		}
	}

	private void initButtonForStudents(final int mode){
		int i=1;
		for(final Exercise ex : exerciseList){
			if( mode == 1  && ex.getIsRemoved() == 1) {
				i++;
				continue;
			}

			final Button btn = new Button();
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(5);

			btn.setText("No. "+i+", Title 「 "+ex.getTitle()+" 」");
			btn.setTitle(ex.getExerciseId()+"");
			btn.addClickHandler( new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Session.exerciseId = Integer.parseInt(btn.getTitle() );
					switch(mode){
					case 1:
						History.newItem("Drawer");
						break;
					case 2:
						History.newItem("Viewer");
						break;
					case 3:
						History.newItem("ViewerForStudent");
						break;
					case 4:
						History.newItem("ProgressView");
						break;
					default:
						History.newItem("Drawer");
						break;
					}
				}
			});
			hp.add(btn);
			this.add(hp);
			i++;
		}

	}

	private void setExerciseRemoved(int exerciseId, boolean isRemoved) {
		ExerciseServiceAsync async = (ExerciseServiceAsync)GWT.create(ExerciseService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "setExerciseRemoved";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<Void> callback = new AsyncCallback<Void>(){
			public void onSuccess(Void result){

			}
			public void onFailure(Throwable caught){
				Window.alert("Connection Error!");
			}
		};
		async.setExerciseRemoved(exerciseId, isRemoved, callback);
	}
	
}


//if(ex.getExerciseId() == 16){
//HTML link = new HTML("<a href=\"files/"+ex.getExerciseId()+".pdf\" \"target=\"_blank\" \">問題文を開く</a>");
//hp.add(link);
//}
//if(ex.getExerciseId() == 11){
//HTML link = new HTML("<a href=\"files/"+ex.getExerciseId()+".pdf\" \"target=\"_blank\" \">問題文を開く</a>");
//hp.add(link);
//}
