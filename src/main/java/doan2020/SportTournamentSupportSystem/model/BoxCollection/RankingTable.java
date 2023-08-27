package doan2020.SportTournamentSupportSystem.model.BoxCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.model.Box.RankingTableSlot;
import doan2020.SportTournamentSupportSystem.model.Box.Seed;
import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

public class RankingTable extends ArrayList<RankingTableSlot> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalTeam;

	public RankingTable() {
		super();
	}	

	public RankingTable(int totalTeam) {
		this.totalTeam = totalTeam;
		for(int i=0; i<totalTeam; i++) {
			BoxDescription description = new BoxDescription(6l, i + 1);
			RankingTableSlot slot = new RankingTableSlot();
			slot.setDescription(description);
			this.add(slot);
			
		}
	}
	
	public RankingTable(ArrayList<Team> teams) {
		this.totalTeam = teams.size();
		int seedNo = 0;
		for (Team team: teams) {
			RankingTableSlot slot = new RankingTableSlot(team, seedNo);
			this.add(slot);
			seedNo ++;
		}
		Collections.sort(this, new RankingTableSlot());
	}
	
	public RankingTable(int totalTeam, int tableId) {
		this.totalTeam = totalTeam;
		for(int i=0; i<totalTeam; i++) {
			BoxDescription description = new BoxDescription(tableId, (long) (i + 1));
			RankingTableSlot slot = new RankingTableSlot();
			slot.setDescription(description);
			this.add(slot);
		}
	}
	
	public void applyTeams(ArrayList<TeamEntity> teams) {
		System.out.println("RankingTable: applyTeams: start");
		java.util.Collections.sort(teams, new TeamEntity());
		int seedNo = 0;
		for (RankingTableSlot slot : this) {
			Team t = new Team();
			t.setId(teams.get(seedNo).getId());
			t.setShortName(teams.get(seedNo).getShortName());
			t.setFullName(teams.get(seedNo).getFullName());
			
			
			slot.setTeam(t);
//			slot.updateElo(new Double(-1*seedNo));
			seedNo++;
		}
		System.out.println("RankingTable: applyTeams: finish");
	}
	
	public void applyDescriptions(int tableId) {
		for(int i=0; i<this.totalTeam; i++) {
			BoxDescription description = new BoxDescription(tableId, (long) (i + 1));
			RankingTableSlot slot = this.get(i);
			slot.setDescription(description);
		}
	}
	
	public Double getEloByTeamId(Long teamId) {
		for(RankingTableSlot slot: this) {
			if (slot.getTeam() != null && slot.getTeam().getId().longValue() == teamId.longValue()) {
				return slot.getElo();
			}
		}
		return 0.0;
	}
	
	public void updateByTeamId(Long teamId, Integer score, Double diff, boolean isWin, Double eloBonus) {
		System.out.println("RankingTable: updateByTeamId: teamId: " + teamId);
		for(RankingTableSlot slot: this) {
			if (slot.getTeam() != null && slot.getTeam().getId().longValue() == teamId.longValue()) {
				System.out.println("RankingTable: updateByTeamId: team: " + slot.getTeam());
				System.out.println("Slot totalWin: " + slot.getTotalWin());
				slot.updateScore(score);
				slot.updateDifference(diff);
				if (isWin) {
					slot.updateTotalWin();
				} else {
					slot.updateTotalLose();
				}
				slot.updateElo(eloBonus);
				Collections.sort(this, new RankingTableSlot());
				break;
			}
		}
		Collections.sort(this, new RankingTableSlot());
	}
	
	public void addTeam(Team team) {
		for (RankingTableSlot slot: this) {
			if (slot.getTeam() == null) {
				slot.setTeam(team);
				return;
			}
		}
	}
//	public static void main(String[] args) {
//		RankingTable x = new RankingTable(4, 9);
//		for (RankingTableSlot y: x) {
//			System.out.println(y.getDescription().getDescription());
//		}
//	}

}
