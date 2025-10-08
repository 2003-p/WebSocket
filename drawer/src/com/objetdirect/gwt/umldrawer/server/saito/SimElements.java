package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.HashMap;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.IGetElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.difference.DiffMethodElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.difference.DiffParaElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.difference.MethodCheck;
import com.objetdirect.gwt.umldrawer.server.yamazaki.difference.ParametarCheck;

public class SimElements implements ISimilarity{

	SimClassCheck checkClass = SimClassCheck.getInstance();
	SimFieldCheck checkField = SimFieldCheck.getInstance();
	MethodCheck checkMethod = MethodCheck.getInstance();
	ParametarCheck checkParametar = ParametarCheck.getInstance();

	private SimClassElements simClassElements = null;
	private SimFieldElements simFieldElements = null;
	private DiffMethodElements simMethodElements = null;
	private DiffParaElements simParametarElements = null;

	public void simCheck(IGetElements student, IGetElements answer)
	{
//		System.out.println("---------------------");
//		System.out.println("-------sim class check-----");

		// Class
		simClassElements = checkClass.checkClass(student.getClassMap(), answer.getClassMap());
		// Field
//		System.out.println("---------------------");
//		System.out.println("-------sim field check-----");
		simFieldElements = checkField.checkField(student.getFieldMap(), answer.getFieldMap());

		//メソッドとパラメータは使わないがコメントにすると通信失敗する
		simMethodElements = checkMethod.checkMethod(student.getMethodMap(), answer.getMethodMap()); 
		simParametarElements = checkParametar.hasBothMethod(student.getMethodMap(), answer.getMethodMap());

//		show();
	}

	public void show()
	{
		simClassElements.show();
		simFieldElements.show();
//		simMethodElements.show();
//		simParametarElements.show();
	}

	@Override
	public Map<String, String> getSimMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String, String> SimMatchMap = new HashMap<String, String>();

		SimMatchMap.putAll(simClassElements.getSimMatchMap());
		SimMatchMap.putAll(simFieldElements.getSimMatchMap());
		SimMatchMap.putAll(simMethodElements.getDiffSurplusMap());
		SimMatchMap.putAll(simParametarElements.getDiffSurplusMap());
		
		return SimMatchMap;
	}

	@Override
	public Map<String, String> getSimMisMatchMap() {
		// TODO 自動生成されたメソッド・スタブ
		Map<String, String> SimMisMatchMap = new HashMap<String, String>();

		SimMisMatchMap.putAll(simClassElements.getSimMisMatchMap());
		SimMisMatchMap.putAll(simFieldElements.getSimMisMatchMap());
		SimMisMatchMap.putAll(simMethodElements.getDiffNotHasMap());
		SimMisMatchMap.putAll(simParametarElements.getDiffNotHasMap());

		return SimMisMatchMap;
	}

}
