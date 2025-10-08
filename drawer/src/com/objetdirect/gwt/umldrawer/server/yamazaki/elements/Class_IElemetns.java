package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;

import java.util.List;

public class Class_IElemetns extends ElementsBoolean implements IElements {

	private int access;
	private String className;
	private String sutereotype;
	private TranseString transeSingleton = TranseString.getInstance();

	public Class_IElemetns(int access, String className)
	{
		this.access = access;
		this.className = className;
		this.sutereotype = transeSingleton.returnClassStereoType(access);


		this.boolAccess = false;
		this.boolName = false;
		this.boolType = false;

	}

	@Override
	public int getAccess() {
		// TODO 自動生成されたメソッド・スタブ
		return access;
	}

	@Override
	public String getElementName() {
		// TODO 自動生成されたメソッド・スタブ
		return className;
	}

	@Override
	public String getElementType() {
		// TODO 自動生成されたメソッド・スタブ

		// クラスに型はないので使わない
		return sutereotype;
	}

	@Override
	public void showParameter() {
		// TODO 自動生成されたメソッド・スタブ

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
		return null;
	}
}
