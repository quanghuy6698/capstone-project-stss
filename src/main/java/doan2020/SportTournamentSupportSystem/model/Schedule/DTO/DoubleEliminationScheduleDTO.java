package doan2020.SportTournamentSupportSystem.model.Schedule.DTO;

import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Struct.BTree;
import doan2020.SportTournamentSupportSystem.model.Struct.DoubleBTree;

public class DoubleEliminationScheduleDTO extends FinalStageScheduleDTO{

	private static final long serialVersionUID = 1L;
	
	protected int totalWinRound;
	protected int totalLoseRound;
	protected BTree<Match> winBranch;
	protected DoubleBTree<Match> loseBranch;
	protected Match summaryFinal;
	protected Match optionFinal;
	
	protected ArrayList<String> winRoundsNaming;
	protected ArrayList<String> loseRoundsNaming;
	protected ArrayList<String> finalRoundsNaming;
	
	public BTree<Match> getWinBranch() {
		return winBranch;
	}
	public void setWinBranch(BTree<Match> winBranch) {
		this.winBranch = winBranch;
	}
	public DoubleBTree<Match> getLoseBranch() {
		return loseBranch;
	}
	public void setLoseBranch(DoubleBTree<Match> loseBranch) {
		this.loseBranch = loseBranch;
	}
	public Match getSummaryFinal() {
		return summaryFinal;
	}
	public void setSummaryFinal(Match summaryFinal) {
		this.summaryFinal = summaryFinal;
	}
	public Match getOptionFinal() {
		return optionFinal;
	}
	public void setOptionFinal(Match optionFinal) {
		this.optionFinal = optionFinal;
	}
	public int getTotalWinRound() {
		return totalWinRound;
	}
	public void setTotalWinRound(int totalWinRound) {
		this.totalWinRound = totalWinRound;
	}
	public int getTotalLoseRound() {
		return totalLoseRound;
	}
	public void setTotalLoseRound(int totalLoseRound) {
		this.totalLoseRound = totalLoseRound;
	}
	
	@Override
	public void setRoundsNaming() {
		this.roundsNaming = new ArrayList<>();
		setWinRoundsNaming();
		setLoseRoundsNaming();
		setFinalRoundsNaming();
	}
	
	public ArrayList<String> getWinRoundsNaming() {
		if (winRoundsNaming == null) {
			setWinRoundsNaming();
		}
		return winRoundsNaming;
	}
	
	public ArrayList<String> getLoseRoundsNaming() {
		if (loseRoundsNaming == null) {
			setLoseRoundsNaming();
		}
		return loseRoundsNaming;
	}
	
	public ArrayList<String> getFinalRoundsNaming() {
		if (finalRoundsNaming == null) {
			setFinalRoundsNaming();
		}
		return finalRoundsNaming;
	}
	
	protected void setWinRoundsNaming() {
		this.winRoundsNaming = new ArrayList<String>();
		String postfix = Const.WIN_BRANCH;
		for (int round = 1; round <= this.totalWinRound; round ++) {
			String name = Const.ROUND + round;
			if (round == this.totalWinRound) {
				name = Const.FINAL;
			}
			if (round == this.totalWinRound - 1) {
				name = Const.SEMIFINAL;
			}
			if (round == this.totalWinRound - 2) {
				name = Const.QUARTERFINAL;
			}
			
			this.winRoundsNaming.add(name + postfix);
		}
	}
	
	protected void setLoseRoundsNaming() {
		this.loseRoundsNaming = new ArrayList<String>();
		String postfix = Const.LOSE_BRANCH;
		for (int round = 1; round <= this.totalLoseRound; round ++) {
			String name = Const.ROUND + round;
			if (round == this.totalLoseRound) {
				name = Const.FINAL;
			}
			this.loseRoundsNaming.add(name + postfix);
		}
		
	}
	
	protected void setFinalRoundsNaming() {
		this.finalRoundsNaming = new ArrayList<String>();
		String name = Const.SUMMARY_FINAL_BRANCH;
		this.finalRoundsNaming.add(name);
		this.finalRoundsNaming.add("");
	}
	
	
	
	
}
