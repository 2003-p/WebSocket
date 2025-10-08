package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;



public class SimClassElements implements ISimilarity{

	List<IElements> simMatchClass = new ArrayList<IElements>();
	List<IElements> simMisMatchClass = new ArrayList<IElements>();
	private TranseString transeSingleton = TranseString.getInstance();

	public SimClassElements(List<IElements> list,List<IElements> list2)
	{
		this.simMatchClass = list;
		this.simMisMatchClass = list2;
	}

	public void show()
	{
		System.out.println("---------------------");
		System.out.println("---------Class-------");
		if(simMatchClass.size() != 0)
		{
			for(IElements element : simMatchClass)
			{
				if(!element.getNamebool())
					System.out.println("類似度１のクラス名　" + element.getElementName());
			}
		}

		System.out.println("");
		if(simMisMatchClass.size() != 0)
		{
			for(IElements element : simMisMatchClass)
			{
				if(element.getNamebool())
				{
					if(!element.getAccessbool())
						System.out.println(element.getElementName() + "クラスの修飾子不一致　" + transeSingleton.returnAccessSymbol(element.getAccess()));
					if(!element.getTypebool())
						System.out.println(element.getElementName() + "クラスの型不一致　" + element.getElementType());
				}
				else
				{
					System.out.println("類似度０のクラス名　" + element.getElementName());
				}
			}


		}
	}

	@Override
	public Map<String, String> getSimMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		if(simMatchClass.size() != 0)
		{
			for(IElements element : simMatchClass)
			{
				if(element.getNamebool())
				{
					if(!element.getTypebool())
						tmpMap.put(element.getElementName() + ";type", element.getElementType());
				}
				else
				{
					tmpMap.put(element.getElementName(), element.getElementName());
				}
			}
		}
		return tmpMap;
	}

	@Override
	public Map<String, String> getSimMisMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		if(simMisMatchClass.size() != 0)
		{
			for(IElements element : simMisMatchClass)
			{
				if(element.getNamebool())
				{
					if(!element.getTypebool())
						tmpMap.put(element.getElementName() + ";type", element.getElementType());
				}
				else
				{
					tmpMap.put(element.getElementName(), element.getElementName() + ";" + element.getElementType());
				}

			}
		}
		return tmpMap;
	}
	
	
	
	
}
