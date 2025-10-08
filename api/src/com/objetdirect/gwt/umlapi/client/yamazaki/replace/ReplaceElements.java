package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLNode;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

public class ReplaceElements extends AbstDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLNode replaceTarget;
	private UMLArtifact roleRebuild;

	private String surplusKey;
//	private String surplusValue;
//	private String nothasKey;
	private String nothasValue;


	public ReplaceElements(String surplusKey,
							String nothasValue,
							UMLNode component,
							UMLArtifact roleRebuild)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.surplusKey = surplusKey;
//		this.surplusValue = surplusValue;
//		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.replaceTarget = component;
		this.roleRebuild =  roleRebuild;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	public void drawReplaceAddDelete()
	{
		if(surplusKey.contains("{"))
		{
			char[] tmp = nothasValue.toCharArray();
			replaceTarget.setVisibility(UMLVisibility.getVisibilityFromToken(tmp[0]));
			replaceTarget.setStrokeBLACK(replaceTarget.getDiffVisibilityKey());
			roleRebuild.rebuildGfxObject();
			//roleRebuild.moveTo(roleRebuild.getLocation());


		}
		else if(surplusKey.contains("}"))
		{
			replaceTarget.setType(nothasValue);
			replaceTarget.setStrokeBLACK(replaceTarget.getDiffTypeKey());
			roleRebuild.rebuildGfxObject();
			//roleRebuild.moveTo(roleRebuild.getLocation());
		}
	}

	private void setMessage()
	{
		//  何が入っているか　？？？の？？？をまで
		String classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);

		if(nothasValue.equals("") && surplusKey.contains("&") && surplusKey.contains("}"))
		{
			// super.message =  classnamefmname + "型なしのコンストラクタに変換";
			// classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);
			super.message = classnamefmname.replace("cherry", TextResource.CONSTRACTAR.getMessage());
		}
		else
		{
			// super.message = classnamefmname + nothasValue + "に変換";
			// classnamefmname = splitsubstringObject.splitClassname(this.surplusKey);
			super.message = classnamefmname.replace("cherry", nothasValue);
		}
	}

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
