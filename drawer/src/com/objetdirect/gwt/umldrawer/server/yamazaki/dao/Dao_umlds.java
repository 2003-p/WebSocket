package com.objetdirect.gwt.umldrawer.server.yamazaki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Class_IElemetns;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Field_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Method_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;



public class Dao_umlds {

	private Statement stm;

	private String sql;

	private String codeDB;
	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/umlds?Unicode=true&characterEncoding=UTF8";

	private TranseString transestring = TranseString.getInstance();
	private final DatabaseAccessorUMLDS daoumlds;


//	private ArrayList<String> umlClassList= new ArrayList<>(); //UML縺ｮ繧ｯ繝ｩ繧ｹ縺ｮ繝ｪ繧ｹ繝�
//	private HashMap<String, List<String>> umlFieldNameMap = new HashMap<>(); //UML縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ蜷�, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝�
//	private HashMap<String, List<String>> umlFieldTypeMap = new HashMap<>(); //UML縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ蜷�, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝�
//	private HashMap<String, List<String>> umlFieldAccessMap = new HashMap<>(); //UML縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ蜷�, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝�
//
//	private HashMap<String, List<String>> umlMethodNameMap = new HashMap<>(); //UML縺ｮ繝｡繧ｽ繝�繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ逶ｮ, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝｡繧ｽ繝�繝峨�ｮ繝ｪ繧ｹ繝�


	public Dao_umlds()
	{
		// super.createHikariConfig(DRIVER_URL);
		daoumlds = DatabaseAccessorUMLDS.getInstance();
		System.out.println("getCanvasURL:HikariCP縺ｧ繧ｳ繝阪け繧ｷ繝ｧ繝ｳ逕滓��");
	}

	public String getDatabaseElements()
	{
		// TODO 閾ｪ蜍慕函謌舌＆繧後◆繝｡繧ｽ繝�繝峨�ｻ繧ｹ繧ｿ繝�
		this.getClasstable();
		return codeDB;

	}

	public void showDatabaseData()
	{
//		System.out.println("");
//		for(String key : classObjectMap.keySet())
//		{
//			System.out.println(key);
//			System.out.println("--Class--");
//			System.out.println("繧ｯ繝ｩ繧ｹ蜷� " + classObjectMap.get(key).getElementName());
//			System.out.println("繧ｯ繝ｩ繧ｹ菫ｮ鬟ｾ蟄� " + classObjectMap.get(key).getAccess());
//			System.out.println("");
//
//			System.out.println("--Field--");
//			List<IElements> fields = fieldObjectMap.get(key);
//			for(IElements field : fields)
//			{
//				System.out.println("繝輔ぅ繝ｼ繝ｫ繝牙錐 " + field.getElementName());
//				System.out.println("蝙� " + field.getElementType());
//				System.out.println("繝輔ぅ繝ｼ繝ｫ繝我ｿｮ鬟ｾ蟄� " + field.getAccess());
//				System.out.println("");
//			}
//
//			System.out.println("--Method--");
//			List<IElements> methods = methodObjectMap.get(key);
//			for(IElements method : methods)
//			{
//				System.out.println("繝｡繧ｽ繝�繝牙錐 " + method.getElementName());
//				System.out.println("謌ｻ繧雁�､蝙� " + method.getElementType());
//				System.out.println("繝｡繧ｽ繝�繝我ｿｮ鬟ｾ蟄� " + method.getAccess());
//				System.out.println("");
//
//				method.showParameter();
//			}
//		}
	}

	public HashMap<String, IElements> getClasstable()
	{
		HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();
		try
		{
			Connection con = daoumlds.createHikariConnection();
			if(con == null)
			{
				return new HashMap<String,IElements>();
			}
			stm = con.createStatement();
			sql = "select * from class;";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int class_num = rs.getInt("class_num");
				String class_name = rs.getString("class_name");
				int class_access = rs.getInt("class_access");
				// int project_num = rs.getInt("project_num");

				// 繧ｯ繝ｩ繧ｹ縺ｮ繝槭ャ繝励↓蜈･繧後ｋ
				classObjectMap.put(class_name, new Class_IElemetns(class_access,class_name));
			}
			daoumlds.closeConnection(con);

		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}
		return classObjectMap;
	}

	public List<IElements> getFieldtable(String className)
	{
		//select * from field,class where class.class_num = field.class_num and class.class_name = "Test2";
		//select * from field where class_num = ( select class_num from class where class_name = "Test2"); select繧剃ｸｭ縺ｫ蜈･繧後ｋ繧�縺､
		List<IElements> fieldList = new ArrayList<IElements>();
		HashMap<String, List<IElements>> fieldObjectMap = new HashMap<String, List<IElements>>();

		try {
			Connection con = daoumlds.createHikariConnection();
			if(con == null)
			{
				return new ArrayList<IElements>();
			}
			stm = con.createStatement();
			// sql = "select * from field where class_num = " + String.valueOf(classNum) + ";";
			sql = "select * from field where class_num = ( select class_num from class where class_name = " + "'" + className + "'" + ");";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int field_num = rs.getInt("class_num");
				// int class_num = rs.getInt("class_num");
				int field_access = rs.getInt("field_access");
				String field_name = rs.getString("field_name");
				String field_type = rs.getString("field_type");

				//縲�蝙九′I縺ｨ縺儀縺ｨ縺九□縺九ｉ螟画鋤
				field_type = transestring.returnTranseType(field_type);

				fieldList.add(new Field_IElements(field_access,field_name,field_type));
			}
			daoumlds.closeConnection(con);
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}



		return fieldList;
	}

	public List<IElements> getMethodtable(String className)
	{
		List<IElements> methodList = new ArrayList<IElements>();
		HashMap<String, List<IElements>> methodObjectMap = new HashMap<String, List<IElements>>();

		try {
			Connection con = daoumlds.createHikariConnection();
			if(con == null)
			{
				return new ArrayList<IElements>();
			}
			stm = con.createStatement();
			//sql = "select * from method where class_num = " + String.valueOf(classNum) + ";";
			sql =  "select * from method where class_num = ( select class_num from class where class_name = " + "'" + className + "'" + ");";
			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				int method_num = rs.getInt("method_num");
				// int class_num = rs.getInt("class_num");
				int method_access = rs.getInt("method_access");
				String method_name = rs.getString("method_name");
				String returnvalue = rs.getString("returnvalue");

				//縲�蝙九′I縺ｨ縺儀縺ｨ縺九□縺九ｉ謌ｻ繧雁�､繧貞､画鋤
				returnvalue = transestring.returnTranseType(returnvalue);

				List<String> paraNameList = getParametaName(method_num);
				List<String> paraTypeList = getParametaType(method_num);

				methodList.add(new Method_IElements(method_access,method_name,returnvalue,paraNameList,paraTypeList));
			}
			daoumlds.closeConnection(con);
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}
		return methodList;

	}

	private List<String> getParametaName(int methodNum)
	{
		List<String> parametarName = new ArrayList<String>();

		try {
			Connection con = daoumlds.createHikariConnection();
			if(con == null)
			{
				return new ArrayList<String>();
			}
			stm = con.createStatement();
			sql = "select * from m_parameta where method_num = " + String.valueOf(methodNum) + ";";
			// 繝代Λ繝｡繧ｿ縺ｯ螟峨∴縺ｪ縺上※繧り｡後￠縺昴≧縲�sql = "select * from method where class_num = ( select class_num from class where class_name = " + className + ");";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				String para_name = rs.getString("para_name");

				parametarName.add(para_name);
			}
			daoumlds.closeConnection(con);
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}

		return parametarName;
	}

	private List<String> getParametaType(int methodNum)
	{
		List<String> parametarType = new ArrayList<String>();

		try {
			Connection con = daoumlds.createHikariConnection();
			if(con == null)
			{
				return new ArrayList<String>();
			}
			stm = con.createStatement();
			sql = "select * from m_parameta where method_num = " + String.valueOf(methodNum) + ";";

			ResultSet rs = stm.executeQuery(sql);

			while(rs.next())
			{
				String para_type = rs.getString("para_type");

				//縲�蝙九′I縺ｨ縺儀縺ｨ縺九□縺九ｉ繝代Λ繝｡繝ｼ繧ｿ縺ｮ蝙九ｒ螟画鋤
				para_type = transestring.returnTranseType(para_type);

				parametarType.add(para_type);
			}
			daoumlds.closeConnection(con);
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}

		return parametarType;
	}

}
