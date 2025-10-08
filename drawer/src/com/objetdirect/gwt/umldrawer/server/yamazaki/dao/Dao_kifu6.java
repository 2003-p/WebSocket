package com.objetdirect.gwt.umldrawer.server.yamazaki.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.repackaged.org.apache.commons.codec.binary.Base64;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Class_IElemetns;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Field_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.Method_IElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.SplitElement;
import com.objetdirect.gwt.umldrawer.server.yamazaki.elements.TranseString;


public class Dao_kifu6
{
	TranseString tranceAccessSingleton = TranseString.getInstance();
	private Connection con;
	private Statement stm;

	private String sql;
	private ResultSet rs;

	private String student_id;
	private int exercise_id;

	// TranseString縺ｮenum縺後↑繧薙°菴ｿ縺医↑縺�縺九ｉ莉｣逕ｨ竊�
	final private int ABSTRACT = 1024;
	final private int INTERFACE = 512;

	SplitElement splitObject = SplitElement.getInstance();

//	private ArrayList<String> umlClassList= new ArrayList<>(); //UML縺ｮ繧ｯ繝ｩ繧ｹ縺ｮ繝ｪ繧ｹ繝�
//	private HashMap<String, List<String>> umlFieldMap = new HashMap<>(); //UML縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ蜷�, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨�ｮ繝ｪ繧ｹ繝�
//	private HashMap<String, List<String>> umlMethodMap = new HashMap<>(); //UML縺ｮ繝｡繧ｽ繝�繝峨�ｮ繝ｪ繧ｹ繝� key=繧ｯ繝ｩ繧ｹ逶ｮ, value=key繧ｯ繝ｩ繧ｹ縺ｮ繝｡繧ｽ繝�繝峨�ｮ繝ｪ繧ｹ繝�
//	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?Unicode=true&characterEncoding=UTF8";
	private final static String DRIVER_URL="jdbc:mysql://localhost:3306/kifu6?useUnicode=true&characterEncoding=UTF8";
	//final private String DATABASE_NAME = "kifu6";

	private final DatabaseAccesser daokifu6;

	public Dao_kifu6(String student_id,int exercise_id)
	{
		daokifu6 = DatabaseAccesser.getInstance();
		// createHikariConfig(DRIVER_URL);
		this.student_id = student_id;
		this.exercise_id = exercise_id;

	}

	public void showDatabaseData()
	{

	}

	/*
	 * HACK maybe-later
	 * 謚ｼ縺励◆譎ゅ↓start縺ｮ繧ｿ繧､繝�繧ｹ繧ｿ繝ｳ繝励Ξ繧ｳ繝ｼ繝峨ｒ蜈･繧後→縺�縺ｦ
	 * 蠕後〒update縺吶ｋ繧�繧頑婿
	 *
	 * now 蠑墓焚縺ｧstart縺ｮ繧ｿ繧､繝�繧ｹ繧ｿ繝ｳ繝励ｒ騾√ｋ繧�繧頑婿
	 */


	public void insertCheckBoxRecord(final int tablecount,		// 荳ｻ繧ｭ繝ｼ
										  final int daialogID,		// 繝�繧､繧｢繝ｭ繧ｰ縺ｮ蛻�鬘樊焚
										  final String showMessage,	// 繝�繧､繧｢繝ｭ繧ｰ縺ｫ陦ｨ遉ｺ縺輔ｌ縺溘Γ繝�繧ｻ繝ｼ繧ｸ
										  final int stateCheckBox,
										  final String reason,
										  final String person)
	{
		String sql = "insert into UMLDiffFixHistory(historyID,dialogID,diffSpecies,checkbool,diffcontext,reason,person) values (?,?,?,?,?,?,?)";
		try {
			con = daokifu6.createHikariConnection();
			if(con == null)
			{
				return ;
			}
			try(PreparedStatement ps = con.prepareStatement(sql)){
				ps.setInt(1, tablecount); 		// 繝ｬ繧ｳ繝ｼ繝峨�ｮ逡ｪ蜿ｷ
				ps.setInt(2, daialogID);		// 髢九＞縺溘ム繧､繧｢繝ｭ繧ｰ縺ｮ繧ｰ繝ｫ繝ｼ繝励∩縺溘＞縺ｪ繧ゅ�ｮ
				ps.setInt(3, 1);				// 蟾ｮ蛻�縺ｮ遞ｮ鬘槭��縺ｪ縺上※繧ゅ＞縺�縺九ｂ
				ps.setInt(4, stateCheckBox);			// 繝√ぉ繝�繧ｯ縺､縺�縺ｦ繧�
				ps.setString(5, showMessage);	// 螟画鋤縺ｮ譁�
				ps.setString(6,reason);
				ps.setString(7,person);
				System.out.format("tablecount:%d  daialogID:%d showMessage:%s stateCheckBox:%d  reason:%s",tablecount,daialogID,showMessage,stateCheckBox,reason);

				ps.executeUpdate();
				ps.close();
				con.close();
			} catch (Exception e) {
				con.rollback();
				System.out.println("rollback");
				throw e;
			}
		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}
	}

	// HACK�ｼ咼ate縺ｮ螟画鋤縺梧ｭ｣遒ｺ縺ｫ縺ｯ豁｣縺励￥縺ｪ縺�繧峨＠縺�
	public void recordTimeStamp(final int daialogID,final String openDateStr)
	{
		// 繧ｿ繧､繝�繧ｹ繧ｿ繝ｳ繝�
		final SimpleDateFormat pushdateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// open
		Date openDate = new Date();
		// 螟画鋤譎�
		Date conversionDate = new Date();
		try {
			openDate = pushdateFormate.parse(openDateStr);

			final String pushConversion = pushdateFormate.format(conversionDate);
			conversionDate = pushdateFormate.parse(pushConversion);
		} catch (ParseException e1) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e1.printStackTrace();
		}

		// sql逕ｨ縺ｫDate繧探imestamp縺ｫ螟画鋤
		final Timestamp toSQLOpenDate = new Timestamp(openDate.getTime());
		final Timestamp toSQLPushConversionDate = new Timestamp(conversionDate.getTime());
		//final java.sql.Date toSQLPushConversionDate = java.sql.Date.valueOf(pushConversion);

		String sql = "insert into conversiontimestamp(dialogID,opendate,conversiondate) values (?,?,?)";

		con = daokifu6.createHikariConnection();
		if(con == null)
		{
			return ;
		}
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ps.setInt(1, daialogID); 					// 繝�繧､繧｢繝ｭ繧ｰ縺ｮID
			ps.setTimestamp(2, toSQLOpenDate);			// 繝�繧､繧｢繝ｭ繧ｰ髢九＞縺滓凾縺ｮ譎る俣
			ps.setTimestamp(3, toSQLPushConversionDate);// 螟画鋤謚ｼ縺輔ｌ縺溘→縺阪�ｮ譎る俣

			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}
	}

	// 菫晏ｭ倥＆繧後※繧九Ξ繧ｳ繝ｼ繝峨�ｮ謨ｰ繧貞叙蠕励��public縺倥ｃ縺ｪ縺上※繧ゅ＞縺�縺九ｂ
	public int recordCount(String tablename)
	{
		String sql = "select count(*) from " + tablename + ";";
		int count = 0;
		try {
			con = daokifu6.createHikariConnection();
			if(con == null)
			{
				return 0;
			}
			stm = con.createStatement();
			rs = stm.executeQuery(sql);

			while(rs.next())
			{
				// codeDB = new String(Base64.getDecoder().decode(rs.getString(1)));
				count = rs.getInt(1);
			}
			daokifu6.closeConnection(con);

		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();

		}
		System.out.println("繝ｬ繧ｳ繝ｼ繝峨�ｮ縺九★" + count);
		return count;
	}

	// 繝�繧､繧｢繝ｭ繧ｰ縺ｮ謨ｰ繧貞叙蠕�
	public int daialogCount()
	{
		int daialogID = 0;
		String sql = "SELECT * FROM CONVERSIONTIMESTAMP ORDER BY dialogID DESC LIMIT 1;";

		System.out.println("getCanvasURL:HikariCP縺ｧ繧ｳ繝阪け繧ｷ繝ｧ繝ｳ逕滓��");

		try {
			System.out.println("student_id:" +this.student_id + " exercise_id:" + String.valueOf(this.exercise_id));

			con = daokifu6.createHikariConnection();
			if(con == null)
			{
				return 0;
			}
			stm = con.createStatement();
			rs = stm.executeQuery(sql);

			while(rs.next())
			{
				daialogID = rs.getInt("dialogID");
			}

			rs.close();
			stm.close();
			daokifu6.closeConnection(con);

		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}
		System.out.println("繝�繧､繧｢繝ｭ繧ｰ縺ｮ謨ｰ縺ｮ縺九★" + daialogID);
		return daialogID;
	}

	public String getDatabaseElements()
	{

		String codeDB = "";
		System.out.println("getCanvasURL:HikariCP縺ｧ繧ｳ繝阪け繧ｷ繝ｧ繝ｳ逕滓��");

		try {
			System.out.println("student_id:" +this.student_id + " exercise_id:" + String.valueOf(this.exercise_id));
			con = daokifu6.createHikariConnection();
			if(con == null)
			{
				return "This program doesn't create connection";
			}
			stm = con.createStatement();
			sql = "select canvas_url from edit_event where student_id='" + this.student_id + "' and exercises_id = " + String.valueOf(this.exercise_id) + " and canvas_url is not null order by edit_event_id desc limit 1";
//			sql = "select * from edit_event where student_id = '"+ student_id + "' and exercises_id = " + String.valueOf(exercise_id) +
//					"and canvas_url is not null and edit_event_id = (select max(edit_event_id) from edit_event where student_id = '" + student_id + "'and exercises_id = " + String.valueOf(exercise_id) + "and canvas_url is not null)";
			rs = stm.executeQuery(sql);

			while(rs.next())
			{
				// codeDB = new String(Base64.getDecoder().decode(rs.getString(1)));
				codeDB = base64ToString(rs.getString(1));
			}
			daokifu6.closeConnection(con);

		} catch (SQLException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
		}

		return codeDB;
	}

	boolean checkNote(String[] splitCodeDB, int index)
	{
		System.out.println(splitCodeDB[index]);
		int start = splitCodeDB[index].indexOf("]") + 1;
		int end = splitCodeDB[index].indexOf("$");
		String umltype = splitCodeDB[index].substring(start,end);
		System.out.println("umltype�ｼ�" + umltype);
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
		System.out.println("ArtifactId�ｼ�" + artifactId);

		return artifactId;
	}

	public IElements splitClassName(String[] splitCodeDB,int index)
	{

		// 繧ｯ繝ｩ繧ｹ蜷�      
		int x = splitCodeDB[index].indexOf("!");
		int y = splitCodeDB[index].indexOf("!",x+1);
		String tmpClassName = "";
		IElements classElemetns = null;
		HashMap<String, IElements> classObjectMap = new HashMap<String, IElements>();

		if(x!=-1 && y!=-1 && !(splitCodeDB[index].substring(x+1, y).contains("<")))
		{
			// umlClassList.add(splitCodeDB[index].substring(x+1, y));
			tmpClassName = splitCodeDB[index].substring(x+1, y).trim();
			// System.err.println("繧ｯ繝ｩ繧ｹ霑ｽ蜉�"+splitCodeDB[index].substring(x+1, y)); 
			// Abstract縺ｨInterfce繧定ｪｿ縺ｹ繧区ｱ壹＞繧�繧頑婿
			// y縺靴lass縺ｮ邨ゅｏ繧翫�ｮ�ｼ√�槭�ｼ繧ｯ
			int stereoEndIndex = splitCodeDB[index].indexOf("!",y + 1);
			//System.out.println("x:" + x + " y:" + y + " stereoEndIndex:" + stereoEndIndex);
			String stereo = splitCodeDB[index].substring(y + 1, stereoEndIndex).trim();
			// System.out.println("StereoType:" + splitCodeDB[index].substring(y + 1, stereoEndIndex).trim());
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

			System.out.println("Access:" + access);
			classObjectMap.put(tmpClassName, new Class_IElemetns(access,tmpClassName));
			classElemetns = new Class_IElemetns(access,tmpClassName);
		}

		return classElemetns;
	}

	public List<IElements> splitField(String className,String[] patternURLSplit2)
	{
		// 竊�1縺､縺ｮ繧ｯ繝ｩ繧ｹ縺ｮ荳ｭ縺ｮ螟画焚縺ｮ郢ｰ繧願ｿ斐＠     
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
			//System.out.println("螟画焚霑ｽ蜉�"+index2);
			if(start != -1 && end != -1)
			{
				String[] fields = patternURLSplit2[j].substring(start, end).split("%", 0);


				for(String field : fields)
				{
					field = field.replaceAll(" ","");
					System.out.println("繝輔ぅ繝ｼ繝ｫ繝�" + field);

					//繝輔ぅ繝ｼ繝ｫ繝峨�ｮsplit
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
			// 菴輔ｂ譖ｸ縺�縺ｦ縺ｪ縺九▲縺溘ｉ
			//umlFieldMap.put(className, new ArrayList<String>());
		}

		return fieldList;
	}

	public List<IElements> splitMethod(String className, String[] patternURLSplit2)
	{
		// 繧ｯ繝ｩ繧ｹ縺斐→縺ｮ髢｢謨ｰ荳�隕ｧ繧貞叙蠕�
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


					//()縺ｮ繧､繝ｳ繝�繝�繧ｯ繧ｹ
					int parenthesesStart = method.indexOf("(") + 1;
					int parenthesesEnd = method.indexOf(")");

					//縲�accecc_and_methodName縲�(parametar) : returnType
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
			// 菴輔ｂ譖ｸ縺�縺ｦ縺ｪ縺九▲縺溘ｉ
			// methodObjectMap.put(className, new ArrayList<String>());
		}

		return methodList;
	}

	private byte[] stringToBytes(String str) {
		try {
			//String -> byte[]
			byte [] bytes = str.getBytes("UTF-8");  //String.getBytes();    or      String.getBytes(encoding);
			//byte [] -> String
			String xx = new String(bytes, "UTF-8"); //
			return bytes;
		} catch (UnsupportedEncodingException e) {
			// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
			e.printStackTrace();
			return null;
		}
	}

	private String base64ToString(String base64){
		String decodedString=null;
		if(base64!=null){
			byte[] bytes = stringToBytes(base64);
			byte[] decoded = Base64.decodeBase64(bytes);
			try {
				decodedString = new String(decoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return decodedString;
	}


}
