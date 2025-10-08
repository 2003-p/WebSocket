package com.objetdirect.gwt.umldrawer.server.saito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Class_IElemetns;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Field_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Method_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.SplitElement;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;


public class CutIntoElements{
	
	TranseString tranceAccessSingleton = TranseString.getInstance();
	final private int ABSTRACT = 1024;
	final private int INTERFACE = 512;

	SplitElement splitObject = SplitElement.getInstance();

	public CutIntoElements()
	{
		

	}

	boolean checkNote(String[] splitCodeDB, int index)
	{
//		System.out.println(splitCodeDB[index]);
		int start = splitCodeDB[index].indexOf("]") + 1;
		int end = splitCodeDB[index].indexOf("$");
		String umltype = splitCodeDB[index].substring(start,end);
//			System.out.println("umltype：" + umltype);
		if(umltype.equals("Note"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getArtifactId(String[] splitCodeDB, int index)
	{
		int start = splitCodeDB[index].indexOf("<") + 1;
		int end = splitCodeDB[index].indexOf(">");

		int artifactId = Integer.parseInt(splitCodeDB[index].substring(start,end));
//			System.out.println("ArtifactId：" + artifactId);

		return artifactId;
	}

	public IElements splitClassName(String[] splitCodeDB,int index)
	{
		int x = splitCodeDB[index].indexOf("!");
		int y = splitCodeDB[index].indexOf("!",x+1);
		String tmpClassName = "";
		IElements classElemetns = null;
		HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();

		if(x!=-1 && y!=-1 && !(splitCodeDB[index].substring(x+1, y).contains("<")))
		{
			
			tmpClassName = splitCodeDB[index].substring(x+1, y).trim();
			
			int stereoEndIndex = splitCodeDB[index].indexOf("!",y + 1);
			String stereo = splitCodeDB[index].substring(y + 1, stereoEndIndex).trim();
			int access = 0;

			if(stereo.equals("Abstract"))
			{
				access = ABSTRACT;
			}
			else if(stereo.equals("Interface"))
			{
				access = INTERFACE;
			}
			else
			{
				access = 0;
			}

//			System.out.println("Access:" + access);
			classObjectMap.put(tmpClassName, new Class_IElemetns(access,tmpClassName));
			classElemetns = new Class_IElemetns(access,tmpClassName);
		}

		return classElemetns;
	}

	public List<IElements> splitField(String className,String[] patternURLSplit2)
	{
		int start = 0,end = 0;
		boolean add = false;
		List<IElements> fieldList = new ArrayList<IElements>();

		for(int j=0;j<patternURLSplit2.length;j++)
		{
			if(!(patternURLSplit2[j].contains("%") || !(patternURLSplit2[j].contains(":"))))
			{
				continue;
			}else
			{

			}
			if(patternURLSplit2[j].contains("("))
			{
				continue;
			}
			end = patternURLSplit2[j].lastIndexOf("%");
			if(start != -1 && end != -1)
			{
				String[] fields = patternURLSplit2[j].substring(start, end).split("%", 0);


				for(String field : fields)
				{
					field = field.replaceAll(" ","");
//					System.out.println("属性：" + field);

					String[] splitField = splitObject.returnSplitField(field);
					int access = splitObject.returnSplitAccess(splitField[0]);

					String fieldName = splitField[0].substring(1,splitField[0].length()).replaceAll(" ","");

					String fieldType = "";
					if(splitField.length > 1)
					{
						fieldType = splitField[1].trim();
					}
					else
					{
						;
					}
					fieldList.add(new Field_IElements(access,fieldName,fieldType));
				}
				add = true;
			}
		}
		if(add = false) {
			}

		return fieldList;
	}

	public List<IElements> splitMethod(String className, String[] patternURLSplit2)
	{
		boolean add = false;
		int index1=0 ,index2=0;
		List<IElements> methodList = new ArrayList<IElements>();

		for(int j = 0;j < patternURLSplit2.length; j++)
		{
			if(!(patternURLSplit2[j].contains("%") || !(patternURLSplit2[j].contains(":"))))
			{
				continue;
			}else {

			}
			if(!(patternURLSplit2[j].contains("(")))
			{
				continue;
			}

			index2 = patternURLSplit2[j].lastIndexOf("%");
			if(index1!=-1 && index2!=-1)
			{
				String[] kifu_method = patternURLSplit2[j].substring(index1, index2).split("%", 0);
				for(String method : kifu_method)
				{
					method = method.replaceAll(" ","");


					int parenthesesStart = method.indexOf("(") + 1;
					int parenthesesEnd = method.indexOf(")");

					String parametar = method.substring(parenthesesStart,parenthesesEnd);


					String accecc_and_methodName = method.substring(0,parenthesesStart - 1);

					// Type
					String returnType = "";
					int typeStart = parenthesesEnd + 2;
					int typeLast = method.length();
					if(typeStart < typeLast)
					{
					    returnType = method.substring(parenthesesEnd + 2,method.length());
					}
					else
					{
					    ;
					}


					int access = splitObject.returnSplitAccess(accecc_and_methodName);
					String methodName = accecc_and_methodName.substring(1,parenthesesStart - 1);

					Map<String,List<String>> parametarMap = splitObject.splitParametarNameType(parametar);

					IElements MethodObject = new Method_IElements(access,methodName,returnType,parametarMap.get("Name"),parametarMap.get("Type"));

					methodList.add(MethodObject);
					add = true;
				}

				add = true;
			}
		}
		if(add = false) {
		}

		return methodList;
	}

	
	

}