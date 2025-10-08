package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;

public class AssumedSituationPanel extends HorizontalPanel{
	private ReflectionEditor reflectionEditor;
	private VerticalPanel left;
	private Label question;
	private TextArea situation;

	public AssumedSituationPanel(ReflectionEditor reflectionEditor){
		this.reflectionEditor = reflectionEditor;
		this.setSpacing(2);
		this.setWidth("280px");
		left = new VerticalPanel();
		left.setSpacing(2);
		this.add(left);

		question = new Label(DrawerTextResource.DESCRIBE_THINKINGS.getMessage());
		left.add(question);
		situation = new TextArea();
		situation.setSize("250px", "70px");
		left.add(situation);
	}

	public String getAssumedSituation(){
		return situation.getText();
	}

	public void setAssumedSituation(String situation){
		this.situation.setText(situation);
	}
}
