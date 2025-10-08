package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassAttribute;


public class SurplusAttributeElement extends AbstDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact targetClassArtifact;
	private UMLClassAttribute targetAttribute;

	private String surplusKey;
	private String surplusValue;

	public SurplusAttributeElement(String surplusKey,String surplusValue,ClassArtifact component,UMLClassAttribute targetAttribute)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetClassArtifact = component;
		this.targetAttribute = targetAttribute;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split("!");
		String classname = splitDiffAttributeClassname[0];

		super.message = classname + "の" + surplusValue + "を削除しますか？";

		super.message = TextResource.FIELD_REMOVE_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("lemon", surplusValue);

	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("!"))
		{
			targetClassArtifact.removeAttribute(targetAttribute);
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
