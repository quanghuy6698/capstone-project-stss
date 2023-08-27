package doan2020.SportTournamentSupportSystem.model.Box;

import java.io.Serializable;

import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

public class Seed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int seedNo;
	private Team team;
	private BoxDescription description;
	
	public Seed() {
	}
	
	public Seed(int seedNo) {
		this.description = new BoxDescription((long)seedNo);
	}
	
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public BoxDescription getDescription() {
		return description;
	}
	public void setDescription(BoxDescription description) {
		this.description = description;
	}
	public int getSeedNo() {
		return seedNo;
	}
	public void setSeedNo(int seedNo) {
		this.seedNo = seedNo;
	}
	
}
