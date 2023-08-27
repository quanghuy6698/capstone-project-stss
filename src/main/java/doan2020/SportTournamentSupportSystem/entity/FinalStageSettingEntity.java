package doan2020.SportTournamentSupportSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "final_stage_settings")
@EntityListeners(AuditingEntityListener.class)
public class FinalStageSettingEntity {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}
	
	@OneToOne
	@JoinColumn(name = "competition_id", nullable = false)
	private CompetitionEntity competition;

	@Column(nullable = false)
	@ColumnDefault("0")
	private boolean hasHomeMatch = false;

	@ManyToOne
	@JoinColumn(name = "formatId", nullable = false)
	@ColumnDefault("1")
	private FormatEntity format;

	@ColumnDefault("'unknown'")
	private String status;

	private String url = "/?";

	public boolean isHasHomeMatch() {
		return hasHomeMatch;
	}

	public void setHasHomeMatch(boolean hasHomeMatch) {
		this.hasHomeMatch = hasHomeMatch;
	}

	public FormatEntity getFormat() {
		return format;
	}

	public void setFormat(FormatEntity format) {
		this.format = format;
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

	public CompetitionEntity getCompetition() {
		return competition;
	}

	public void setCompetition(CompetitionEntity competition) {
		this.competition = competition;
	}
	
	

}
