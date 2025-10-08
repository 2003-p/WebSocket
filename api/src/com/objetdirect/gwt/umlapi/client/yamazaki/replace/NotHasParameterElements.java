package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.ClassPartMethodsArtifact;
import com.objetdirect.gwt.umlapi.client.artifacts.UMLArtifact;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

public class NotHasParameterElements extends AbstDrawReplaceAddDelete {

	private StringSplitSubstring splitsubstringObject;
	private UMLClassMethod addTarget;
	private ClassArtifact roleRebuild;

	private String nothasKey;
	private String nothasValue;

	public NotHasParameterElements(String nothasKey,String nothasValue,UMLClassMethod method,ClassArtifact roleRebuild)
	{
		super();
		splitsubstringObject = StringSplitSubstring.getInstance();
		this.nothasKey = nothasKey;
		this.nothasValue = nothasValue;
		this.addTarget = method;
		this.roleRebuild = roleRebuild;
		setMessage();

		super.setCheckBox();
	}

	private void setMessage()
	{
		String[] splitDiffAttributeClassname = nothasKey.split("&");
		String classname = splitDiffAttributeClassname[0];

		String[] splitMethodName = nothasKey.split("%");
		int indexAndMark = splitMethodName[0].indexOf("&") + 1;
		String substringMethodName = splitMethodName[0].substring(indexAndMark,splitMethodName[0].length());
		super.message = classname + "クラスの" + substringMethodName + "メソッドにパラメータ" + nothasValue + "を追加しますか？";

		super.message = TextResource.PARAMETER_ADD_STRING.getMessage();
		super.message = super.message.replace("Strawberry", classname);
		super.message = super.message.replace("banana", substringMethodName);
		super.message = super.message.replace("apple",nothasValue);

	}

	@Override
	public void drawReplaceAddDelete() {
		// TODO 自動生成されたメソッド・スタブ
		if(addTarget != null && nothasKey.contains("%") && nothasKey.contains("&"))
		{
			String[] splitPara = nothasValue.split(":");
			addTarget.addParametar(new UMLParameter(splitPara[1], splitPara[0]));

			roleRebuild.rebuildGfxObject();
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
