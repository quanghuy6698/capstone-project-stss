package doan2020.SportTournamentSupportSystem.model;

public class Ranks {

	private int rankNumber;
	private String teamName;
	private String managementTeam;
	private String matchHistory;
	private int score;
	private Long competitionId;
	
	public Ranks() {
		
	}
	
	
	
	public Ranks(int rankNumber, String teamName, String managementTeam, String matchHistory, int score,
			Long competitionId) {
		super();
		this.rankNumber = rankNumber;
		this.teamName = teamName;
		this.managementTeam = managementTeam;
		this.matchHistory = matchHistory;
		this.score = score;
		this.competitionId = competitionId;
	}



	public int getRankNumber() {
		return rankNumber;
	}
	public void setRankNumber(int rankNumber) {
		this.rankNumber = rankNumber;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getManagementTeam() {
		return managementTeam;
	}
	public void setManagementTeam(String managementTeam) {
		this.managementTeam = managementTeam;
	}
	public String getMatchHistory() {
		return matchHistory;
	}
	public void setMatchHistory(String matchHistory) {
		this.matchHistory = matchHistory;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public Long getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(Long competitionId) {
		this.competitionId = competitionId;
	}
	
}
