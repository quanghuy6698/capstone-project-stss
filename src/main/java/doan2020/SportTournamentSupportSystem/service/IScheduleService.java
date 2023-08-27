
package doan2020.SportTournamentSupportSystem.service;

import java.util.HashMap;

import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.ScheduleDTO;

public interface IScheduleService {

	public ScheduleDTO getSchedule(CompetitionEntity competition);

	public ScheduleDTO createSchedule(CompetitionEntity competition);

	public void createMatchesInDatabase(CompetitionEntity competition);

	public ScheduleDTO finishMatch(MatchEntity match);

	public ScheduleDTO changeMatchInfo(CompetitionEntity competition, Integer nodeId, Integer degree, Integer location,
			Integer tableId, HashMap<String, Object> newInfo);

}