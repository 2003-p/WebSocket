package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;

class DiscoveredProblemPanel extends HorizontalPanel{
	private ReflectionEditor reflectionEditor;
	private VerticalPanel left = new VerticalPanel();
	private Label question = new Label(DrawerTextResource.ERROR_DETECTION_MESSAGE.getMessage());
	private Label other = new Label(DrawerTextResource.ERROR_CONTENT_MESSAGE.getMessage());
	private TextArea detail = new TextArea();
	private List<RadioButton> raidioButtonList = new ArrayList<RadioButton>();

	public DiscoveredProblemPanel(ReflectionEditor reflectionEditor){
		this.reflectionEditor = reflectionEditor;
		this.setSpacing(2);
		detail.setSize("250px", "70px");
		this.setWidth("280");
		left.setSpacing(2);
		left.add(question);
		addRadioButtonList();
		left.add(other);
		left.add(detail);
		this.add(left);
	}

	private void addRadioButtonList() {
		raidioButtonList.add(new RadioButton("isThereProblem", DrawerTextResource.YES.getMessage()) );
		raidioButtonList.add(new RadioButton("isThereProblem", DrawerTextResource.NO.getMessage()) );
		left.add( raidioButtonList.get(0) );
		left.add( raidioButtonList.get(1) );
		raidioButtonList.get(1).setValue(true);
	}

	public boolean isThereProblem(){
		return raidioButtonList.get(1).getValue();
	}

	public void setIsThereProblem(boolean isThereProblem){
		if(isThereProblem){
			raidioButtonList.get(0).setValue(false);
			raidioButtonList.get(1).setValue(true);
		}
		else{
			raidioButtonList.get(0).setValue(true);
			raidioButtonList.get(1).setValue(false);
		}
	}

	public String getDetailText() {
		return detail.getText();
	}

	public void setDetailText(String detail) {
		this.detail.setText(detail);
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

	public List<RadioButton> getRaidioButtonList() {
		return raidioButtonList;
	}

	public void setRaidioButtonList(List<RadioButton> raidioButtonList) {
		this.raidioButtonList = raidioButtonList;
	}


}