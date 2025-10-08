package com.objetdirect.gwt.umldrawer.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.objetdirect.gwt.umldrawer.client.difficulty.SetDifficultyService;
import com.objetdirect.gwt.umldrawer.server.dao.Dao;

public class SetDifficultyServiceImpl extends RemoteServiceServlet implements SetDifficultyService{
	public SetDifficultyServiceImpl(){

	}

	@Override
	public void setDifficulty(int editEventId, int difficulty) {
		System.out.println("SetDifficultyServiceImpl:"+editEventId+":"+difficulty);
		Dao dao = new Dao();
		dao.setDifficulty(editEventId, difficulty);


	}


}
