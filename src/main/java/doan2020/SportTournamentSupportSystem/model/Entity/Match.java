package doan2020.SportTournamentSupportSystem.model.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.model.Box.MatchSlot;

public class Match implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id; // id to identify in DB
	private Integer matchNo; // identify in schedule struct
	private Integer roundNo; 
	private String name;
	
	private MatchSlot team1;
	private MatchSlot team2;
	
	private MatchSlot winner;
	private MatchSlot loser;
	
	private String location = Const.DEFAULT_PLACE;
	private String time = Const.DEFAULT_DATE_STRING;
	
	@JsonBackReference
	private MatchEntity realMatch;
	
	/*
	 * status:
	 * -1: chỉ có 1 đội
	 * 0: đã có 2 đội 
	 * 1: 2 đội lấy từ 2 trận thắng của left và right
	 * 2: có sẵn đội 1, đội 2 lấy từ trận thắng của right (round 2 win branch)
	 * 3: 2 đội lấy từ 2 trận thua của left và right (round 1 lose branch)
	 * 4: đội 1 lấy từ đội thua của trận left, đội 2 lấy từ trận thắng của right (round 2+ lose branch)
	 */
	private Integer status;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMatchNo() {
		return matchNo;
	}
	public void setMatchNo(Integer matchNo) {
		this.matchNo = matchNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRoundNo() {
		return roundNo;
	}
	public void setRoundNo(Integer roundNo) {
		this.roundNo = roundNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public MatchSlot getTeam1() {
		return team1;
	}
	public void setTeam1(MatchSlot team1) {
		this.team1 = team1;
	}
	public MatchSlot getTeam2() {
		return team2;
	}
	public void setTeam2(MatchSlot team2) {
		this.team2 = team2;
	}
	public MatchSlot getWinner() {
		return winner;
	}
	public void setWinner(MatchSlot winner) {
		this.winner = winner;
	}
	public MatchSlot getLoser() {
		return loser;
	}
	public void setLoser(MatchSlot loser) {
		this.loser = loser;
	}
	@Override
	public String toString() {
		return "id: " + id + ", matchNo: " + matchNo + ", roundNo: " + roundNo + ", name: \'" + name + "\', team1: {"
				+ team1.toString() + "}, team2: {" + team2.toString() + "}, winner: {" + winner.toString() 
				+ "}, loser: {" + loser + "}, location: \'" + location
				+ "\', time: " + time + ", status: \'" + status + "\'";
	}
	
	
	
}
