package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;

public class NotHasClassTypeElements extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private ClassArtifact addTarget;

	private String nothasKey;
	private String nothasValue;

	public NotHasClassTypeElements(String nothasKey,String nothasValue,ClassArtifact addTarget)
	{
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = addTarget;

		setMessage();
		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] classElements = nothasKey.split(";");
		String classname = classElements[0];
		super.message = classname + "クラスに " + nothasValue + "を追加しますか？";

		super.message = TextResource.CLASS_TYPE_ADD_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("banana", nothasValue);
		// 英語　"Do you want to add + grape in the classname?"
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		addTarget.getClassPartNameArtifact().setStereotype(nothasValue);
		addTarget.rebuildGfxObject();
	}

	@Override
	public CheckBox getCheckBox() {
		// TODO 自動生成されたメソッド・スタブ
		return checkbox;
	}

	@Override
	public String getSurplusKey() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getSurplusValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getNothasKey() {
		// TODO 自動生成されたメソッド・スタブ
		return nothasKey;
	}

	@Override
	public String getNothasValue() {
		// TODO 自動生成されたメソッド・スタブ
		return nothasValue;
	}

}
