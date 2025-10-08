package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

public class SurplusParameterElement extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private UMLParameter targetpara;
	private UMLClassMethod targetMethod;
	private ClassArtifact roleRebuild;

	private String surplusKey;
	private String surplusValue;


	public SurplusParameterElement(String surplusKey,String surplusValue,UMLClassMethod targetMethod,UMLParameter targetpara,ClassArtifact roleRebuild)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.targetpara = targetpara;
		this.targetMethod = targetMethod;
		this.roleRebuild = roleRebuild;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname = surplusKey.split("&");
		String classname = splitDiffAttributeClassname[0];

		String[] splitMethodName = surplusKey.split("%");
		int indexAndMark = splitMethodName[0].indexOf("&") + 1;
		String substringMethodName = splitMethodName[0].substring(indexAndMark,splitMethodName[0].length());
		super.message = classname + "クラスの" + substringMethodName + "メソッドのパラメータ" + surplusValue + "を削除しますか？";

		super.message = TextResource.PARAMETER_REMOVE_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("banana", substringMethodName);
		super.message = super.message.replace("apple", surplusValue);
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(surplusKey.contains("&"))
		{
			targetMethod.removeParameter(targetpara);
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
