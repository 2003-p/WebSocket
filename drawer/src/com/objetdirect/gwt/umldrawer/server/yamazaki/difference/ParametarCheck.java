package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.MethodParametar;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Method_IElements;



public class ParametarCheck extends AbstractDfferenceMethodsClass{

	private static ParametarCheck singleton = new ParametarCheck();

	private ParametarCheck()
	{

	}

	static public ParametarCheck getInstance()
	{
		return singleton;
	}


	public DiffParaElements hasBothMethod(HashMap<String, List<IElements>> kifu_fmMap,HashMap<String, List<IElements>> umlds_fmMap) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<MethodParametar>> surplusParametar = new HashMap<String,List<MethodParametar>>();
		Map<String,List<MethodParametar>> nothasParametar = new HashMap<String,List<MethodParametar>>();

		for(String kifuKey : kifu_fmMap.keySet())
		{
			if(umlds_fmMap.get(kifuKey) == null)
			{
				//メソッドがなにもないクラスはパラメータを調べない
				continue;
			}

			//　ソースコード側のクラスの中のメソッドの繰り返し
			for(IElements kifu_field : kifu_fmMap.get(kifuKey))
			{
				//　KIfU側 1つのクラスの中にあるメソッドのリスト
				for(IElements umlds_field : umlds_fmMap.get(kifuKey))
				{
					//　名前があるかどうか
					if(isName(umlds_field.getElementName(),kifu_field.getElementName()) == true)
					{
						///いろいろためす
						Method_IElements kyasuto_kifu = (Method_IElements) kifu_field;
						Method_IElements kyasuto_umlds = (Method_IElements) umlds_field;

						String tmpuseParaKey = kifuKey + "&" +  kifu_field.getElementName();

						Map<String,List<MethodParametar>> tmpnothas = nothas(tmpuseParaKey,kyasuto_kifu,kyasuto_umlds);
						Map<String,List<MethodParametar>> tmpsurplus = surplus(tmpuseParaKey,kyasuto_kifu,kyasuto_umlds);

						surplusParametar = getMap(surplusParametar,tmpsurplus);
//						surplusParametar = Stream.of(surplusParametar,tmpsurplus)
//								.flatMap(m -> m.entrySet().stream())
//								.collect(Collectors.toMap(Entry::getKey,  Entry::getValue));
//
						nothasParametar = getMap(nothasParametar,tmpnothas);
//						nothasParametar = Stream.of(nothasParametar,tmpnothas)
//								.flatMap(m -> m.entrySet().stream())
//								.collect(Collectors.toMap(Entry::getKey,  Entry::getValue));					}


					}
				}

			}
		}


		return new DiffParaElements(nothasParametar,surplusParametar);
	}

	private Map<String,List<MethodParametar>> surplus(String methodName, Method_IElements kifu, Method_IElements umlds) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<MethodParametar>> surplusPaarametar = new HashMap<String,List<MethodParametar>>();


		// kifuにはあってコードにない場合は余剰
		if(umlds.getParametarLen() == 0)
		{
			List<MethodParametar> surplusParaList = new ArrayList<MethodParametar>();
			//フィールドがなにもないクラス保存して終わり
			for(int kifuIndex = 0; kifuIndex < kifu.getParametarLen(); kifuIndex++)
			{
				surplusParaList.add(kifu.getParaElement(kifuIndex));
			}
			surplusPaarametar.put(methodName, surplusParaList);
		}
		else
		{
			List<MethodParametar> surplusParaList = new ArrayList<MethodParametar>();
			for(int kifuIndex = 0; kifuIndex < kifu.getParametarLen(); kifuIndex++)
			{

				boolean has = false;

				//　ソースコード側のクラスの中のフィールドの繰り返し
				for(int umldsIndex = 0; umldsIndex < umlds.getParametarLen(); umldsIndex++)
				{
					//　名前があるかどうか
					if(isName(umlds.getParaName(umldsIndex),kifu.getParaName(kifuIndex)) == true)
					{
						kifu.getParaElement(kifuIndex).changeNamebool();
						if(isName(umlds.getParaType(umldsIndex),kifu.getParaType(kifuIndex)) == true)
						{
							kifu.getParaElement(kifuIndex).changeTypebool();
							has = true;
						}
						break;
					}
				}
				if(!has)
				{
					surplusParaList.add(kifu.getParaElement(kifuIndex));
				}
			}
			surplusPaarametar.put(methodName, surplusParaList);
		}

		return surplusPaarametar;
	}

	private Map<String,List<MethodParametar>> nothas(String methodName, Method_IElements kifu, Method_IElements umlds) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<MethodParametar>> nothasParametar = new HashMap<String,List<MethodParametar>>();

		// コードにはあってkifuにない場合は余剰
		if(kifu.getParametarLen() == 0)
		{
			List<MethodParametar> nothasParaList = new ArrayList<MethodParametar>();
			//フィールドがなにもないクラス保存して終わり
			for(int umldsIndex = 0; umldsIndex < umlds.getParametarLen(); umldsIndex++)
			{
				nothasParaList.add(umlds.getParaElement(umldsIndex));
			}
			nothasParametar.put(methodName, nothasParaList);
		}
		else
		{
			List<MethodParametar> nothasParaList = new ArrayList<MethodParametar>();
			for(int umldsIndex = 0; umldsIndex < umlds.getParametarLen(); umldsIndex++)
			{
				boolean has = false;

				//　ソースコード側のクラスの中のフィールドの繰り返し
				for(int kifuIndex = 0; kifuIndex < kifu.getParametarLen(); kifuIndex++)
				{
					//　名前があるかどうか
					if(isName(umlds.getParaName(umldsIndex),kifu.getParaName(kifuIndex)) == true)
					{
						umlds.getParaElement(umldsIndex).changeNamebool();
						if(isName(umlds.getParaType(umldsIndex),kifu.getParaType(kifuIndex)) == true)
						{
							umlds.getParaElement(umldsIndex).changeTypebool();
							has = true;
						}
						//has = true;
						break;
					}
				}
				if(!has)
					nothasParaList.add(umlds.getParaElement(umldsIndex));

			}
			nothasParametar.put(methodName, nothasParaList);
		}
		return nothasParametar;
	}

	private Map<String,List<MethodParametar>> getMap(Map<String,List<MethodParametar>> data, Map<String,List<MethodParametar>> tmp)
	{
		for(String key : data.keySet())
		{
			if(!tmp.containsKey(key))
				tmp.put(key, data.get(key));
		}
		return tmp;

	}

	private void show(Map<String,List<MethodParametar>> show)
	{
		for(String key : show.keySet())
		{
			System.out.println("key:" + key + " value:" + show.get(key));
		}
	}
}
