package com.objetdirect.gwt.umldrawer.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.objetdirect.gwt.umldrawer.client.beans.Exercise;

public class ExerciseViewPanel extends VerticalPanel{
	Exercise exercise;
	final Label  title = new Label("Title");
	final TextBox titleBox= new TextBox();

	final Label  task = new Label("Task");
	final TextArea taskArea= new TextArea();

	public ExerciseViewPanel(Exercise exercise) {
		super();
		this.exercise = exercise;
		titleBox.setText(exercise.getTitle());
		setFontSize(titleBox, 16);
		taskArea.setText(exercise.getTask());
		setFontSize(taskArea, 16);
		titleBox.setReadOnly(true);
		taskArea.setReadOnly(true);

		this.setSpacing(5);
		this.setHeight((int) (Window.getClientHeight()*0.95)+"px");
		this.setVerticalAlignment(ALIGN_TOP);
		this.add(this.title);
		this.add(this.titleBox);
		this.add(this.task);
		this.add(this.taskArea);
		this.taskArea.setSize("700px", "600px");


	}
	private void setFontSize(Label label, double pt) {
		  label.getElement().getStyle().setFontSize(pt, Unit.PT);
		}
	private void setFontSize(TextArea textArea, double pt) {
		  textArea.getElement().getStyle().setFontSize(pt, Unit.PT);
		}
	private void setFontSize(TextBox textBox, double pt) {
		  textBox.getElement().getStyle().setFontSize(pt, Unit.PT);
		}
}
