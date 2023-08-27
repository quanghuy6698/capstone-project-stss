package doan2020.SportTournamentSupportSystem.model.Schedule.DTO;

import java.util.ArrayList;

import doan2020.SportTournamentSupportSystem.config.Const;

public class RoundRobinScheduleDTO extends FinalStageScheduleDTO {

	private static final long serialVersionUID = 1L;

	protected boolean hasHomeMatch;

	public boolean isHasHomeMatch() {
		return hasHomeMatch;
	}

	public void setHasHomeMatch(boolean hasHomeMatch) {
		this.hasHomeMatch = hasHomeMatch;
	}

	@Override
	public void setRoundsNaming() {
		this.roundsNaming = new ArrayList<>();
		String awayPostfix = Const.ROUND_ROBIN_AWAY;
		String homePostfix = Const.ROUND_ROBIN_HOME;
		if (!hasHomeMatch) {
			awayPostfix = "";
			homePostfix = "";
		}
		for (int round = 1; round <= totalRound; round++) {
			String postfix = (round <= totalRound / 2) ? homePostfix:awayPostfix;
			int roundNo =  (round > totalRound / 2 && hasHomeMatch) ? round - (totalRound / 2) : round;
			String name = Const.ROUND + roundNo + postfix;
			this.roundsNaming.add(name);
		}
	}

}
