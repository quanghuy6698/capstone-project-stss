package doan2020.SportTournamentSupportSystem.model;

import java.io.Serializable;

public class CanSaveToFileObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Object obj;

	public CanSaveToFileObject(Object o) {
		this.obj = o;
	}
	
	public Object getObject() {
		return this.obj;
	}

}
