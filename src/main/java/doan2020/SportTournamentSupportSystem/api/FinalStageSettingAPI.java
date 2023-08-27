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
import doan2020.SportTournamentSupportSystem.converter.FinalStageSettingConverter;
import doan2020.SportTournamentSupportSystem.dto.FinalStageSettingDTO;
import doan2020.SportTournamentSupportSystem.dto.FinalStageSettingDTO;
import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IFinalStageSettingService;

@RestController
@CrossOrigin
@RequestMapping("/finalStageSetting")
public class FinalStageSettingAPI {

	@Autowired
	private FinalStageSettingConverter converter;

	@Autowired
	private IFinalStageSettingService service;

	@GetMapping("/getByCompetitionId")
	public ResponseEntity<Response> getFinalStageSetting(
			@RequestParam(value = "competitionId", required = false) Long competitionId) {
		System.out.println("FinalStageSettingAPI: getFinalStageSetting: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		FinalStageSettingEntity finalStageSettingEntity = new FinalStageSettingEntity();
		FinalStageSettingDTO finalStageSettingDTO = new FinalStageSettingDTO();
		try {
			if (competitionId == null) { // competitionId null
				result.put("FinalStageSetting", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param competitionId");
			} else { // competitionId not null

				finalStageSettingEntity = service.findByCompetitionId(competitionId);

				if (finalStageSettingEntity == null) { // not found
					result.put("FinalStageSetting", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found

					finalStageSettingDTO = converter.toDTO(finalStageSettingEntity);

					result.put("FinalStageSetting", finalStageSettingDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("FinalStageSettingAPI: getFinalStageSetting: no exception");
		} catch (Exception e) {
			System.out.println("FinalStageSettingAPI: getFinalStageSetting: has exception");
			result.put("FinalStageSetting", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("FinalStageSettingAPI: getFinalStageSetting: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping
	public ResponseEntity<Response> createFinalStageSetting(@RequestBody FinalStageSettingDTO newFinalStageSetting) {
		System.out.println("FinalStageSettingAPI: createFinalStageSetting: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		FinalStageSettingEntity finalStageSettingEntity = new FinalStageSettingEntity();

		try {
			finalStageSettingEntity = converter.toEntity(newFinalStageSetting);
			TournamentEntity tour = finalStageSettingEntity.getCompetition().getTournament();
			if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {

				finalStageSettingEntity = service.create(finalStageSettingEntity);

				FinalStageSettingDTO dto = converter.toDTO(finalStageSettingEntity);

				result.put("FinalStageSetting", dto);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "FinalStageSetting create successfuly");
				System.out.println("FinalStageSettingAPI: createFinalStageSetting: no exception");
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
		} catch (Exception e) {
			System.out.println("FinalStageSettingAPI: createFinalStageSetting: has exception");
			result.put("FinalStageSetting", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("FinalStageSettingAPI: createFinalStageSetting: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping
	public ResponseEntity<Response> editFinalStageSetting(@RequestBody FinalStageSettingDTO finalStageSetting,
			@RequestParam Long id) {
		System.out.println("FinalStageSettingAPI: editFinalStageSetting: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		FinalStageSettingEntity finalStageSettingEntity = new FinalStageSettingEntity();

		try {

			finalStageSettingEntity = converter.toEntity(finalStageSetting);
			TournamentEntity tour = finalStageSettingEntity.getCompetition().getTournament();
			if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {

				finalStageSettingEntity = service.update(id, finalStageSettingEntity);

				FinalStageSettingDTO dto = converter.toDTO(finalStageSettingEntity);
				System.out.println("FinalStageSettingAPI: editFinalStageSetting: dto: " + dto.isHasHomeMatch());

				result.put("FinalStageSetting", dto);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "FinalStageSetting update successfuly");
				System.out.println("FinalStageSettingAPI: editFinalStageSetting: no exception");
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
		} catch (Exception e) {
			System.out.println("FinalStageSettingAPI: editFinalStageSetting: has exception");
			result.put("FinalStageSetting", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("FinalStageSettingAPI: editFinalStageSetting: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
