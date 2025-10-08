package com.objetdirect.gwt.umlapi.client.yamazaki.replace;

import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umlapi.client.helpers.TextResource;


public class StringSplitSubstring {

	private static StringSplitSubstring singleton = new StringSplitSubstring();

	private StringSplitSubstring()
	{

	}

	public static StringSplitSubstring getInstance()
	{
		return singleton;
	}


	public String splitClassname(String splitString)
	{
		String[] splitarray = new String[2];
		String classname = "";
		String fmname = "";
		String drawertextresource = "リターン文";

		if(splitString.contains("!")) 	// フィールド
		{
			splitarray = splitString.split("!");

			classname = splitarray[0];
			fmname = splitarray[1];

			String fieldName = splitarray[1].substring(0,getTypeVisibilityIndex(fmname));
			String type_or_modifier = FieldMethod_replace(fmname);


			drawertextresource = TextResource.REPLACE_FIELD_STRING.getMessage();
			drawertextresource = drawertextresource.replace("Strawberry",classname);
			drawertextresource = drawertextresource.replace("lemon",fieldName);
			drawertextresource = drawertextresource.replace("Peach",type_or_modifier);
		}
		else if(splitString.contains("%") && splitString.contains("&")) // パラメータ
		{
			splitarray = splitString.split("%");
			classname = splitString.split("&")[0];	//クラス名&メソッド名%

			String methodsplit = splitString.split("&")[1];
			int end_parametere = getrIdnexParameterStart(methodsplit);
			String methodName = methodsplit.substring(0,end_parametere);


			fmname = splitarray[1];
			String parameterName = fmname.substring(0,getTypeVisibilityIndex(fmname));


			// Window.alert("start]" + start_parameter + " end:" + end_parametere+ " name:"+parameterName);
			if(fmname.contains("}"))
			{
				// Convert the parameter type of the method getName in the Strawberry class to Cherry?
				drawertextresource = TextResource.REPLACE_PARAMETER_STRING.getMessage();
				drawertextresource = drawertextresource.replace("Strawberry",classname);
				drawertextresource = drawertextresource.replace("banana",methodName);
				drawertextresource = drawertextresource.replace("apple",parameterName);
			}

		}
		else if(splitString.contains("&")) 	//　メソッド
		{
			splitarray = splitString.split("&");

			classname = splitarray[0];
			fmname = splitarray[1];

			String methodName = splitarray[1].substring(0,getTypeVisibilityIndex(fmname));
			String type_or_modifier = FieldMethod_replace(fmname);

			drawertextresource = TextResource.REPLACE_METHOD_TYPE_STRING.getMessage();
			drawertextresource = drawertextresource.replace("Strawberry",classname);
			drawertextresource = drawertextresource.replace("banana",methodName);
			drawertextresource = drawertextresource.replace("Peach",type_or_modifier);

		}
		else 		// クラス名
		{
			splitarray = splitString.split(";");

			classname = splitarray[0];
			fmname = splitarray[1];

			drawertextresource = TextResource.REPLACE_CLASS_TYPE_STRING.getMessage();
			drawertextresource = drawertextresource.replace("Strawberry",classname);
			drawertextresource = drawertextresource.replace("grape",fmname);
		}

		return drawertextresource;
	}

	private String FieldMethod_replace(	String fmname)
	{
		String type_or_modifier = "type_or_modifier";

		if(fmname.contains("{"))			//　修飾子
			type_or_modifier = TextResource.VISIBILITY.getMessage();
		else if(fmname.contains("}"))		//　型
			type_or_modifier = TextResource.TYPE.getMessage();

		return type_or_modifier;
	}

	private int getTypeVisibilityIndex(String fmname)
	{
		int index = 0;
		if(fmname.contains("{"))			//　修飾子
			index = fmname.indexOf("{");

		else if(fmname.contains("}"))		//　型
			index = fmname.indexOf("}");

		return index;
	}

	private int getrIdnexParameterStart(String fmname)
	{
		int index = 0;
		if(fmname.contains("%"))		// パラメータ
			index = fmname.indexOf("%");

		return index;
	}
	public String splitConstract(String surplusKey)
	{
		String[] splitarray = new String[2];
		String classname = "";
		String fmname = "";
		String drawertextresource = "リターン文";

		splitarray = surplusKey.split("&");

		classname = splitarray[0] + "の";
		fmname = splitarray[1];

		drawertextresource = TextResource.REPLACE_METHOD_TYPE_STRING.getMessage();
		drawertextresource = drawertextresource.replace("Strawberry",classname);
		drawertextresource = drawertextresource.replace("banana",fmname);

		return drawertextresource;

	}

	public String splitFMname(String splitString)
	{
		String[] splitarray = null;

		if(splitString.contains("!")) 								// フィールド
		{
			splitarray = splitString.split("!");
		}
		else if(splitString.contains("%") && splitString.contains("&")) // パラメータ
		{
			splitarray = splitString.split("%");
		}
		else if(splitString.contains("&")) 							//　メソッド
		{
			splitarray = splitString.split("&");
		}
		else 													// クラス名
		{
			splitarray = splitString.split(";");
		}

		String fmname = splitarray[1];

		if(fmname.contains("{"))
			fmname = fmname.replace("{", "の");
		else if(fmname.contains("}"))
			fmname = fmname.replace("}", "の");


		return fmname;
	}

	public String getTypeorVisibility(String type)
	{
		if(type.contains("{"))
			return "visibility";
		else if(type.contains("}"))
			return "type";
		else
			return "name";
	}
}
