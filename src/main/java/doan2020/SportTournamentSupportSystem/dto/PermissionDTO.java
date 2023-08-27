
package doan2020.SportTournamentSupportSystem.dto;

public class PermissionDTO{

	private Long id;
	private String name;
	private String description;
	private boolean canEdit;
	private boolean canDelete;
	private String status;
	private String url;

	public PermissionDTO(){
	}

	public PermissionDTO(Long id, String name, String description, boolean canEdit, boolean canDelete, String status, String url){
		this.id = id;
		this.name = name;
		this.description = description;
		this.canEdit = canEdit;
		this.canDelete = canDelete;
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
	
	public boolean getCanEdit() {
		return canEdit;
	}
	
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	public boolean getCanDelete() {
		return canDelete;
	}
	
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
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