package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;



public class SimFieldElements implements ISimilarity {

	TranseString tranceAccessSingleton = TranseString.getInstance();

	Map<String,List<IElements>> matchFields = new HashMap<String,List<IElements>>();
	Map<String,List<IElements>> misMatchFields = new HashMap<String,List<IElements>>();
	private TranseString transeSingleton = TranseString.getInstance();

	SimFieldElements(Map<String,List<IElements>> matchFields,Map<String,List<IElements>> misMatchFields)
	{
		this.matchFields = matchFields;
		this.misMatchFields = misMatchFields;
	}

	public void show() {
	// TODO 自動生成されたメソッド・スタブ
		System.out.println("");
		System.out.println("---------------------");
		System.out.println("--------Ffield-------");

		for(String classname : matchFields.keySet())
		{
			System.out.println("クラス名 " + classname);
			for(IElements field : matchFields.get(classname))
				System.out.println("類似度１の属性　" + field.getElementName());
			System.out.println();
		}
		System.out.println(" ");
		for(String classname : misMatchFields.keySet())
		{
			System.out.println("クラス名 " + classname);
			for(IElements field : misMatchFields.get(classname))
			{
				System.out.println("類似度０の属性　" + field.getElementName());
//				if(!field.getAccessbool())
//					System.out.println("修飾子の不一致　" + transeSingleton.returnAccessSymbol(field.getAccess()));
//				if(!field.getTypebool())
//					System.out.println("型の不一致　" + field.getElementType());
				System.out.println();
			}
		}

	}

	@Override
	public Map<String, String> getSimMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String classname : misMatchFields.keySet())
		{
			for(IElements field : misMatchFields.get(classname))
			{
				if(!field.getNamebool())
				{
					String access = tranceAccessSingleton.returnAccessSymbol(field.getAccess());
					tmpMap.put(classname + "!" +  field.getElementName(), access + field.getElementName() + ":" + field.getElementType());
				}
				else
				{
					if(!field.getAccessbool())
					{
						String access = tranceAccessSingleton.returnAccessSymbol(field.getAccess());
						tmpMap.put(classname + "!" +  field.getElementName() + "{visibility", access);
					}
					if(!field.getTypebool())
					{
						tmpMap.put(classname + "!" +  field.getElementName() + "}type", field.getElementType());
					}
				}
			}
		}
		return tmpMap;
	}

	@Override
	public Map<String, String> getSimMisMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String classname : matchFields.keySet())
		{
			for(IElements field : matchFields.get(classname))
			{
				if(!field.getNamebool())
				{
					String access = tranceAccessSingleton.returnAccessSymbol(field.getAccess());
					tmpMap.put(classname + "!" +  field.getElementName(), access + field.getElementName() + ":" + field.getElementType());
				}
				else
				{
					if(!field.getAccessbool())
					{
						String access = tranceAccessSingleton.returnAccessSymbol(field.getAccess());
						tmpMap.put(classname + "!" +  field.getElementName() + "{visibility", access);
					}
					if(!field.getTypebool())
					{
						tmpMap.put(classname + "!" +  field.getElementName() + "}type", field.getElementType());
					}
				}

			}
		}
		return tmpMap;
	}
}
