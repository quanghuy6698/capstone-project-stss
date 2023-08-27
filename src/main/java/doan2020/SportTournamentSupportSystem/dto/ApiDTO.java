
package doan2020.SportTournamentSupportSystem.dto;

public class ApiDTO{

	private Long id;
	private String name;
	private String description;
	private String method;
	private String status;
	private String url;

	public ApiDTO(){
	}

	public ApiDTO(Long id, String name, String description, String method, String status, String url){
		this.id = id;
		this.name = name;
		this.description = description;
		this.method = method;
		this.status = status;
		this.url = url;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
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