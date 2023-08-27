package doan2020.SportTournamentSupportSystem.model.Schedule;

import java.io.Serializable;
import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.model.BoxCollection.RankingTable;
import doan2020.SportTournamentSupportSystem.model.BoxCollection.SeedList;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

abstract public class ScheduleStruct implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int totalTeam;
	protected SeedList seedList;
	protected Integer totalRound;
	protected ArrayList<Match> matches = new ArrayList<>();
	protected RankingTable rankingTable;
	protected int tableId = -1;

	public ScheduleStruct() {
	}

	public ScheduleStruct(int totalTeam) {
		this.totalTeam = totalTeam;
		this.seedList = new SeedList(totalTeam);
		this.rankingTable = new RankingTable(totalTeam);
	}

	public ScheduleStruct(int totalTeam, int tableId) {
		this.tableId = tableId;
		this.totalTeam = totalTeam;
		this.seedList = new SeedList(totalTeam);
		this.rankingTable = new RankingTable(totalTeam, tableId);
	}

	protected void updateTeams(ArrayList<TeamEntity> teams) {
		if (teams.size() != this.totalTeam) {
			return;
		}
		this.seedList.applyTeams(teams);
		this.rankingTable.applyTeams(teams);

	}

	abstract protected void applyDescriptions();

	abstract protected void applyTeams();

	public void applyTeams(ArrayList<TeamEntity> teams) {
		this.updateTeams(teams);
		this.applyTeams();
	}

	public void applyDescriptions(int firstSeed) {
		if (firstSeed < 1) {
			return;
		}

		this.seedList.applyDescriptions(firstSeed);
		this.applyDescriptions();
	}

	public void applyDescriptions(ArrayList<BoxDescription> descriptions) {
		if (descriptions.size() != totalTeam) {
			return;
		}

		this.seedList.applyDescriptions(descriptions);
		this.applyDescriptions();
	}

	public RankingTable getRankingTable() {
		return this.rankingTable;
	}

	public int getTotalTeam() {
		return totalTeam;
	}

	public Integer getTotalRound() {
		return totalRound;
	}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		if (tableId < 0) {
			return;
		}
		this.tableId = tableId;
		this.rankingTable.applyDescriptions(tableId);
	}

	public SeedList getSeedList() {
		return seedList;
	}

}
