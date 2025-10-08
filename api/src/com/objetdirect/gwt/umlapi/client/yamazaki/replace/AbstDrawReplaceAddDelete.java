package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TextBox;

public abstract class AbstDrawReplaceAddDelete {

	protected TextBox reson;
	protected String message;
	protected CheckBox checkbox;


	abstract public void drawReplaceAddDelete();
	abstract public String getSurplusKey();
	abstract public String getSurplusValue();
	abstract public String getNothasKey();
	abstract public String getNothasValue();

	protected AbstDrawReplaceAddDelete()
	{
		this.checkbox = new CheckBox();
		this.reson = new TextBox();
		this.message = new String();
	}

	protected void setCheckBox()
	{
		this.checkbox = new CheckBox(this.message);
	}


	public CheckBox getCheckBox()
	{
		return this.checkbox;
	}

	public String getMessage()
	{
		return this.message;
	}

	public TextBox getResonComponent()
	{
		return this.reson;
	}

	public String getResonText()
	{
		return this.reson.getText();
	}

	public void setTextBoxWidth(String width)
	{
		this.reson.setWidth(width);
	}
}
