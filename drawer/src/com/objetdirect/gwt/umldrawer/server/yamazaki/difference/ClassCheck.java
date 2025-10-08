package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



public class ClassCheck{

	private static ClassCheck singleton = new ClassCheck();

	public static ClassCheck getInstance()
	{
		return singleton;
	}

	public DiffClassElements checkClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap)
	{
		return new DiffClassElements(nothasClass(kifu_classMap,umlds_classMap), surplusClass(kifu_classMap,umlds_classMap));
	}

	public List<IElements> nothasClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<IElements> nothasClass = new ArrayList<IElements>();

		for(String umldsKey : umlds_classMap.keySet())
		{
			if(kifu_classMap.get(umldsKey) == null)
			{
				nothasClass.add(umlds_classMap.get(umldsKey));
			}
			else
			{
				umlds_classMap.get(umldsKey).changeNamebool();
				if(!umlds_classMap.get(umldsKey).getElementType().equals(kifu_classMap.get(umldsKey).getElementType()))
				{
					System.out.println("Eurice kifuname:" + kifu_classMap.get(umldsKey).getNamebool() + " umldsname:" + umlds_classMap.get(umldsKey).getNamebool());
					System.out.println("Eurice kifuname:" + kifu_classMap.get(umldsKey).getElementName() + " umldsname:" + umlds_classMap.get(umldsKey).getElementName());
					nothasClass.add(umlds_classMap.get(umldsKey));
				}
			}
		}
		
		System.out.println("nothasClass::"+nothasClass);//saito
		
		return nothasClass;
	}


	public List<IElements> surplusClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<IElements> surplusClass = new ArrayList<IElements>();

		for(String kifuKey : umlds_classMap.keySet())
		{
			System.out.println("umlds:" + kifuKey + "=");
		}
		for(String kifuKey : kifu_classMap.keySet())
		{
			System.out.println("kifuKey:" + kifuKey + "=");
			if(umlds_classMap.get(kifuKey) == null)
			{
				System.out.println("クラスが余剰kifuKey:" + kifuKey);
				surplusClass.add(kifu_classMap.get(kifuKey));
			}
			else
			{
				kifu_classMap.get(kifuKey).changeNamebool();
				if(!umlds_classMap.get(kifuKey).getElementType().equals(kifu_classMap.get(kifuKey).getElementType()))
				{
					surplusClass.add(kifu_classMap.get(kifuKey));
				}
			}
		}

		System.out.println("surplusClass::"+surplusClass);//saito
		
		return surplusClass;
	}

}
