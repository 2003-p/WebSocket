package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



class AbstractDfferenceMethodsClass {

	protected Map<String,List<IElements>> nothasElements(HashMap<String, List<IElements>> kifu_fmMap,HashMap<String, List<IElements>> umlds_fmMap) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<IElements>> nothasMethod = new HashMap<String,List<IElements>>();

		for(String umldsKey : umlds_fmMap.keySet())
		{
			List<IElements> nothasMethodList = new ArrayList<IElements>();

			if(kifu_fmMap.get(umldsKey) == null)
			{
				//フィールドがなにもないクラス保存して終わり
				for(IElements umlds_method : umlds_fmMap.get(umldsKey))
				{
					nothasMethodList.add(umlds_method);
				}
				nothasMethod.put(umldsKey, nothasMethodList);
				continue;
			}

			//　ソースコード側のクラスの中のメソッドの繰り返し
			for(IElements umlds_method : umlds_fmMap.get(umldsKey))
			{
				boolean has = false;

				//　KIfU側 1つのクラスの中にあるメソッドのリスト
				for(IElements kifu_method : kifu_fmMap.get(umldsKey))
				{
					//　名前があるかどうか
					if(isName(umlds_method.getElementName(),kifu_method.getElementName()) == true)
					{
						//　名前があってたら名前のフラグを立てる
						umlds_method.changeNamebool();
						has = true;

						if(isName(umlds_method.getElementType(),kifu_method.getElementType()) == true)
						{
							//falseだったら型が一致してない
							umlds_method.changeTypebool();
						}
						else {
							has = false;
						}

						if(checkAccess(umlds_method.getAccess(),kifu_method.getAccess()) == true)
						{
							//falseだったら修飾子が一致していない
							umlds_method.changeAccessbool();
						}
						else {
							has = false;
						}
						//has = true;
						break;
					}
					else {
						has = false;
					}

				}
				if(!has)
					nothasMethodList.add(umlds_method);
			}
			nothasMethod.put(umldsKey, nothasMethodList);
		}
		return nothasMethod;
	}

	protected Map<String,List<IElements>> surplusElements(HashMap<String, List<IElements>> kifu_fmMap,HashMap<String, List<IElements>> umlds_fmMap) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<IElements>> surplusMethod = new HashMap<String,List<IElements>>();

		for(String kifuKey : kifu_fmMap.keySet())
		{
			List<IElements> surplusMethodList = new ArrayList<IElements>();

			if(umlds_fmMap.get(kifuKey) == null)
			{
				//フィールドがなにもないクラス保存して終わり
				for(IElements kifu_method : kifu_fmMap.get(kifuKey))
				{
					System.out.println(kifu_method.getElementName());
					surplusMethodList.add(kifu_method);
				}
				surplusMethod.put(kifuKey, surplusMethodList);
				continue;
			}

			//　ソースコード側のクラスの中のフィールドの繰り返し
			for(IElements kifu_method : kifu_fmMap.get(kifuKey))
			{
				boolean has = false;

				//　KIfU側 1つのクラスの中にあるフィールドのリスト
				for(IElements umlds_method : umlds_fmMap.get(kifuKey))
				{
					//　名前があるかどうか
					if(isName(umlds_method.getElementName(),kifu_method.getElementName()) == true)
					{
						/*
						 *
						 * 余剰に関してはどっちか片方でやればいいから以下コメント
						 *
						 *
						*/

						//　名前があってたら名前のフラグを立てる
						kifu_method.changeNamebool();
						has = true;

						if(isName(umlds_method.getElementType(),kifu_method.getElementType()) == true)
						{
							//falseだったら型が一致してない
							kifu_method.changeTypebool();
						}
						else {
							has = false;
						}

						if(checkAccess(umlds_method.getAccess(),kifu_method.getAccess()) == true)
						{
							//falseだったら修飾子が一致していない
							kifu_method.changeAccessbool();
						}
						else {
							has = false;
						}
						//has = true;
						break;
					}
					else {

						has = false;
					}

				}
				if(!has) {
					surplusMethodList.add(kifu_method);
				}
			}
			surplusMethod.put(kifuKey, surplusMethodList);

		}
		return surplusMethod;
	}

	protected boolean isName(String umlds, String kifu)
	{
		if(kifu.equals(umlds))
		{
			return true;
		}
		return false;
	}

	protected boolean checkAccess(int umlds, int kifu)
	{
		if(umlds == kifu)
		{
			return true;
		}
		return false;
	}
}
