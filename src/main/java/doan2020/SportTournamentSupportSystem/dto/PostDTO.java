
package doan2020.SportTournamentSupportSystem.dto;

public class PostDTO{

	private Long id;
	private String title;
	private Long creatorId;
	private Long tournamentId;
	private boolean systemPost;
	private String content;
	private String status;
	private String url;

	public PostDTO(){
	}

	public PostDTO(Long id, String title, Long creatorId, Long tournamentId, boolean systemPost, String content, String status, String url){
		this.id = id;
		this.title = title;
		this.creatorId = creatorId;
		this.tournamentId = tournamentId;
		this.systemPost = systemPost;
		this.content = content;
		this.status = status;
		this.url = url;
	}


	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	public Long getTournamentId() {
		return tournamentId;
	}
	
	public void setTournamentId(Long tournamentId) {
		this.tournamentId = tournamentId;
	}
	
	public boolean getSystemPost() {
		return systemPost;
	}
	
	public void setSystemPost(boolean systemPost) {
		this.systemPost = systemPost;
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
	

}