package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.IGetElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;


public class AnswerElements implements IGetElements {

	private HashMap<String,Integer> artifactIdMap = new HashMap<String,Integer>();
	private HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();
	private HashMap<String, List<IElements>> fieldObjectMap = new HashMap<String, List<IElements>>();
	private HashMap<String, List<IElements>> methodObjectMap = new HashMap<String, List<IElements>>();

	public AnswerElements(String answer_url){
		CutIntoElements cut = new CutIntoElements();

		if(!answer_url.equals(null)){
			
			System.out.println("正解例のURL:" + answer_url);
			String[] splitCodeDB = answer_url.split(";",0);
			for(int i = 0; i < splitCodeDB.length; i++){

				if(cut.checkNote(splitCodeDB,i)){
					System.out.println("ノートはスキップ");
					continue;
				}

				//　クラスの要素の取得
				IElements classElements = cut.splitClassName(splitCodeDB,i);
				
				if(classElements != null) {  //if文で囲った saito
					String className = classElements.getElementName();
					
					classObjectMap.put(className, classElements);
					artifactIdMap.put(className, cut.getArtifactId(splitCodeDB,i));
					String[] patternURLSplit2 = splitCodeDB[i].split("!", -1);

					fieldObjectMap.put(className,cut.splitField(className,patternURLSplit2));
					methodObjectMap.put(className,cut.splitMethod(className,patternURLSplit2));
				
				}
				
			}
			
		}

//		showDatabaseData();
	}

	public void showDatabaseData(){
		System.out.println("");
		System.out.println("-----正解例-----");
		System.out.println("");
		for(String key : classObjectMap.keySet()){
			
//			System.out.println(key);
			System.out.println("--Class--");
			System.out.println(classObjectMap.get(key).getElementName());
			System.out.println("");

			System.out.println("--Field--");
			List<IElements> fields = fieldObjectMap.get(key);
			if(fields != null)
			{
				for(IElements field : fields)
				{
					System.out.println(field.getElementName());	
				}
			}
			System.out.println("");
		}
	}


	@Override
	public HashMap<String, IElements> getClassMap() {
		// TODO 自動生成されたメソッド・スタブ
		return classObjectMap;
	}

	@Override
	public HashMap<String, List<IElements>> getFieldMap() {
		// TODO 自動生成されたメソッド・スタブ
		return fieldObjectMap;
	}

	@Override
	public HashMap<String, List<IElements>> getMethodMap() {
		// TODO 自動生成されたメソッド・スタブ
		return methodObjectMap;
	}

	@Override
	public HashMap<String, Integer> getArtifactMap() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
