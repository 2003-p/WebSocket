package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class NotHasMethodElements extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact addTarget;

	private String surplusKey;
	private String nothasValue;


	public NotHasMethodElements(String surplusKey,String nothasValue,ClassArtifact component)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.nothasValue = nothasValue;
		this.addTarget = component;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffMethodKey = surplusKey.split("&");
		String classname = splitDiffMethodKey[0];
		super.message = classname + "に" + nothasValue + "を追加しますか？";

		super.message = TextResource.METHOD_ADD_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("banana", nothasValue);
		// 英語　"Do you want to add banana in the strawberry?"
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("&")) 							//　メソッド
		{
			addTarget.addMethod(UMLClassMethod.parseMethod(nothasValue));
			addTarget.rebuildGfxObject();

		}

	}

	@Override
	public String getSurplusKey()
	{
		return this.surplusKey;
	}

	public String getSurplusValue()
	{
		return "ReplaceElementsのgetSurplusValueはなし";
	}

	public String getNothasKey()
	{
		return "ReplaceElementsのgetNothasKeyはなし";
	}

	public String getNothasValue()
	{
		return this.nothasValue;
	}

}
