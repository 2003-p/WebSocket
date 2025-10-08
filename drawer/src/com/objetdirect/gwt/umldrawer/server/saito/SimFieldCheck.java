package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.client.helpers.SimilarityManager;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;

public class SimFieldCheck{

	private static SimFieldCheck singleton = new SimFieldCheck();
	SimilarityManager sm = new SimilarityManager();

	private SimFieldCheck()
	{

	}

	public static SimFieldCheck getInstance()
	{
		return singleton;
	}
	
	public SimFieldElements checkField(HashMap<String, List<IElements>> student_Map,HashMap<String, List<IElements>> answer_Map)
	{
		return new SimFieldElements(simMatchElements(student_Map,answer_Map),simMisMatchElements(student_Map,answer_Map));
	}
	
	protected Map<String,List<IElements>> simMatchElements(HashMap<String, List<IElements>> student_Map,HashMap<String, List<IElements>> answer_Map) {
		// TODO 自動生成されたメソッド・スタブ
		
		Map<String,List<IElements>> matchField = new HashMap<String,List<IElements>>();
		
		for(String studentKey : student_Map.keySet()){
		
			List<IElements> MatchFieldList = new ArrayList<IElements>();
			
			for(IElements student_field : student_Map.get(studentKey)){
				
				for(String answerKey : answer_Map.keySet()){
					
					for(IElements answer_field : answer_Map.get(answerKey)){
						
//						System.out.println( student_field.getElementName()+" == "+answer_field.getElementName() );
						if(sm.nameSim(student_field.getElementName(), answer_field.getElementName()) == 1) {
//							System.out.println("完全一致");
							MatchFieldList.add(student_field);
						}
					
					}
					
				}
				
			}
			matchField.put(studentKey, MatchFieldList);
		}
//		System.out.println("-------------------");
//		System.out.println("");
		return matchField;
	}

	protected Map<String,List<IElements>> simMisMatchElements(HashMap<String, List<IElements>> student_Map,HashMap<String, List<IElements>> answer_Map) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,List<IElements>> misMatchField = new HashMap<String,List<IElements>>();
		boolean skip = true;
		
		for(String studentKey : student_Map.keySet()){
			
			List<IElements> MatchFieldList = new ArrayList<IElements>();
			
			for(IElements student_field : student_Map.get(studentKey)){
	
				outerLoop:
				for(String answerKey : answer_Map.keySet()){
					
					for(IElements answer_field : answer_Map.get(answerKey)){
						
//						System.out.println( student_field.getElementName()+" == "+answer_field.getElementName() +"   ("+sm.nameSim(student_field.getElementName(), answer_field.getElementName())+")");
						if(sm.nameSim(student_field.getElementName(), answer_field.getElementName()) != 0) {
							skip = true;
							break outerLoop;
						}else {
							skip = false;
						}
						
					}
					
				}
				if(skip==false){
//					System.out.println("add::"+student_field.getElementName());
					MatchFieldList.add(student_field);
				}
			
			}
			misMatchField.put(studentKey, MatchFieldList);
		
		}
		return misMatchField;
	}

	protected boolean isName(String answer, String student)
	{
		if(answer.equals(student))
		{
			return true;
		}
		return false;
	}

	protected boolean checkAccess(int answer, int student)
	{
		if(answer == student)
		{
			return true;
		}
		return false;
	}

}
