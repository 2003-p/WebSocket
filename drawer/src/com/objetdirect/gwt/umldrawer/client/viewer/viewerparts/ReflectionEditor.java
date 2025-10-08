package com.objetdirect.gwt.umldrawer.client.viewer.viewerparts;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.DrawerTextResource;
import com.objetdirect.gwt.umldrawer.client.beans.Reflection;
import com.objetdirect.gwt.umldrawer.client.helpers.DrawerSession;

public class ReflectionEditor extends DialogBox{
	private Reflection reflection;
	private ReflectionPanel reflectionPanel;
	private Button submitButton;
	private Button closeButton;

	private VerticalPanel basePanel;
	private WhyReflectPanel whyReflectPanel;
	private CheckedItemPanel checkedItemPanel;
	private AssumedSituationPanel assumedSituationPanel;
	private DiscoveredProblemPanel discoveredProblemPanel;

	public ReflectionEditor(Reflection reflection, ReflectionPanel reflectionPanel, int width, int height) {
		super();
		this.setText( DrawerTextResource.SC_EDITOR.getMessage() );

		this.reflection = reflection;
		this.reflectionPanel = reflectionPanel;
		this.basePanel = new VerticalPanel();
		this.submitButton = new Button(DrawerTextResource.SAVE_AND_CLOSE.getMessage() , new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateReflection();
				ReflectionEditor.this.reflectionPanel.updateByNewReflection(ReflectionEditor.this.reflection);
				ReflectionEditor.this.hide();
			}
		});
		this.closeButton = new Button(DrawerTextResource.JUST_CLOSE.getMessage(), new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ReflectionEditor.this.hide();
			}
		});
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setSpacing(10);

		submitButton.setSize("100px", "50px");
		closeButton.setSize("100px", "50px");

		basePanel.setSize("600px", "600px");
		basePanel.setSpacing(2);
		this.setWidget(basePanel);

		HorizontalPanel hori1 = new HorizontalPanel();
		basePanel.add(hori1);
		whyReflectPanel = new WhyReflectPanel(this);
		//hori1.add(whyReflectPanel);

		checkedItemPanel = new CheckedItemPanel(this);
		hori1.add(checkedItemPanel);

		VerticalPanel ver01 = new VerticalPanel();
		hori1.add(ver01);
		assumedSituationPanel = new AssumedSituationPanel(this);
		ver01.add(assumedSituationPanel);
		discoveredProblemPanel = new DiscoveredProblemPanel(this);
		ver01.add(discoveredProblemPanel);

		//他人のreflectionを書き換えできないように
		if(DrawerSession.student.getStudentId().equals(reflection.getStudentId())){
			buttons.add(submitButton);
		}
		buttons.add(closeButton);
		basePanel.add(buttons);

		setValues();
	}

	private void setValues() {
		whyReflectPanel.setCheckedReasonList( reflection.getReasonList() );
		whyReflectPanel.setOtherReason( reflection.getOtherReason() );
		checkedItemPanel.setCheckedItemList( reflection.getCheckedItemList() );
		assumedSituationPanel.setAssumedSituation( reflection.getImagedSituation() );
		discoveredProblemPanel.setIsThereProblem(reflection.isThereProblem() );
		discoveredProblemPanel.setDetailText(reflection.getDiscoveredProblems() );
	}

	private void updateReflection(){
		reflection.setReasonList( whyReflectPanel.getCheckedReasonList() );
		reflection.setOtherReason( whyReflectPanel.getOtherReason() );
		reflection.setCheckedItemList( checkedItemPanel.getCheckedItemList() );
		reflection.setImagedSituation( assumedSituationPanel.getAssumedSituation() );
		reflection.setIsThereProblem( discoveredProblemPanel.isThereProblem() );
		reflection.setDiscoveredProblems( discoveredProblemPanel.getDetailText() );
	}

	public Reflection getReflection() {
		return reflection;
	}

	public void setReflection(Reflection reflection) {
		this.reflection = reflection;
	}

}
