package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anotherbigidea.flash.movie.Shape.Element;
import com.google.gwt.user.client.Window;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;



public class DiffClassElements implements IDifference{

	List<IElements> nothasClass = new ArrayList<IElements>();
	List<IElements> surplusClass = new ArrayList<IElements>();
	private TranseString transeSingleton = TranseString.getInstance();

	DiffClassElements(List<IElements> list,List<IElements> list2)
	{
		this.nothasClass = list;
		this.surplusClass = list2;
	}

	public void show()
	{
		System.out.println("---------------------");
		System.out.println("-----Class----");
		System.out.println("不足クラス");
		if(nothasClass.size() != 0)
		{
			for(IElements element : nothasClass)
			{
				if(!element.getNamebool())
					System.out.println("欠損のクラス名　" + element.getElementName());
				if(!element.getTypebool())
					System.out.println("欠損の型　" + element.getElementType());
			}
		}

		System.out.println("");
		System.out.println("余剰クラス");
		if(surplusClass.size() != 0)
		{
			for(IElements element : surplusClass)
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
					System.out.println("余剰のクラス名　" + element.getElementName());
				}
			}


		}
	}

	@Override
	public Map<String, String> getDiffSurplusMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		if(surplusClass.size() != 0)
		{
			for(IElements element : surplusClass)
			{
				if(element.getNamebool())
				{
//					if(!element.getAccessbool())
//						tmpMap.put(element.getElementName() + ";visibility", transeSingleton.returnAccessSymbol(element.getAccess()));
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
	public Map<String, String> getDiffNotHasMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		if(nothasClass.size() != 0)
		{
			for(IElements element : nothasClass)
			{
				if(element.getNamebool())
				{
//					if(!element.getAccessbool())
//						tmpMap.put(element.getElementName() + ";visibility",  transeSingleton.returnAccessSymbol(element.getAccess()));
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
