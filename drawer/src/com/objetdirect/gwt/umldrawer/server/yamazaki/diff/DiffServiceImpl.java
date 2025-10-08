package com.objetdirect.gwt.umldrawer.server.yamazaki.diff;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umlapi.client.helpers.Session;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.DiffService;
import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.SavaUMLDSElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.SaveKIfUElements;
import com.objetdirect.gwt.umldrawer.server.yamazaki.difference.DiffElements;

public class DiffServiceImpl extends RemoteServiceServlet  implements DiffService {

	@Override
	public Map<String, Map<String,String>> getDiffMap(String student_id,int exercise_id) {
		// TODO 自動生成されたメソッド・スタブ
		Map<String,Map<String,String>> sendDiffMap = new HashMap<String,Map<String,String>>();

		System.out.println("DiffServiceImpl:" + Session.studentId);
		SaveKIfUElements kifu = new SaveKIfUElements(student_id,exercise_id);
		
		System.out.println("SavaUMLDSElements umlds = new SavaUMLDSElements();");
		SavaUMLDSElements umlds = new SavaUMLDSElements();

		System.out.println("DiffElements diff = new DiffElements();");
		DiffElements diff = new DiffElements();
		diff.diffCheck(kifu, umlds);
		diff.show();

		Map<String, String> sendSurplusDiffMap = diff.getDiffSurplusMap();
		Map<String, String> sendNotHasDiffMap = diff.getDiffNotHasMap();

		sendDiffMap.put("余剰",sendSurplusDiffMap);
		sendDiffMap.put("欠損", sendNotHasDiffMap);

		return sendDiffMap;
	}

}
