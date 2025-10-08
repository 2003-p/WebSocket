package com.objetdirect.gwt.umldrawer.server.yamazaki.diff;

import java.sql.Date;
import java.util.HashMap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.DiffService;
import com.objetdirect.gwt.umldrawer.client.yamazaki.diffservice.RecordCheckBoxStateService;
import com.objetdirect.gwt.umldrawer.server.yamazaki.dao.Dao_kifu6;

public class RecordCheckBoxStateServiceImpl extends RemoteServiceServlet  implements RecordCheckBoxStateService {

	private final String UML_DIFF_SPECIES = "UMLDiffFixHistory";

	@Override
	public boolean recordCheckboxState(String student_id,
										int exercise_id,
										HashMap<String,Integer> tmpRecordState,
										String openDate,
										HashMap<String,String> reson)
	{

		// TODO 自動生成されたメソッド・スタブ
		System.out.println("レコードのImple");
		Dao_kifu6 dao = new Dao_kifu6(student_id,exercise_id);
		int tablecount = dao.recordCount(UML_DIFF_SPECIES) + 1;
		int daialogID = dao.daialogCount() + 1;
		System.out.println("dialog No" + daialogID);
		dao.recordTimeStamp(daialogID,openDate);

		for(String diffmessage : tmpRecordState.keySet())
		{
			String reason = reson.get(diffmessage);
			if(reason == null)
			{
				reason = "とくに理由はないよ";
			}
			System.out.println("dialog No" + daialogID);
			dao.insertCheckBoxRecord(tablecount++,
									daialogID,
									diffmessage,
									tmpRecordState.get(diffmessage),
									reason,
									student_id);
		}


		return true;
	}

}
