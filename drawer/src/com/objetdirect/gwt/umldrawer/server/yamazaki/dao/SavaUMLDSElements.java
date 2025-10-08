package com.objetdirect.gwt.umldrawer.server.yamazaki.dao;

import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



public class SavaUMLDSElements implements IGetElements{

	private HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();
	private HashMap<String, List<IElements>> fieldObjectMap = new HashMap<String, List<IElements>>();
	private HashMap<String, List<IElements>> methodObjectMap = new HashMap<String, List<IElements>>();

	Dao_umlds dao = new Dao_umlds();

	public SavaUMLDSElements()
	{

		String codeDB = dao.getDatabaseElements();


		classObjectMap = dao.getClasstable();

		for(String className : classObjectMap.keySet())
		{
			fieldObjectMap.put(className,dao.getFieldtable(className));
			methodObjectMap.put(className,dao.getMethodtable(className));
		}

//		showDatabaseData();
	}

	public void showDatabaseData()
	{
		System.out.println("");
		System.out.println("UMLDSの取得したデータ");
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
		throw new MapAccessException("UMLDSにArtifactの要素はない");
	}
}
