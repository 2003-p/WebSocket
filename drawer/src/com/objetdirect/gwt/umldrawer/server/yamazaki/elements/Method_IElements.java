package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




public class Method_IElements extends ElementsBoolean implements IElements {

	private int access;
	private String mehotdName;
	private String returnType;
	private List<MethodParametar> parametarList = new ArrayList<MethodParametar>();



	public Method_IElements(int access, String methodName, String methodType, List<String> paraName, List<String> paraType)
	{
		this.access = access;
		this.mehotdName = methodName;
		this.returnType = methodType;

		this.boolAccess = false;
		this.boolName = false;
		this.boolType = false;

		// パラメータの名前と型はセットであることが前提で
		for(int para_index = 0; para_index < paraName.size(); para_index++)
		{
			MethodParametar methodparametar = new MethodParametar(paraName.get(para_index), paraType.get(para_index));
			parametarList.add(methodparametar);
		}
	}

	@Override
	public void showParameter() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("--Parametar--");
		for(MethodParametar parametar : parametarList)
		{
			System.out.println("名前 " + parametar.getName());
			System.out.println("型 " + parametar.getType());
			System.out.println("");
		}
		System.out.println("");
	}

	@Override
	public int getAccess() {
		// TODO 自動生成されたメソッド・スタブ
		return access;
	}

	@Override
	public String getElementName() {
		// TODO 自動生成されたメソッド・スタブ
		return mehotdName;
	}

	@Override
	public String getElementType() {
		// TODO 自動生成されたメソッド・スタブ
		return returnType;
	}

	public int getParametarLen()
	{
		return parametarList.size();
	}

	public MethodParametar getParaElement(int index)
	{
		return parametarList.get(index);
	}

	public String getParaName(int index)
	{
		return parametarList.get(index).getName();
	}

	public String getParaType(int index)
	{
		return parametarList.get(index).getType();
	}

	@Override
	public void changeTypebool() {
		// TODO 自動生成されたメソッド・スタブ
		this.boolType = !this.boolType;
	}


	@Override
	public void changeNamebool() {
		// TODO 自動生成されたメソッド・スタブ
		this.boolName = !this.boolName;
	}


	@Override
	public void changeAccessbool() {
		// TODO 自動生成されたメソッド・スタブ
		this.boolAccess = !this.boolAccess;
	}

	@Override
	public boolean getTypebool() {
		// TODO 自動生成されたメソッド・スタブ
		return this.boolType;
	}


	@Override
	public boolean getNamebool() {
		// TODO 自動生成されたメソッド・スタブ
		return this.boolName;
	}


	@Override
	public boolean getAccessbool() {
		// TODO 自動生成されたメソッド・スタブ
		return this.boolAccess;
	}

	@Override
	public List<MethodParametar> getMethodParameter() {
		// TODO 自動生成されたメソッド・スタブ
		return this.parametarList;
	}
}
