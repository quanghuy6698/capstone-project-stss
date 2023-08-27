
package doan2020.SportTournamentSupportSystem.dto;

public class ResultDTO{

	private Long id;

	private int setNo;

	private int team1Score;

	private int team2Score;

	private String status;

	private String url;
	
	private Long matchId;
	
	public ResultDTO() {
		
	}

	public ResultDTO(Long id, int setNo, int team1Score, int team2Score, String status, String url, Long matchId) {
		super();
		this.id = id;
		this.setNo = setNo;
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		this.status = status;
		this.url = url;
		this.matchId = matchId;
	}
	
	public ResultDTO(int setNo, int team1Score, int team2Score, Long matchId) {
		super();

		this.setNo = setNo;
		this.team1Score = team1Score;
		this.team2Score = team2Score;
		this.matchId = matchId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSetNo() {
		return setNo;
	}

	public void setSetNo(int setNo) {
		this.setNo = setNo;
	}

	public int getTeam1Score() {
		return team1Score;
	}

	public void setTeam1Score(int team1Score) {
		this.team1Score = team1Score;
	}

	public int getTeam2Score() {
		return team2Score;
	}

	public void setTeam2Score(int team2Score) {
		this.team2Score = team2Score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
	
}