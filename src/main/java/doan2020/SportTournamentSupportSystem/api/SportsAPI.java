package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.SportConverter;
import doan2020.SportTournamentSupportSystem.dto.SportDTO;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.SportEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.ICompetitionService;
import doan2020.SportTournamentSupportSystem.service.ISportService;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;

@RestController
@CrossOrigin
@RequestMapping("/sports")
public class SportsAPI {

	@Autowired
	private SportConverter converter;

	@Autowired
	private ISportService service;

	@Autowired
	private ICompetitionService competitionService;

	@Autowired
	private ITournamentService tournamentService;

	@GetMapping("")
	public ResponseEntity<Response> getAllSport() {
		System.out.println("SportsAPI: getAllSport: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		Collection<SportEntity> sportEntites = new ArrayList<SportEntity>();
		SportDTO dto = new SportDTO();
		List<SportDTO> sportDTOs = new ArrayList<SportDTO>();
		try {

			sportEntites = service.findAll();

			if (sportEntites.isEmpty()) { // not found
				result.put("Sports", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Not found");
			} else { // found

				for (SportEntity entity : sportEntites) {
					dto = converter.toDTO(entity);
					sportDTOs.add(dto);
				}

				result.put("Sports", sportDTOs);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
			}
			System.out.println("SportsAPI: getAllSport: no exception");
		} catch (Exception e) {
			System.out.println("SportsAPI: getAllSport: has exception");
			result.put("Sport", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("SportsAPI: getAllSport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/getByTournamentId")
	public ResponseEntity<Response> getByTournamentId(
			@RequestParam(value = "tournamentId", required = false) Long tournamentId) {

		System.out.println("SportsAPI: getByTournamentId: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		Collection<SportDTO> dtos = new HashSet<SportDTO>();

		try {
			if (tournamentId == null) { // tournamentId null
				result.put("Sports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param tournamentId");
			} else { // tournamentId not null

				TournamentEntity thisTournament = tournamentService.findOneById(tournamentId);

				if (thisTournament == null) {
					result.put("Sports", null);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Tournament not found");
				} else {
					Collection<CompetitionEntity> competitions = thisTournament.getCompetitions();
					
					System.out.println("SportsAPI: getByTournamentId: competitions.size: " + competitions.size());
					
					HashSet<Long> sportsId = new HashSet<>();

					for (CompetitionEntity comp : competitions) {

						sportsId.add(comp.getSport().getId());
					}

					for (Long sportId : sportsId) {
						SportDTO dto = converter.toDTO(service.findOneById(sportId));
						dtos.add(dto);
					}
					
					System.out.println("SportsAPI: getByTournamentId: sportsId.size: " + sportsId.size());

					result.put("Sports", dtos);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}

			System.out.println("SportsAPI: getByTournamentId: no exception");
		} catch (Exception e) {
			System.out.println("SportsAPI: getByTournamentId: has exception");
			result.put("Sports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("SportsAPI: getByTournamentId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
