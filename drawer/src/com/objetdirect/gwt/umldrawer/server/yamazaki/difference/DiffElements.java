package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.HashMap;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.IGetElements;

public class DiffElements implements IDifference{

	ClassCheck checkClass = ClassCheck.getInstance();
	FieldCheck checkField = FieldCheck.getInstance();
	MethodCheck checkMethod = MethodCheck.getInstance();
	ParametarCheck checkParametar = ParametarCheck.getInstance();

	private DiffClassElements diffClassElements = null;
	private DiffFieldElements diffFieldElements = null;
	private DiffMethodElements diffMethodElements = null;
	private DiffParaElements diffParametarElements = null;

	public void diffCheck(IGetElements kifu, IGetElements umlds)
	{
		System.out.println("---------------------");
		System.out.println("-----diff check----");

		// Class
		diffClassElements = checkClass.checkClass(kifu.getClassMap(), umlds.getClassMap());


		// Field
		diffFieldElements = checkField.checkField(kifu.getFieldMap(), umlds.getFieldMap());


/*
 * 		Method
		Mainメソッドの処理は後回しにしようとおもう
		余剰クラスの余剰メソッドがソースコードで存在する場合に余剰と判断しないバグがある
		多分解決した
*/
		diffMethodElements = checkMethod.checkMethod(kifu.getMethodMap(), umlds.getMethodMap());



		// Parametar
		diffParametarElements = checkParametar.hasBothMethod(kifu.getMethodMap(), umlds.getMethodMap());


	}

	public void show()
	{
		diffClassElements.show();
		diffFieldElements.show();
		diffMethodElements.show();
		diffParametarElements.show();
	}

	@Override
	public Map<String, String> getDiffSurplusMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String, String> sendDiffMap = new HashMap<String, String>();

		sendDiffMap.putAll(diffClassElements.getDiffSurplusMap());
		sendDiffMap.putAll(diffFieldElements.getDiffSurplusMap());
		sendDiffMap.putAll(diffMethodElements.getDiffSurplusMap());
		sendDiffMap.putAll(diffParametarElements.getDiffSurplusMap());

		return sendDiffMap;
	}

	@Override
	public Map<String, String> getDiffNotHasMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String, String> sendDiffMap = new HashMap<String, String>();

		sendDiffMap.putAll(diffClassElements.getDiffNotHasMap());
		sendDiffMap.putAll(diffFieldElements.getDiffNotHasMap());
		sendDiffMap.putAll(diffMethodElements.getDiffNotHasMap());
		sendDiffMap.putAll(diffParametarElements.getDiffNotHasMap());

		return sendDiffMap;
	}

}
