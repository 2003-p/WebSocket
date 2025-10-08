/**
 *
 */
package com.objetdirect.gwt.umldrawer.client.test;

import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;

/**
 * @author tanaka
 *
 */
public class SimularityManagerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		nameSimTest();


	}

	public static boolean nameSimTest(){    //publicを追加
		SimilarityManager sm = new SimilarityManager();
		String str1 = "注文コード";
		String str2 = "注文コード";
		String str3 = "商品コード";
		String str4 = "商品管理システム";
		String str5 = "商品カテゴリ";

		System.out.println("nameSimTest");
		System.out.println("str1 and str2 = "+sm.nameSim(str1, str2));
		System.out.println("str1 and str3 = "+sm.nameSim(str1, str3));
		System.out.println("str1 and str4 = "+sm.nameSim(str1, str4));
		System.out.println("str1 and str5 = "+sm.nameSim(str1, str5));
		System.out.println("nameSimTest end");
		return true;
	}

	boolean makeRelationPairListTest(){
		//Dao dao = new Dao();


		return true;
	}

}
