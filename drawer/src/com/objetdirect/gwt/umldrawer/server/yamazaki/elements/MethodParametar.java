package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;


public class MethodParametar extends ElementsBoolean{

	private String type;
	private String name;

	MethodParametar(String name, String type)
	{
		this.name = name;
		this.type = type;

		this.boolAccess = false;
		this.boolName = false;
		this.boolType = false;
	}

	public String getName()
	{
		return name;
	}

	public String getType()
	{
		return type;
	}

	public void changeTypebool() {
		// TODO 自動生成されたメソッド・スタブ
		this.boolType = !this.boolType;
	}

	public void changeNamebool() {
		// TODO 自動生成されたメソッド・スタブ
		this.boolName = !this.boolName;
	}


	public boolean getTypebool() {
		// TODO 自動生成されたメソッド・スタブ
		return this.boolType;
	}


	public boolean getNamebool() {
		// TODO 自動生成されたメソッド・スタブ
		return this.boolName;
	}

}
