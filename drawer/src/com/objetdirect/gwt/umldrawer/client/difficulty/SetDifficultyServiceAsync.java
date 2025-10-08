package com.objetdirect.gwt.umldrawer.client.difficulty;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SetDifficultyServiceAsync {
	public void setDifficulty(int editEventId, int difficulty, AsyncCallback callback);
}
