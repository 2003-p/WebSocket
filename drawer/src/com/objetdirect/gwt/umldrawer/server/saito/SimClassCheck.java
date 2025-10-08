package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



public class SimClassCheck{

	private static SimClassCheck singleton = new SimClassCheck();

	public static SimClassCheck getInstance()
	{
		return singleton;
	}

	public SimClassElements checkClass(HashMap<String, IElements> student_classMap, HashMap<String, IElements> answer_classMap)
	{
		List<IElements> list1 = matchClass(student_classMap,answer_classMap);
		List<IElements> list2 = misMatchClass(student_classMap,answer_classMap, list1);
		
		return new SimClassElements(list2, list1);
	}

	//類似度1のクラス
	public List<IElements> matchClass(HashMap<String, IElements> student_classMap, HashMap<String, IElements> answer_classMap) {
		// TODO 自動生成されたメソッド・スタブ
		ArrayList<IElements> matchClass = new ArrayList<IElements>();

		for(String answerKey : answer_classMap.keySet())
		{
			for(String studentKey : student_classMap.keySet()) {
				
				if(answer_classMap.get(answerKey) != null && answerKey.equals(studentKey))
				{
//					System.out.println("");
					
					matchClass.add(student_classMap.get(studentKey));
				}
			}
		}
		
		return matchClass;
	}

	//類似度0のクラス
	public List<IElements> misMatchClass(HashMap<String, IElements> student_classMap, HashMap<String, IElements> answer_classMap, List<IElements> matchClass) {
		// TODO 自動生成されたメソッド・スタブ
		SimilarityManager sm = new SimilarityManager();
		ArrayList<IElements> misMatchClass = new ArrayList<IElements>();
		boolean skip = true;
		String tmpKey;
		
		for(String studentKey : student_classMap.keySet()){
			skip = false;	
			tmpKey = null;
			
			for(IElements element : matchClass) {
				if(element.equals(student_classMap.get(studentKey))) {
					skip = true;
				}
			}
			
			outerLoop:
			if(skip == false) {
				
				for(String answerKey : answer_classMap.keySet()) {
//					System.out.println(studentKey+" == "+answerKey);
					if(sm.nameSim(studentKey, answerKey) != 0) {
//						System.out.println("離脱");
						break outerLoop;
					}
					tmpKey = studentKey;
				}
				
//				System.out.println("ペアなし："+tmpKey);
				misMatchClass.add(student_classMap.get(tmpKey));
			
			}
			
		}
		
		return misMatchClass;
	}

}
