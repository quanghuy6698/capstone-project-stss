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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import java.util.Collection;

@Entity
@Table(name = "competitions")
@EntityListeners(AuditingEntityListener.class)
public class CompetitionEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	private boolean groupStage;

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
	@JoinColumn(name = "sportId")
	private SportEntity sport;

	@ManyToOne
	@JoinColumn(name = "mainFormatId")
	private CompetitionFormatEntity mainFormat;

	@ManyToOne
	@JoinColumn(name = "groupStageFormatId")
	private CompetitionFormatEntity groupStageFormat;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Collection<MatchEntity> matches;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Collection<TeamEntity> teams;

	@OneToOne(mappedBy = "competition")
	private CompetitionSettingEntity competition_setting;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Collection<RegisterFormEntity> register_forms;

	public Long getId() {
		return id;
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

	public boolean getGroupStage() {
		return groupStage;
	}

	public void setGroupStage(boolean groupStage) {
		this.groupStage = groupStage;
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

	public SportEntity getSport() {
		return sport;
	}

	public void setSport(SportEntity sport) {
		this.sport = sport;
	}

	public CompetitionFormatEntity getMainFormat() {
		return mainFormat;
	}

	public void setMainFormat(CompetitionFormatEntity mainFormat) {
		this.mainFormat = mainFormat;
	}

	public CompetitionFormatEntity getGroupStageFormat() {
		return groupStageFormat;
	}

	public void setGroupStageFormat(CompetitionFormatEntity groupStageFormat) {
		this.groupStageFormat = groupStageFormat;
	}

	public Collection<MatchEntity> getMatches() {
		return matches;
	}

	public void setMatches(Collection<MatchEntity> matches) {
		this.matches = matches;
	}

	public Collection<TeamEntity> getTeams() {
		return teams;
	}

	public void setTeams(Collection<TeamEntity> teams) {
		this.teams = teams;
	}

	public CompetitionSettingEntity getCompetitionSetting() {
		return competition_setting;
	}

	public void setCompetitionSetting(CompetitionSettingEntity competition_setting) {
		this.competition_setting = competition_setting;
	}

	public Collection<RegisterFormEntity> getRegisterForms() {
		return register_forms;
	}

	public void setRegisterForms(Collection<RegisterFormEntity> register_forms) {
		this.register_forms = register_forms;
	}

=======

package doan2020.SportTournamentSupportSystem.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "competitions")
public class CompetitionEntity {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@ColumnDefault("'Chưa có mô tả.'")
	private String description;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	private boolean hasGroupStage;

	@ColumnDefault("'unknown'")
	private String status;

	private String url = "/?";

	@ManyToOne
	@JoinColumn(name = "tournamentId", nullable = false)
	private TournamentEntity tournament;

	@ManyToOne
	@JoinColumn(name = "sportId", nullable = false)
	private SportEntity sport;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Collection<MatchEntity> matches;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Collection<TeamEntity> teams;

	@OneToOne(mappedBy = "competition")
	private GroupStageSettingEntity groupStageSetting;

	@OneToOne(mappedBy = "competition")
	private FinalStageSettingEntity finalStageSetting;

	public Long getId() {
		return id;
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

	public SportEntity getSport() {
		return sport;
	}

	public void setSport(SportEntity sport) {
		this.sport = sport;
	}

	public Collection<MatchEntity> getMatches() {
		return matches;
	}

	public void setMatches(Collection<MatchEntity> matches) {
		this.matches = matches;
	}

	public Collection<TeamEntity> getTeams() {
		return teams;
	}

	public void setTeams(Collection<TeamEntity> teams) {
		this.teams = teams;
	}

	public GroupStageSettingEntity getGroupStageSetting() {
		return groupStageSetting;
	}

	public void setGroupStageSetting(GroupStageSettingEntity groupStageSetting) {
		this.groupStageSetting = groupStageSetting;
	}

	public FinalStageSettingEntity getFinalStageSetting() {
		return finalStageSetting;
	}

	public void setFinalStageSetting(FinalStageSettingEntity finalStageSetting) {
		this.finalStageSetting = finalStageSetting;
	}

	public boolean isHasGroupStage() {
		return hasGroupStage;
	}

	public void setHasGroupStage(boolean hasGroupStage) {
		this.hasGroupStage = hasGroupStage;
	}

>>>>>>> develop
}