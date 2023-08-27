package doan2020.SportTournamentSupportSystem.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.GroupStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.model.Schedule.DTO.ScheduleDTO;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.ICompetitionService;
import doan2020.SportTournamentSupportSystem.service.IScheduleService;

@RestController
@CrossOrigin
@RequestMapping("/schedule")
public class ScheduleAPI {

	@Autowired
	private ICompetitionService competitionService;

	@Autowired
	private IScheduleService scheduleService;

	/*
	 * Get schedule for a competition
	 */

	@GetMapping()
	public ResponseEntity<Response> getScheduleByCompetitionId(
			@RequestParam(value = "competitionId", required = false) Long competitionId) {
		System.out.println("ScheduleAPI: getScheduleByCompetitionId: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		try {

			CompetitionEntity thisCompetition = competitionService.findOneById(competitionId);
			if (thisCompetition == null) {
				result.put("Schedule", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Competition not found");
			} else {
				System.out.println("ScheduleAPI: getScheduleByCompetitionId: -> ScheduleService: getSchedule: ");
				ScheduleDTO schedule = scheduleService.getSchedule(thisCompetition);
				System.out.println("ScheduleService: getSchedule: -> ScheduleAPI: getScheduleByCompetitionId:");
				if (schedule == null) {
					result.put("Schedule", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not enough team");
				} else {
					result.put("Schedule", schedule);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Success");
				}
			}
			System.out.println("ScheduleAPI: getScheduleByCompetitionId: no exception");
		} catch (Exception e) {
			System.out.println("ScheduleAPI: getScheduleByCompetitionId: has exception");
			result.put("Schedule", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ScheduleAPI: getScheduleByCompetitionId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping()
	public ResponseEntity<Response> schedulingByCompetitionId(
			@RequestParam(value = "competitionId", required = false) Long competitionId) {
		System.out.println("ScheduleAPI: createFinalStageScheduleByCompetitionId: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		ScheduleDTO schedule = new ScheduleDTO();

		boolean err = false;

		try {
			CompetitionEntity thisCompetition = competitionService.findOneById(competitionId);

			if (thisCompetition == null) {
				result.put("Schedule", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Competition not found");
			} else {
				TournamentEntity tour = thisCompetition.getTournament();
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {

					boolean hasGroupStage = thisCompetition.isHasGroupStage();
					FinalStageSettingEntity fsse = thisCompetition.getFinalStageSetting();
					GroupStageSettingEntity gsse = thisCompetition.getGroupStageSetting();

					if (fsse == null) {
						error.put("Message", "Missing final stage setting");
						err = true;
					}

					if (hasGroupStage && gsse == null) {
						error.put("Message", "Missing group stage setting");
						err = true;
					}

					if (!err) {
						schedule = scheduleService.createSchedule(thisCompetition);

						result.put("Schedule", schedule);
						config.put("Global", 0);
						error.put("MessageCode", 0);
						error.put("Message", "Success");

					} else {
						result.put("Schedule", null);
						config.put("Global", 0);
						error.put("MessageCode", 1);
					}
				} else {
					String message = "Unknown error";
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
						message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					result.put("Competition", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}
			}

			System.out.println("ScheduleAPI: createFinalStageScheduleByCompetitionId: no exception");
		} catch (Exception e) {
			System.out.println("ScheduleAPI: createFinalStageScheduleByCompetitionId: has exception");
			System.out.println(e);
			result.put("Schedule", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ScheduleAPI: createFinalStageScheduleByCompetitionId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/changeMatchInfo")
	public ResponseEntity<Response> changeMatchInfo(
			@RequestParam(value = "competitionId", required = false) Long competitionId,
			@RequestParam(value = "nodeId") Integer nodeId,
			@RequestParam(value = "degree") Integer degree,
			@RequestParam(value = "location") Integer location, // -1-RR, 0-SE, 1-Win branch, 2-Lose branch, 3-match34,
																// 4-summary final, 5-option
			@RequestParam(value = "tableId") Integer tableId, @RequestBody HashMap<String, Object> newInfo) {
		System.out.println("ScheduleAPI: changeMatchInfo: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		try {

			CompetitionEntity thisCompetition = competitionService.findOneById(competitionId);
			if (thisCompetition == null) {
				result.put("Schedule", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Competition not found");
			} else {

				ScheduleDTO schedule = scheduleService.changeMatchInfo(thisCompetition, nodeId, degree, location,
						tableId, newInfo);

				result.put("Schedule", schedule);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Success");

			}
			System.out.println("ScheduleAPI: changeMatchInfo: no exception");
		} catch (Exception e) {
			System.out.println("ScheduleAPI: changeMatchInfo: has exception");
			result.put("Schedule", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ScheduleAPI: changeMatchInfo: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
}
