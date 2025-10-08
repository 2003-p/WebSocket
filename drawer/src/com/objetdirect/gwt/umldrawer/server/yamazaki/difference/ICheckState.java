package com.objetdirect.gwt.umldrawer.server.yamazaki.difference;

import java.util.HashMap;
import java.util.List;


import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;



public interface ICheckState {

	DiffClassElements checkClass(HashMap<String, IElements> kifu_classMap, HashMap<String, IElements> umlds_classMap);


	ICheckState nextCheck();
}
