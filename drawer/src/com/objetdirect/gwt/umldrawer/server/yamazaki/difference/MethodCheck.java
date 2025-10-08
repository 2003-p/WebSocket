package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



public class MethodCheck extends AbstractDfferenceMethodsClass{

	private static MethodCheck singleton = new MethodCheck();




	Map<String,List<ParametarCheck>> parametarMap = new HashMap<String,List<ParametarCheck>>();

	private MethodCheck()
	{

	}

	public static MethodCheck getInstance()
	{
		return singleton;
	}



	public DiffMethodElements checkMethod(HashMap<String, List<IElements>> kifu_fmMap,HashMap<String, List<IElements>> umlds_fmMap)
	{
		return new DiffMethodElements(nothasElements(kifu_fmMap,umlds_fmMap),surplusElements(kifu_fmMap,umlds_fmMap));

	}

}
