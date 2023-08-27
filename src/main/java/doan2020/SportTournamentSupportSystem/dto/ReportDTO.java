
package doan2020.SportTournamentSupportSystem.dto;

public class ReportDTO{
	private Long id;

	private String subject;

	private String content;

	private String status;

	private String url;
	
	private String type;
	
	private Long senderId;
	
	private Long tournamentId;

	public ReportDTO() {
		
	}

//	

	public ReportDTO(Long id, String subject, String content, String status, String url, String type, Long senderId,
			Long tournamentId) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.status = status;
		this.url = url;
		this.type = type;
		this.senderId = senderId;
		this.tournamentId = tournamentId;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	
}