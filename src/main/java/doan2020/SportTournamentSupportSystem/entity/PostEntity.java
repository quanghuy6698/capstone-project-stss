
package doan2020.SportTournamentSupportSystem.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sun.istack.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.Collection;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;



@Entity
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class PostEntity{

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	
	private boolean systemPost;
	
	private String content;
	
	private String createdBy;
	
	private Date createdDate;
	
	private String modifiedBy;
	
	private Date modifiedDate;
	
	private String status;
	
	private String url;
	

	@ManyToOne
	@JoinColumn(name = "tournamentId")
	private TournamentEntity tournament;
	
	@ManyToOne
	@JoinColumn(name = "creatorId")
	private UserEntity creator;
	
	@ManyToMany(mappedBy = "postsList")
	private Collection<UserEntity> usersList;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Collection<CommentEntity> comments;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Collection<NotificationEntity> notifications;


public Long getId() {
	return id;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
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

public String getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

public String getModifiedBy() {
	return modifiedBy;
}

public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
}

public Date getModifiedDate() {
	return modifiedDate;
}

public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
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

public TournamentEntity getTournament() {
	return tournament;
}

public void setTournament(TournamentEntity tournament) {
	this.tournament = tournament;
}

public UserEntity getCreator() {
	return creator;
}

public void setCreator(UserEntity creator) {
	this.creator = creator;
}

public Collection<UserEntity> getUsersList() {
	return usersList;
}

public void setUsersList(Collection<UserEntity> usersList) {
	this.usersList = usersList;
}

public Collection<CommentEntity> getComments() {
	return comments;
}

public void setComments(Collection<CommentEntity> comments) {
	this.comments = comments;
}

public Collection<NotificationEntity> getNotifications() {
	return notifications;
}

public void setNotifications(Collection<NotificationEntity> notifications) {
	this.notifications = notifications;
}


}