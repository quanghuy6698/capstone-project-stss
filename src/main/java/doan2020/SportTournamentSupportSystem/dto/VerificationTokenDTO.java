
package doan2020.SportTournamentSupportSystem.dto;

public class VerificationTokenDTO{

	private Long id;
	private String token;
	private String expiredDateTime;
	private String issuedDateTime;
	private String confirmedDateTime;
	private Long userId;
	private String status;
	private String url;

	public VerificationTokenDTO(){
	}

	public VerificationTokenDTO(Long id, String token, String expiredDateTime, String issuedDateTime, String confirmedDateTime, Long userId, String status, String url){
		this.id = id;
		this.token = token;
		this.expiredDateTime = expiredDateTime;
		this.issuedDateTime = issuedDateTime;
		this.confirmedDateTime = confirmedDateTime;
		this.userId = userId;
		this.status = status;
		this.url = url;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getExpiredDateTime() {
		return expiredDateTime;
	}
	
	public void setExpiredDateTime(String expiredDateTime) {
		this.expiredDateTime = expiredDateTime;
	}
	
	public String getIssuedDateTime() {
		return issuedDateTime;
	}
	
	public void setIssuedDateTime(String issuedDateTime) {
		this.issuedDateTime = issuedDateTime;
	}
	
	public String getConfirmedDateTime() {
		return confirmedDateTime;
	}
	
	public void setConfirmedDateTime(String confirmedDateTime) {
		this.confirmedDateTime = confirmedDateTime;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
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
	

}