package doan2020.SportTournamentSupportSystem.model.Schedule.DTO;

import java.util.ArrayList;

public class UnknownScheduleDTO extends FinalStageScheduleDTO{

	private static final long serialVersionUID = 1L;

	@Override
	public void setRoundsNaming() {
		this.roundsNaming = new ArrayList<>();		
	}
}
