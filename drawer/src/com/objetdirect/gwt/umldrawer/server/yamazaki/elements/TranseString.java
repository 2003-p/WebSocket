package com.objetdirect.gwt.umldrawer.server.yamazaki.elements;

public class TranseString {

	private static TranseString singleton = new TranseString();

	public enum StereoType {
		NONE(0),
		PUBLIC(1),
		PRIVATE(2),
		PROTECTED(4),
		STATIC(8),
		FINAL(16),
		SYNCHRONIZED(32),
		INTERFACE(512),
		ABSTRACT(1024);

		private final int stereoId;

		private StereoType(final int id)
		{
			this.stereoId = id;
		}

		public int getInt()
		{
			return this.stereoId;
		}

	}

	private TranseString()
	{

	}

	public static TranseString getInstance()
	{
		return singleton;
	}

	public String returnTranseType(String before)
	{
		String after = "";

		// List
//		if(before.contains("<") && before.contains("QList"))
//		{
// 			after = returnTypeforList(before);
// 			return after;
//		}

		int hairetuNum = before.length() - before.replace("[", "").length();
		before = before.replace("[", "");

		if(before.equals("I"))
		{
			after = "int";
		}
		else if(before.equals("V"))
		{
			after = "void";
		}
		else if(before.equals("D"))
		{
			after = "double";
		}
		else if(before.contains("QMap"))
		{
			after = "Map";
		}
		else if(before.contains("QList"))
		{
			after = "List";
		}
		else if(before.equals("Z"))
		{
			after = "boolean";
		}
		else if(before.equals("CONSTRUCTOR"))
		{
			after = ""; // CONSTRUCTORはなし
		}
		else if(before.contains("QString"))
		{
			after = "String";
		}
		else
		{
		    // 自作クラスとか既存のライブラリに入っているやつだと思う
		    before = before.substring(0, before.length() - 1);
			after = before.substring(1,before.length());
		}

		if(hairetuNum > 0)
			after = arrayType(after,hairetuNum);
		else
			;

		return after;
	}

	private String returnTypeforList(String before)
	{
		String after = "List";
		int hairetuNum = before.length() - before.replace("[", "").length();
		before = before.replace("[", "");

		int listStartNum = before.indexOf("<") + 1;
		int listEndNum = before.indexOf(";");
		String substringListType = before.substring(listStartNum,listEndNum);


		if(before.contains("QInteger"))
		{
			after += "<Integer";
		}
		else if(before.contains("D"))
		{
			after += "<double";
		}
		else if(before.contains("QString"))
		{
			after += "<String";
		}
		else
		{
			after += "<" + substringListType.substring(1,substringListType.length());
		}


		if(hairetuNum > 0)
			after = arrayType(after,hairetuNum);
		else
			;


		return after += ">";

	}

	private static String arrayType(String after,int hairetuNum)
	{
		for(int count = 0; count < hairetuNum; count++)
			after += "[]";

		return after;
	}

	public String returnAccessSymbol(int beforeAccessNum)
	{
		String afterAccess = "";

		if	   (beforeAccessNum == StereoType.NONE.getInt())
			afterAccess = "~";

		else if(beforeAccessNum == StereoType.PUBLIC.getInt())
			afterAccess = "+";

		else if(beforeAccessNum == StereoType.PRIVATE.getInt())
			afterAccess = "-";

		else if(beforeAccessNum == StereoType.PROTECTED.getInt())
			afterAccess = "#";

		return afterAccess;
	}

	public String returnClassStereoType(int beforeAccessNum)
	{
		String afterAccess = "";

		if	   (beforeAccessNum / StereoType.ABSTRACT.getInt() > 0)
			afterAccess = "Abstract";

		else if(beforeAccessNum / StereoType.INTERFACE.getInt() > 0)
			afterAccess = "Interface";

		return afterAccess;

	}

}
