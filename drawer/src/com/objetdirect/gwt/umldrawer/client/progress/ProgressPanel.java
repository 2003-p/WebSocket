package com.objetdirect.gwt.umldrawer.client.progress;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassRelationLinkArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.CanvasUrlManager;
import com.objetdirect.gwt.umldrawer.client.beans.EditEvent;
import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;

public class ProgressPanel extends HorizontalPanel{
	private String studentId;
	private EditEvent lastEditEvent;
	private GraphPanel graphPanel;
	private List<UMLArtifact>answer;
	private int hour;
	private int minite;
	private int second;
	private int classNum;
	private int relNum;
	private SimilarityManager sm;
	private VerticalPanel dataPanel;


	public ProgressPanel(String studentId, List<EditEvent> eventList, List<UMLArtifact> answer) {
		super();
		this.studentId = studentId;
		this.answer = answer;
		//count fields from edit event list

		if(eventList.size()==0 || eventList==null) return;



		this.setSize("800px", "400px");
		this.sm = new SimilarityManager();
		this.graphPanel = new GraphPanel(sm.createDataForGraph(eventList, answer), studentId, this);
		this.add(graphPanel);

		//dataPanel
		this.dataPanel = new VerticalPanel();
		this.lastEditEvent = eventList.get(eventList.size()-1);

		CanvasUrlManager cum = new CanvasUrlManager();

		this.classNum = classCount( cum.fromURL(lastEditEvent.getCanvasUrl(), false) );
		this.relNum = relationCount( cum.fromURL(lastEditEvent.getCanvasUrl(), false) );

		Label classNumLabel = new Label("Classes : "+this.classNum);
		setFontSize(classNumLabel, 5);
		classNumLabel.setSize("400px", "100px");
		dataPanel.add( classNumLabel );

		Label relationNumLabel = new Label("Relations : "+this.relNum);
		setFontSize(relationNumLabel, 5);
		relationNumLabel.setSize("400px", "100px");
		dataPanel.add( relationNumLabel );

		Label eventNumLabel = new Label("Edits : "+eventList.size());
		setFontSize(eventNumLabel, 5);
		eventNumLabel.setSize("400px", "100px");
		dataPanel.add( eventNumLabel );

		this.add(dataPanel);

	}

	private void setFontSize(Label label, double ems) {
		  label.getElement().getStyle().setFontSize(ems, Unit.EM);
		}

	private int classCount(List<UMLArtifact> diagram) {
		int classCount = 0;
		for(UMLArtifact artifact : diagram){
			if(artifact.getClass()==ClassArtifact.class){
				classCount++;
			}
		}
		return classCount;
	}

	private int relationCount(List<UMLArtifact> diagram) {
		int relCount = 0;
		for(UMLArtifact artifact : diagram){
			if(artifact.getClass()==ClassRelationLinkArtifact.class){
				relCount++;
			}
		}
		return relCount;
	}



}
