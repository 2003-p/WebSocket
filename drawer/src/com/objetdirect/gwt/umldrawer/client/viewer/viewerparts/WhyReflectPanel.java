package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.OccurrenceReason;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionService;
import com.objetdirect.gwt.umldrawer.client.reflection.ReflectionServiceAsync;

public class WhyReflectPanel extends HorizontalPanel{
	private VerticalPanel left = new VerticalPanel();
	private Label question = new Label(DrawerTextResource.WHY_SC.getMessage());
	private Label other = new Label(DrawerTextResource.WHY_SC_OTHER.getMessage());
	private TextArea detail = new TextArea();
	private List<OccurrenceReason> allReasonList;
	private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();
	private Reflection reflection;

	public WhyReflectPanel(ReflectionEditor reflectionEditor){
		this.reflection = reflectionEditor.getReflection();
		this.setSpacing(2);
		this.setWidth("280px");
		detail.setSize("250px", "70px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();

		this.add(left);
	}

	public WhyReflectPanel(Reflection reflection){
		this.reflection = reflection;
		this.setSpacing(2);
		this.setWidth("280px");
		detail.setSize("250px", "70px");
		left.setSpacing(2);
		left.add(question);
		addCheckBoxList();

		this.add(left);
	}

	private void addCheckBoxList() {
		this.clear();
		 getAllReasonList();
	}

	//TODO
	private void getAllReasonList() {
		List<OccurrenceReason> rl = new ArrayList<OccurrenceReason>();
		for(int i=0; i<5; i++){
			OccurrenceReason or =new  OccurrenceReason(i, "Reason "+i, "");
			rl.add(or);
		}

		ReflectionServiceAsync async = (ReflectionServiceAsync)GWT.create(ReflectionService.class);
		ServiceDefTarget entryPoint = (ServiceDefTarget) async;
		String entryURL = GWT.getModuleBaseURL() + "getAllOccurrenceReasonList";
		entryPoint.setServiceEntryPoint(entryURL);
		AsyncCallback<List<OccurrenceReason>> callback = new AsyncCallback<List<OccurrenceReason>>(){
			public void onSuccess(List<OccurrenceReason> result){
				allReasonList = result;
				for(int i=0 ; i<allReasonList.size() ; i++){
					checkBoxList.add(new CheckBox( allReasonList.get(i).getReason() ));
					left.add( checkBoxList.get(i) );
				}
				setCheckedReasonList(reflection.getReasonList());
				left.add(other);
				left.add(detail);
			}
			public void onFailure(Throwable caught){
			}
		};
		async.getAllOccurrenceReasonList( callback );
	}

	public List<OccurrenceReason> getCheckedReasonList(){
		List<OccurrenceReason> ans = new ArrayList<OccurrenceReason>();
		for( CheckBox cb : this.getCheckBoxList()){
			if( cb.getValue() ){
				for(OccurrenceReason or : this.allReasonList){
					if( or.getReason().equals(cb.getText() ) ){
						ans.add( or);
					}
				}
			}
		}
		return ans;
	}

	public void setCheckedReasonList(List<OccurrenceReason> checkedResonList){
		if (checkedResonList == null) return;
		for (OccurrenceReason or : checkedResonList){
			for( CheckBox cb : this.getCheckBoxList()){
				if( or.getReason().equals(cb.getText() ) ){
					cb.setValue(true);
				}
			}
		}
	}

	public String getOtherReason(){
		return this.detail.getText();
	}

	public void setOtherReason(String otherReason){
		this.detail.setText(otherReason);
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

	public Label getOther() {
		return other;
	}

	public void setOther(Label other) {
		this.other = other;
	}

	public TextArea getDetail() {
		return detail;
	}

	public void setDetail(TextArea detail) {
		this.detail = detail;
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