package doan2020.SportTournamentSupportSystem.model.Schedule.DTO;

import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.model.Entity.Match;
import doan2020.SportTournamentSupportSystem.model.Struct.BTree;

public class SingleEliminationScheduleDTO extends FinalStageScheduleDTO{

	private static final long serialVersionUID = 1L;
	
	protected BTree<Match> bracket;
	protected boolean hasMatch34;
	protected Match match34;
	
	public SingleEliminationScheduleDTO() {
		super();
	}
	
	public BTree<Match> getBracket() {
		return bracket;
	}
	public void setBracket(BTree<Match> bracket) {
		this.bracket = bracket;
	}
	public boolean isHasMatch34() {
		return hasMatch34;
	}
	public void setHasMatch34(boolean hasMatch34) {
		this.hasMatch34 = hasMatch34;
	}
	public Match getMatch34() {
		return match34;
	}
	public void setMatch34(Match match34) {
		this.match34 = match34;
	}
	@Override
	public void setRoundsNaming() {
		this.roundsNaming = new ArrayList<>();
		for (int round = 1; round <= this.totalRound; round ++) {
			String name = Const.ROUND + round;
			if (round == this.totalRound) {
				name = Const.FINAL;
			}
			if (round == this.totalRound - 1) {
				name = Const.SEMIFINAL;
			}
			if (round == this.totalRound - 2) {
				name = Const.QUARTERFINAL;
			}
			
			this.roundsNaming.add(name);
		}
	}
	
	
}
