package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.MethodParametar;


public class DiffParaElements implements IDifference {

	Map<String,List<MethodParametar>> surplusPaarametar = new HashMap<String,List<MethodParametar>>();
	Map<String,List<MethodParametar>> nothasParametar = new HashMap<String,List<MethodParametar>>();

	public DiffParaElements(Map<String,List<MethodParametar>> nothas,Map<String,List<MethodParametar>> surplus)
	{
		this.surplusPaarametar = surplus;
		this.nothasParametar = nothas;
	}

	public void show() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("---------------------");
		System.out.println("----Parametar----");

		System.out.println(" ");
		for(String methodName : nothasParametar.keySet())
		{
			System.out.println("欠損メソッド名 " + methodName);
			for(MethodParametar para : nothasParametar.get(methodName))
			{
				if(!para.getNamebool())
					System.out.println("欠損パラメータ名 " + methodName + "%" + para.getName());
				if(!para.getTypebool())
					System.out.println("欠損パラメータ名 " + methodName + "%" + para.getName() + "}" + para.getType());
				System.out.println();

				System.out.println();
			}
		}

		for(String methodName : surplusPaarametar.keySet())
		{
			System.out.println("余剰メソッド名 " + methodName);
			for(MethodParametar para : surplusPaarametar.get(methodName))
			{
				if(!para.getNamebool())
					System.out.println("余剰パラメータ名 " + methodName + "%" + para.getName());
				if(!para.getTypebool())
					System.out.println("余剰パラメータ名 " + methodName + "%" + para.getName() + "}" + para.getType());
				System.out.println();
			}

		}
	}


	@Override
	public Map<String, String> getDiffSurplusMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String methodname : surplusPaarametar.keySet())
		{
			for(MethodParametar para : surplusPaarametar.get(methodname))
			{
				if(!para.getNamebool())
					tmpMap.put(methodname + "%" +  para.getName(), para.getName() + ":" + para.getType());
				else if(!para.getTypebool())
					tmpMap.put(methodname + "%" +  para.getName() + "}type", para.getType());

			}
		}

		return tmpMap;

	}

	@Override
	public Map<String, String> getDiffNotHasMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,String> tmpMap = new HashMap<String,String>();

		for(String methodname : nothasParametar.keySet())
		{
			for(MethodParametar para : nothasParametar.get(methodname))
			{
				if(!para.getNamebool())
					tmpMap.put(methodname + "%" +  para.getName(), para.getName() + ":" + para.getType());
				else if(!para.getTypebool())
					tmpMap.put(methodname + "%" +  para.getName() + "}type", para.getType());
			}
		}

		return tmpMap;
	}
}
