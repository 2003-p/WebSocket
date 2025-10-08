package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.MethodParametar;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;


public class DiffMethodElements implements IDifference {

	TranseString tranceAccessSingleton = TranseString.getInstance();


	Map<String,List<IElements>> nothasMethods = new HashMap<String,List<IElements>>();
	Map<String,List<IElements>> surplusMethods = new HashMap<String,List<IElements>>();
	private TranseString transeSingleton = TranseString.getInstance();

	DiffMethodElements(Map<String,List<IElements>> nothasMethods,Map<String,List<IElements>> surplusMethods)
	{
		this.nothasMethods = nothasMethods;
		this.surplusMethods = surplusMethods;
	}

	public void show() {
	// TODO 自動生成されたメソッド・スタブ
		System.out.println("---------------------");
		System.out.println("----Method----");


		System.out.println(" ");
		for(String classname : nothasMethods.keySet())
		{
			System.out.println("欠損メソッド" + classname);
			for(IElements method : nothasMethods.get(classname))
				System.out.println("欠損メソッド" + method.getElementName());
			System.out.println();
		}
		for(String classname : surplusMethods.keySet())
		{
			System.out.println("足りないクラス名 " + classname);
			for(IElements method : surplusMethods.get(classname))
			{
				System.out.println("欠損メソッド名　" + method.getElementName());
				if(!method.getAccessbool())
					System.out.println("修飾子の不一致　" + transeSingleton.returnAccessSymbol(method.getAccess()));
				if(!method.getTypebool())
					System.out.println("戻り値の不一致　" + method.getElementType());
				System.out.println();
			}
		}
	}



	@Override
	public Map<String, String> getDiffSurplusMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String classname : surplusMethods.keySet())
		{
			for(IElements method : surplusMethods.get(classname))
			{
				if(!method.getNamebool())
				{
					String access = tranceAccessSingleton.returnAccessSymbol(method.getAccess());
					List<MethodParametar> paraList = method.getMethodParameter();

					String paraString = "";
					for(MethodParametar tmpparameter : paraList)
					{
						paraString = paraString + tmpparameter.getName() + ":" + tmpparameter.getType() + ",";
					}
					if(paraString.length() > 0)
						paraString = paraString.substring(0,paraString.length() - 1);
					tmpMap.put(classname + "&" +  method.getElementName(), access + method.getElementName() + "(" + paraString + "):" + method.getElementType());
				}
				else
				{
					if(!method.getAccessbool())
					{
						String access = tranceAccessSingleton.returnAccessSymbol(method.getAccess());


						tmpMap.put(classname + "&" +  method.getElementName() + "{visibility", access);
					}
					if(!method.getTypebool())
					{
						tmpMap.put(classname + "&" +  method.getElementName() + "}type",  method.getElementType());
					}
				}
			}
		}
		return tmpMap;

	}

	@Override
	public Map<String, String> getDiffNotHasMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String classname : nothasMethods.keySet())
		{
			for(IElements method : nothasMethods.get(classname))
			{
				if(!method.getNamebool())
				{
					String access = tranceAccessSingleton.returnAccessSymbol(method.getAccess());
					List<MethodParametar> paraList = method.getMethodParameter();

					String paraString = "";
					for(MethodParametar tmpparameter : paraList)
					{
						paraString = paraString + tmpparameter.getName() + ":" + tmpparameter.getType() + ",";
					}
					if(paraString.length() > 0)
						paraString = paraString.substring(0,paraString.length() - 1);
					else
						;


					if(method.getElementType().equals(""))
						tmpMap.put(classname + "&" +  method.getElementName(), access + method.getElementName() + "(" + paraString + ")" );
					else
						tmpMap.put(classname + "&" +  method.getElementName(), access + method.getElementName() + "(" + paraString + "):" + method.getElementType() );

				}
				else
				{
					if(!method.getAccessbool())
					{
						String access = tranceAccessSingleton.returnAccessSymbol(method.getAccess());
						tmpMap.put(classname + "&" +  method.getElementName() + "{visibility", access);
					}
					if(!method.getTypebool())
					{
						tmpMap.put(classname + "&" +  method.getElementName() + "}type", method.getElementType());
					}
				}
			}
		}

		return tmpMap;
	}


}
