package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;


public class FieldCheck extends AbstractDfferenceMethodsClass{

	private static FieldCheck singleton = new FieldCheck();

	private FieldCheck()
	{

	}

	public static FieldCheck getInstance()
	{
		return singleton;
	}

//	public void show() {
//		// TODO 自動生成されたメソッド・スタブ
//		System.out.println("---------------------");
//		System.out.println("-----Ffield----");
//
//		for(String classname : nothasFields.keySet())
//		{
//			System.out.println("クラス名 " + classname);
//			for(IElements field : nothasFields.get(classname))
//				System.out.println("nothasFields " + field.getElementName());
//		}
//		System.out.println(" ");
//		for(String classname : surplusFields.keySet())
//		{
//			System.out.println("クラス名 " + classname);
//			for(IElements field : surplusFields.get(classname))
//				System.out.println("surplusFields " + field.getElementName());
//		}
//
//	}

	public DiffFieldElements checkField(HashMap<String, List<IElements>> kifu_fmMap,HashMap<String, List<IElements>> umlds_fmMap)
	{
		return new DiffFieldElements(nothasElements(kifu_fmMap,umlds_fmMap),surplusElements(kifu_fmMap,umlds_fmMap));
	}

}
