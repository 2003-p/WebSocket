package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitElement {

	private static  SplitElement singleton = new SplitElement();

	private SplitElement()
	{

	}

	public static SplitElement getInstance()
	{
		return singleton;
	}

	public Map<String,List<String>> splitParametarNameType(String parametars)
	{
		List<String> splitNameList = new ArrayList<String>();
		List<String> splitTypeList = new ArrayList<String>();

		String[] splitComma = parametars.split(",",0);

		for(String parametar: splitComma)
		{
			if(0 == parametar.length())
			{
				System.out.println("パラメータがない:" + parametar.length());
				break;
			}
			parametar = parametar.replaceAll(" ","");

			int indexColon = parametar.indexOf(":");

			splitNameList.add(parametar.substring(0,indexColon));
			splitTypeList.add(parametar.substring(indexColon + 1,parametar.length()));
			System.out.println("名前　" + parametar.substring(0,indexColon));
			System.out.println("型　" + parametar.substring(indexColon + 1,parametar.length()));

		}

		// マップに名前と型のリストを入れる
		Map<String,List<String>> tmpParametarMap = new HashMap<String,List<String>>();
		tmpParametarMap.put("Name",splitNameList);
		tmpParametarMap.put("Type",splitTypeList);

		return tmpParametarMap;

	}

	public String[] returnSplitField(String splitString)
	{
		return splitString.split(":",0);
	}

	public int returnSplitAccess(String access)
	{
		int returnAccessNum = -1;

		// CoRに直す
		if(access.contains("~"))
			returnAccessNum = 0;
		else if(access.contains("+"))
			returnAccessNum = 1;
		else if(access.contains("-"))
			returnAccessNum = 2;
		else if(access.contains("#"))
			returnAccessNum = 4;
		else
			System.out.println("returnSplitAccess:UMLに修飾子がついてない");


		return returnAccessNum;
	}


}
