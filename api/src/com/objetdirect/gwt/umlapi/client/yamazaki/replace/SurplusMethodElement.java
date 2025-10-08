package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class SurplusMethodElement extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact targetClassArtifact;
	private UMLClassMethod targetMethod;

	private String surplusKey;
	private String surplusValue;


	public SurplusMethodElement(String surplusKey,String surplusValue,ClassArtifact component,UMLClassMethod targetMethod)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetClassArtifact = component;
		this.targetMethod = targetMethod;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split("&");
		String classname = splitDiffAttributeClassname[0];
		super.message = classname + "の" + surplusValue + "を削除しますか？";

		super.message = TextResource.METHOD_REMOVE_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("banana", surplusValue);
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("&"))
		{
			targetClassArtifact.removeMethod(targetMethod);
			targetClassArtifact.rebuildGfxObject();
		}
	}

	@Override
	public String getSurplusKey() {
		// TODO 自動生成されたメソッド・スタブ
		return surplusKey;
	}

	@Override
	public String getSurplusValue() {
		// TODO 自動生成されたメソッド・スタブ
		return surplusValue;
	}

	@Override
	public String getNothasKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getNothasValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
