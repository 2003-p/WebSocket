package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;

import java.util.List;

public interface IElements {
	int getAccess();
	String getElementName();
	String getElementType();
	List<MethodParametar> getMethodParameter();

	void showParameter();



	void changeTypebool();
	void changeNamebool();
	void changeAccessbool();

	boolean getTypebool();
	boolean getNamebool();
	boolean getAccessbool();
}
