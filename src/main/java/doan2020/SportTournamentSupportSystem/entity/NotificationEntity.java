
package doan2020.SportTournamentSupportSystem.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener.class)
public class NotificationEntity {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@ColumnDefault("'Không có nội dung'")
	private String content = "no content";

	
	@ColumnDefault("'unknown'")
	private String status;

	private String url = "/?";

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "notification_user", joinColumns = @JoinColumn(name = "notification_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Collection<UserEntity> usersList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Collection<UserEntity> getUsersList() {
		return usersList;
	}

	public void setUsersList(Collection<UserEntity> usersList) {
		this.usersList = usersList;
	}

	public Long getId() {
		return id;
	}

}