package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartNameArtifact;

public class ReplaceClassStereoType extends AbstDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact replaceTarget;

	private String surplusKey;
	private String nothasValue;


	public ReplaceClassStereoType(String surplusKey,String nothasValue,ClassArtifact component)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
		this.nothasValue = nothasValue;
		this.replaceTarget = component;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	public void drawReplaceAddDelete()
	{
		if(surplusKey.contains(";")) // 本当はやりたくないがわからない
		{
			replaceTarget.getClassPartNameArtifact().setStereotype(nothasValue);
			// 置換したら黒に戻す
			ClassPartNameArtifact tmpColorREDtoBLACKObject = replaceTarget.getClassPartNameArtifact();
			tmpColorREDtoBLACKObject.getUMLClass().setStrokeBLACK(surplusKey);
			replaceTarget.rebuildGfxObject();

		}
	}

	private void setMessage()
	{
		//  何が入っているか　？？？の？？？をまで
		String classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);

		String appendReplaceString = classnamefmname + nothasValue + "に変換";
		super.message = appendReplaceString;

		super.message = classnamefmname;
		super.message = super.message.replace("cherries", nothasValue);
	}


	public String getSurplusKey()
	{
		return this.surplusKey;
	}

	public String getSurplusValue()
	{
		//return this.surplusValue;
		return null;
	}

	public String getNothasKey()
	{
		return null;
		//return this.nothasKey;
	}

	public String getNothasValue()
	{
		return this.nothasValue;
	}

}
