package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.CheckItem;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;

public class CheckedItemPanel extends HorizontalPanel{
	private Reflection reflection;
	private VerticalPanel left = new VerticalPanel();
	private Label question = new Label(DrawerTextResource.CHECK_ITEMS_MESSAGE.getMessage());
	private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
	private List<CheckItem> allCheckItemList;

	public CheckedItemPanel(ReflectionEditor reflectionEditor){
		this.reflection = reflectionEditor.getReflection();
		this.setSpacing(2);
		this.setWidth("350px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();
		this.add(left);
	}

	public CheckedItemPanel(Reflection reflection){
		this.reflection = reflection;
		this.setSpacing(2);
		this.setWidth("350px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();
		this.add(left);
	}

	private void addCheckBoxList() {
		this.clear();
		getAllCheckListItemList();

	}

	private void getAllCheckListItemList() {
		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getAllCheckItemList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<CheckItem>> callback = new AsyncCallback<List<CheckItem>>(){
			public void onSuccess(List<CheckItem> result){
				allCheckItemList = result;
				for(int i=0 ; i<allCheckItemList.size() ; i++){
					checkBoxList.add(new CheckBox( allCheckItemList.get(i).getCheckItem() ));
					left.add( checkBoxList.get(i) );
				}
				setCheckedItemList(reflection.getCheckedItemList());
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getAllCheckItemList( callback );
	}

	public List<CheckItem> getCheckedItemList(){
		List<CheckItem> ans = new ArrayList<CheckItem>();
		for( CheckBox cb : this.getCheckBoxList()){
			if( cb.getValue() ){
				for(CheckItem ci : this.allCheckItemList){
					if( ci.getCheckItem().equals(cb.getText() ) ){
						ans.add( ci );
					}
				}
			}
		}
		return ans;
	}

	public void setCheckedItemList(List<CheckItem> checkedItemList){
		if (checkedItemList == null) return;
		for (CheckItem ci : checkedItemList){
			for( CheckBox cb : this.getCheckBoxList()){
				if( ci.getCheckItem().equals(cb.getText() ) ){
					cb.setValue(true);
				}
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
		return question;
	}

	public void setQuestion(Label question) {
		this.question = question;
	}

	public List<CheckBox> getCheckBoxList() {
		return checkBoxList;
	}

	public void setCheckBoxList(List<CheckBox> checkBoxList) {
		this.checkBoxList = checkBoxList;
	}

	public Reflection getReflection() {
		return reflection;
	}

	public void setReflection(Reflection reflection) {
		this.reflection = reflection;
		addCheckBoxList();
	}

}