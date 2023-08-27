
package doan2020.SportTournamentSupportSystem.dto;

public class MatchDTO{

	private Long id;

	private String name;

	private String time;

	private String location;

	private String status;

	private String url;
	
	private Long competitionId;
	
	private Long team1Id;
	
	private Long team2Id;
	
	private Long winnerId;
	
	private Long loserId;
	
	private float team1Bonus;

	private float team2Bonus;
	
	public MatchDTO() {
		
	}

	public MatchDTO(Long id, String name, String time, String location, String status, String url, Long competitionId,
			Long team1Id, Long team2Id, Long winnerId, Long loserId, float team1Bonus, float team2Bonus) {
		super();
		this.id = id;
		this.name = name;
		this.time = time;
		this.location = location;
		this.status = status;
		this.url = url;
		this.competitionId = competitionId;
		this.team1Id = team1Id;
		this.team2Id = team2Id;
		this.winnerId = winnerId;
		this.loserId = loserId;
		this.team1Bonus = team1Bonus;
		this.team2Bonus = team2Bonus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Long getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(Long competitionId) {
		this.competitionId = competitionId;
	}

	public Long getTeam1Id() {
		return team1Id;
	}

	public void setTeam1Id(Long team1Id) {
		this.team1Id = team1Id;
	}

	public Long getTeam2Id() {
		return team2Id;
	}

	public void setTeam2Id(Long team2Id) {
		this.team2Id = team2Id;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}

	public Long getLoserId() {
		return loserId;
	}

	public void setLoserId(Long loserId) {
		this.loserId = loserId;
	}

	public float getTeam1Bonus() {
		return team1Bonus;
	}

	public void setTeam1Bonus(float team1Bonus) {
		this.team1Bonus = team1Bonus;
	}

	public float getTeam2Bonus() {
		return team2Bonus;
	}

	public void setTeam2Bonus(float team2Bonus) {
		this.team2Bonus = team2Bonus;
	}
	
	
	
	

}