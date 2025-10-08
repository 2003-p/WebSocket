package com.objetdirect.gwt.umldrawer.client.difficulty;

import com.google.gwt.user.client.rpc.RemoteService;

public interface SetDifficultyService extends RemoteService{
	public void setDifficulty(int editEventId, int difficulty);

}
