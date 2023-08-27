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
import java.util.Collection;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Table(name = "tournaments")
@EntityListeners(AuditingEntityListener.class)
public class TournamentEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String fullName;

	private String shortName;

	private String description;

	private String openingLocation;

	private Date openingTime;

	private String closingLocation;

	private Date closingTime;

	private String donor;

	private String createdBy;

	private Date createdDate;

	private String modifiedBy;

	private Date modifiedDate;

	private String status;

	private String url;

	@ManyToOne
	@JoinColumn(name = "creatorId")
	private UserEntity creator;

	@ManyToMany(mappedBy = "tournamentsList")
	private Collection<UserEntity> usersList;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	private Collection<ReportEntity> reports;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	private Collection<CompetitionEntity> competitions;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	private Collection<PostEntity> posts;

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpeningLocation() {
		return openingLocation;
	}

	public void setOpeningLocation(String openingLocation) {
		this.openingLocation = openingLocation;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingLocation() {
		return closingLocation;
	}

	public void setClosingLocation(String closingLocation) {
		this.closingLocation = closingLocation;
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}

	public String getDonor() {
		return donor;
	}

	public void setDonor(String donor) {
		this.donor = donor;
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

	public Collection<ReportEntity> getReports() {
		return reports;
	}

	public void setReports(Collection<ReportEntity> reports) {
		this.reports = reports;
	}

	public Collection<CompetitionEntity> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Collection<CompetitionEntity> competitions) {
		this.competitions = competitions;
	}

	public Collection<PostEntity> getPosts() {
		return posts;
	}

	public void setPosts(Collection<PostEntity> posts) {
		this.posts = posts;
	}

=======

package doan2020.SportTournamentSupportSystem.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tournaments")
@EntityListeners(AuditingEntityListener.class)
public class TournamentEntity {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fullName;

	@Column(nullable = false)
	private String shortName;

	@ColumnDefault("'Chưa có mô tả.'")
	private String description;

	private String openingLocation;

	private Date openingTime;

	private String closingLocation;

	private Date closingTime;

	@ColumnDefault("'Không có nhà tài trợ'")
	private String donor;

	@ColumnDefault("'unknown'")
	private String status;

	private String url = "/?";

	private String avatar;

	private String background;

	private Date openRegistrationTime;
	private Date closeRegistrationTime;


	@ManyToOne
	@JoinColumn(name = "creatorId", nullable = false)
	private UserEntity creator;

	@ManyToMany(mappedBy = "tournamentsList")
	private Collection<UserEntity> usersList;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	private Collection<ReportEntity> reports;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
	private Collection<CompetitionEntity> competitions;

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpeningLocation() {
		return openingLocation;
	}

	public void setOpeningLocation(String openingLocation) {
		this.openingLocation = openingLocation;
	}

	public Date getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Date openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingLocation() {
		return closingLocation;
	}

	public void setClosingLocation(String closingLocation) {
		this.closingLocation = closingLocation;
	}

	public Date getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Date closingTime) {
		this.closingTime = closingTime;
	}

	public String getDonor() {
		return donor;
	}

	public void setDonor(String donor) {
		this.donor = donor;
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

	public Collection<ReportEntity> getReports() {
		return reports;
	}

	public void setReports(Collection<ReportEntity> reports) {
		this.reports = reports;
	}

	public Collection<CompetitionEntity> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(Collection<CompetitionEntity> competitions) {
		this.competitions = competitions;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getOpenRegistrationTime() {
		return openRegistrationTime;
	}

	public void setOpenRegistrationTime(Date openRegistrationTime) {
		this.openRegistrationTime = openRegistrationTime;
	}

	public Date getCloseRegistrationTime() {
		return closeRegistrationTime;
	}

	public void setCloseRegistrationTime(Date closeRegistrationTime) {
		this.closeRegistrationTime = closeRegistrationTime;
	}

>>>>>>> develop
}