package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;

public class SurplusClassElement extends AbstDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLCanvas canvas;
	private ClassArtifact targetClass;

	private String surplusKey;
	private String surplusValue;


	public SurplusClassElement(String surplusKey,String surplusValue,UMLCanvas canvas,ClassArtifact targetClass)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.surplusValue = surplusValue;
		this.canvas = canvas;
		this.targetClass = targetClass;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname= surplusKey.split(";");
		String classname = splitDiffAttributeClassname[0];
		super.message = classname + "クラスを削除しますか？";

		super.message = TextResource.CLASS_REMOVE_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(!surplusKey.contains(";"))
		{
//			targetClass.addYamazakichecngeisSelected();
//			canvas.remove(targetClass);
			canvas.selectArtifact(targetClass);
			canvas.removeSelected();
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
