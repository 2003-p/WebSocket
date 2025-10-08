package com.objetdirect.gwt.umldrawer.server.yamazaki.dao;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;


public class SaveKIfUElements implements IGetElements {

	private HashMap<String,Integer> artifactIdMap = new HashMap<String,Integer>();
	private HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();
	private HashMap<String, List<IElements>> fieldObjectMap = new HashMap<String, List<IElements>>();
	private HashMap<String, List<IElements>> methodObjectMap = new HashMap<String, List<IElements>>();

	public SaveKIfUElements(String student_id,int exercise_id)
	{
		Dao_kifu6 dao = new Dao_kifu6(student_id,exercise_id);
		String codeDB = dao.getDatabaseElements();

		if(!codeDB.equals(null))
		{
			System.out.println("DBCode:" + codeDB);
			String[] splitCodeDB = codeDB.split(";",0);
			for(int i = 0; i < splitCodeDB.length; i++)
			{
				System.out.println(i + "番目：ArtifactIDが欲しい：" + splitCodeDB[i]);

				// ノートはスキップする
				if(dao.checkNote(splitCodeDB,i))
				{
					System.out.println("ノートはスキップ");
					continue;
				}

				//　クラスの要素の取得
				IElements classElements = dao.splitClassName(splitCodeDB,i);
				
				System.out.println("classElements："+classElements);
				
				
				String className = classElements.getElementName();
				classObjectMap.put(className, classElements);
				// ArtifactID
				artifactIdMap.put(className, dao.getArtifactId(splitCodeDB,i));
				System.out.println("artifactIdMap："+artifactIdMap);

				String[] patternURLSplit2 = splitCodeDB[i].split("!", -1);
				for(String str : patternURLSplit2)
					System.out.println("patternURLSplit2の中身： " + str);
						

				fieldObjectMap.put(className,dao.splitField(className,patternURLSplit2));
				System.out.println("fieldObjectMap："+fieldObjectMap);
					
				methodObjectMap.put(className,dao.splitMethod(className,patternURLSplit2));
				System.out.println("methodObjectMap："+methodObjectMap);
				
				
			}
		}

//		showDatabaseData();
	}

	public void showDatabaseData()
	{
		System.out.println("");
		System.out.println("KIfUから取得したデータ");
		for(String key : classObjectMap.keySet())
		{
			System.out.println(key);
			System.out.println("--Class--");
			System.out.println("クラス名 " + classObjectMap.get(key).getElementName());
			System.out.println("クラス修飾子 " + classObjectMap.get(key).getAccess());
			System.out.println("");

			System.out.println("--Field--");
			List<IElements> fields = fieldObjectMap.get(key);
			if(fields != null)
			{
				for(IElements field : fields)
				{
					System.out.println("フィールド名 " + field.getElementName());
					System.out.println("型 " + field.getElementType());
					System.out.println("フィールド修飾子 " + field.getAccess());
					System.out.println("");
				}

			}

			System.out.println("--Method--");
			List<IElements> methods = methodObjectMap.get(key);
			if(methods != null)
			{
				for(IElements method : methods)
				{
					System.out.println("メソッド名 " + method.getElementName());
					System.out.println("戻り値型 " + method.getElementType());
					System.out.println("メソッド修飾子 " + method.getAccess());
					System.out.println("");

					method.showParameter();
				}
			}

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
