<<<<<<< HEAD

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
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import java.util.Collection;
import javax.persistence.OneToMany;



@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity{

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String phoneNumber;
	
	private boolean gender;
	
	private Date dob;
	
	private String email;
	
	private String avatar;
	
	private String background;
	
	private String createdBy;
	
	private Date createdDate;
	
	private String modifiedBy;
	
	private Date modifiedDate;
	
	private String status;
	
	private String url;
	
	

	public UserEntity(Long id, String username, String password, String firstName, String lastName, String address,
			String phoneNumber, boolean gender, Date dob, String email, String avatar, String background,
			String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String status, String url,
			RoleEntity role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.avatar = avatar;
		this.background = background;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.url = url;
		this.role = role;
	}

	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "roleId")
	private RoleEntity role;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_tournament",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "tournament_id")
	)
	private Collection<TournamentEntity> tournamentsList;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
		name = "user_post",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "post_id")
	)
	private Collection<PostEntity> postsList;
	
	@ManyToMany(mappedBy = "usersList")
	private Collection<NotificationEntity> notificationsList;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Collection<ReportEntity> reports;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<CommentEntity> comments;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<PostEntity> posts;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<TeamEntity> teams;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<TournamentEntity> tournaments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<VerificationTokenEntity> verification_tokens;


public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public boolean getGender() {
	return gender;
}

public void setGender(boolean gender) {
	this.gender = gender;
}

public Date getDob() {
	return dob;
}

public void setDob(Date dob) {
	this.dob = dob;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getAvatar() {
	return avatar;
}

public void setAvatar(String avatar) {
	this.avatar = avatar;
}

public String getBackground() {
	return background;
}

public void setBackground(String background) {
	this.background = background;
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

public RoleEntity getRole() {
	return role;
}

public void setRole(RoleEntity role) {
	this.role = role;
}

public Collection<TournamentEntity> getTournamentsList() {
	return tournamentsList;
}

public void setTournamentsList(Collection<TournamentEntity> tournamentsList) {
	this.tournamentsList = tournamentsList;
}

public Collection<PostEntity> getPostsList() {
	return postsList;
}

public void setPostsList(Collection<PostEntity> postsList) {
	this.postsList = postsList;
}

public Collection<NotificationEntity> getNotificationsList() {
	return notificationsList;
}

public void setNotificationsList(Collection<NotificationEntity> notificationsList) {
	this.notificationsList = notificationsList;
}

public Collection<ReportEntity> getReports() {
	return reports;
}

public void setReports(Collection<ReportEntity> reports) {
	this.reports = reports;
}

public Collection<CommentEntity> getComments() {
	return comments;
}

public void setComments(Collection<CommentEntity> comments) {
	this.comments = comments;
}

public Collection<PostEntity> getPosts() {
	return posts;
}

public void setPosts(Collection<PostEntity> posts) {
	this.posts = posts;
}

public Collection<TeamEntity> getTeams() {
	return teams;
}

public void setTeams(Collection<TeamEntity> teams) {
	this.teams = teams;
}

public Collection<TournamentEntity> getTournaments() {
	return tournaments;
}

public void setTournaments(Collection<TournamentEntity> tournaments) {
	this.tournaments = tournaments;
}

public Collection<VerificationTokenEntity> getVerificationTokens() {
	return verification_tokens;
}

public void setVerificationTokens(Collection<VerificationTokenEntity> verification_tokens) {
	this.verification_tokens = verification_tokens;
}


=======

package doan2020.SportTournamentSupportSystem.entity;

import java.util.Collection;
import java.util.Date;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import doan2020.SportTournamentSupportSystem.config.Const;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@ColumnDefault("'Adam'")
	private String firstName;

	@ColumnDefault("'Trần'")
	private String lastName;

	private String address = Const.DEFAULT_PLACE;

	private String phoneNumber;

	@ColumnDefault("0")
	private boolean gender;

	private Date dob = Const.DEFAULT_DATE;

	@Column(nullable = false)
	private String email;

	private String avatar;

	private String background;

	@ColumnDefault("'unknown'")
	private String status;

	private String url = "/?";

	public UserEntity(Long id, String username, String password, String firstName, String lastName, String address,
			String phoneNumber, boolean gender, Date dob, String email, String avatar, String background, String status,
			String url, RoleEntity role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.avatar = avatar;
		this.background = background;
		this.status = status;
		this.url = url;
		this.role = role;
	}

	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	@ManyToOne
	@JoinColumn(name = "roleId", nullable = false)
	private RoleEntity role;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "user_tournament", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tournament_id"))
	private Collection<TournamentEntity> tournamentsList;

	@ManyToMany(mappedBy = "usersList")
	private Collection<NotificationEntity> notificationsList;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Collection<ReportEntity> reports;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<TeamEntity> teams;

	@OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
	private Collection<TournamentEntity> tournaments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<VerificationTokenEntity> verification_tokens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
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

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public Collection<TournamentEntity> getTournamentsList() {
		return tournamentsList;
	}

	public void setTournamentsList(Collection<TournamentEntity> tournamentsList) {
		this.tournamentsList = tournamentsList;
	}

	public Collection<NotificationEntity> getNotificationsList() {
		return notificationsList;
	}

	public void setNotificationsList(Collection<NotificationEntity> notificationsList) {
		this.notificationsList = notificationsList;
	}

	public Collection<ReportEntity> getReports() {
		return reports;
	}

	public void setReports(Collection<ReportEntity> reports) {
		this.reports = reports;
	}

	public Collection<TeamEntity> getTeams() {
		return teams;
	}

	public void setTeams(Collection<TeamEntity> teams) {
		this.teams = teams;
	}

	public Collection<TournamentEntity> getTournaments() {
		return tournaments;
	}

	public void setTournaments(Collection<TournamentEntity> tournaments) {
		this.tournaments = tournaments;
	}

	public Collection<VerificationTokenEntity> getVerificationTokens() {
		return verification_tokens;
	}

	public void setVerificationTokens(Collection<VerificationTokenEntity> verification_tokens) {
		this.verification_tokens = verification_tokens;
	}

>>>>>>> develop
}