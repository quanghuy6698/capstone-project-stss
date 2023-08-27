package doan2020.SportTournamentSupportSystem.model.Box;

import java.io.Serializable;
import java.util.Comparator;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.model.Entity.Team;
import doan2020.SportTournamentSupportSystem.model.Indexing.BoxDescription;

public class RankingTableSlot implements Serializable, Comparator<RankingTableSlot> {

	private static final long serialVersionUID = 1L;

	private Team team;
	private BoxDescription description;
	private Double difference = 0.0;
	private Integer score = 0;
	private Integer totalWin = 0;
	private Integer totalLose = 0;
	private Double elo = Const.DEFAULT_ELO;

	public RankingTableSlot(Team team) {
		this.team = team;
	}

	public RankingTableSlot(Team team, int seedNo) {
		this.team = team;
//		elo -= new Double(seedNo);
	}

	public RankingTableSlot() {
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Double getDifference() {
		return difference;
	}

	public void updateDifference(Double difference) {
		this.difference += difference;
	}

	public Integer getScore() {
		return score;
	}

	public void updateScore(Integer score) {
		this.score += score;
	}

	@Override
	public int compare(RankingTableSlot o1, RankingTableSlot o2) {
		Integer totalWin1 = o1.getTotalWin();
		Integer totalWin2 = o2.getTotalWin();
		Integer score1 = o1.getScore();
		Integer score2 = o2.getScore();
		Double diff1 = o1.getDifference();
		Double diff2 = o2.getDifference();

		Double elo1 = o1.getElo();
		Double elo2 = o2.getElo();

		if (elo1 == elo2) {
			if (totalWin1 == totalWin2) {
				if (score1 == score2) {
					return (int) Math.ceil(diff2 - diff1);
				} else {
					return score2 - score1;
				}
			} else {
				return totalWin2 - totalWin1;
			}
		} else {
			return (int) Math.ceil(elo2 - elo1);
		}
	}

	public BoxDescription getDescription() {
		return description;
	}

	public void setDescription(BoxDescription description) {
		this.description = description;
	}

	public Integer getTotalWin() {
		return totalWin;
	}

	public void updateTotalWin() {
		this.totalWin ++;
	}

	public Integer getTotalLose() {
		return totalLose;
	}

	public void updateTotalLose() {
		this.totalLose ++;
	}

	public Double getElo() {
		return elo;
	}

	public void updateElo(Double update) {
		this.elo += update;
	}

}
