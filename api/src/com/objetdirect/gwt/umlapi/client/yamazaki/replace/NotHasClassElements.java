package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.helpers.UMLCanvas;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;

public class NotHasClassElements extends AbstDrawReplaceAddDelete {
	private StringSplitSubstring splitsubstringObject;
	private UMLCanvas addTarget;

	private String nothasKey;
	private String nothasValue;


	public NotHasClassElements(String nothasKey,String nothasValue,UMLCanvas umlcanvas)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = umlcanvas;

		setMessage();

		super.setCheckBox();
		//checkbox.setValue(false);
	}

	private void setMessage()
	{
		String[] classElements = nothasValue.split(";");
		String classname = classElements[0];
		super.message = classname + "クラスを追加しますか？";
		// Do you want to add strawberry class?

		super.message = TextResource.CLASS_ADD_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		String[] classElements = nothasValue.split(";");

		if(!nothasKey.contains("&") && !nothasKey.contains("%") && !nothasKey.contains("!"))
		{
			if(classElements.length > 1)
				//　型がある場合
				addTarget.add(new ClassArtifact(classElements[0],classElements[1]));
			else
				// 型がない場合
				addTarget.add(new ClassArtifact(classElements[0]));
		}
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
