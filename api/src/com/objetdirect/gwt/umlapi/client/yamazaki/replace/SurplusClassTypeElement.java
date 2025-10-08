package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

public class SurplusClassTypeElement extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassPartNameArtifact targetClass;
	private UMLArtifact roleRebuild;

	private String surplusKey;
	private String surplusValue;


	public SurplusClassTypeElement(String surplusKey,String surplusValue,ClassPartNameArtifact targetClass,UMLArtifact roleRebuild)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetClass = targetClass;
		this.roleRebuild = roleRebuild;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split(";");
		String classname = splitDiffAttributeClassname[0];
		//String type = splitDiffAttributeClassname[1];
		super.message = classname + "クラスの" + surplusValue + "を削除しますか？";

		super.message = TextResource.CLASS_TYPE_REMOVE_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("grape", surplusValue);
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(targetClass != null)
		{
			targetClass.setStereotype("");
			roleRebuild.rebuildGfxObject();
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
