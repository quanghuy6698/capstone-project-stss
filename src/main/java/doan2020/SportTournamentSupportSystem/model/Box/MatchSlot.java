package doan2020.SportTournamentSupportSystem.model.Box;

import java.io.Serializable;

import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

public class MatchSlot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Team team;
	private Integer score = 0;
	private Double difference = 0.0;
	private BoxDescription description;
	
	
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
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Double getDifference() {
		return difference;
	}
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	
	
	
}
