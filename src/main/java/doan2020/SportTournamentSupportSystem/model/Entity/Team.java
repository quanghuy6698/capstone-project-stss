package doan2020.SportTournamentSupportSystem.model.Entity;

import java.io.Serializable;

public class Team implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String fullName;
	private String shortName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", fullName=" + fullName + ", shortName=" + shortName + "]";
	}
	
	
	
}
