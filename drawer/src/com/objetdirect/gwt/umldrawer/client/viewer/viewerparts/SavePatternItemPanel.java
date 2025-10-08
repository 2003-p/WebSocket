package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternService;
import com.objetdirect.gwt.umldrawer.client.pattern.PatternServiceAsync;

public class SavePatternItemPanel extends HorizontalPanel{
	private VerticalPanel left = new VerticalPanel();
	private Label l = new Label(DrawerTextResource.CHECK_SPOT_ITEMS_MESSAGE.getMessage());
	private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
	private List<String> allClassItemList;
/*
	public SavePatternItemPanel(ReflectionEditor reflectionEditor){
		this.reflection = reflectionEditor.getReflection();
		this.setSpacing(2);
		this.setWidth("350px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();
		this.add(left);
	}
*/
	public SavePatternItemPanel(){
		this.setSpacing(2);
		this.setWidth("350px");
		left.setSpacing(2);
		left.add(l);
		addCheckBoxList();
		this.add(left);
	}

	private void addCheckBoxList() {
		this.clear();
		getAllClassListItemList();

	}

	private void getAllClassListItemList() {
		PatternServiceAsync async = (PatternServiceAsync)GWT.create(PatternService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getUMLClassDiagramList";
		entryPoint.setServiceEntryPoint(entryURL);
		
		//final String str = "<0>]Class$(186,196)!Client!!!;<1>]Class$(357,184)!Prototype!!!+ConcreteClone() : void%;<2>]Class$(369,344)!ConcretePrototype!!!+ConcreteClone() : void%;<15>]ClassRelationLink$<1>!<2>!SimpleRelation!Name!Solid!None!None!!!None!None!!;<16>]ClassRelationLink$<0>!<1>!SimpleRelation!Name!Solid!None!None!!!None!None!!;";

		@SuppressWarnings("rawtypes")
		AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>(){
			@SuppressWarnings({ "unchecked", "deprecation" })
			public void onSuccess(List<String> result){
				allClassItemList = result;
				
				for(int i = 0; i < allClassItemList.size(); i ++) {
					checkBoxList.add(new CheckBox( allClassItemList.get(i)));
					left.add( checkBoxList.get(i) );
				}
				setCheckedItemList(allClassItemList);
			}

			public void onFailure(Throwable caught){
				String s = String.valueOf(caught);
				Window.alert(s);
				System.out.println(caught);
			}
		};
		async.getUMLClassDiagramList(Session.studentId, Session.exerciseId, callback);
	}

	public List<String> getCheckedItemList(){
		List<String> ans = new ArrayList<String>();
		for( CheckBox cb : this.getCheckBoxList()){
			if( cb.getValue() ){
				for(String ci : this.allClassItemList){
					if(ci.equals(cb.getText())) {
						ans.add( ci );
					}
				}
			}
		}
		return ans;
	}

	public void setCheckedItemList(List<String> classItemList){
		if (classItemList == null) return;
		for (String ci : classItemList){
			for( CheckBox cb : this.getCheckBoxList()){
				cb.setValue(false);
			}
		}
	}
	public VerticalPanel getLeft() {
		return left;
	}

	public void setLeft(VerticalPanel left) {
		this.left = left;
	}

	public Label getQuestion() {
		return l;
	}

	public void setQuestion(Label l) {
		this.l = l;
	}

	public List<CheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(List<CheckBox> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}

}
