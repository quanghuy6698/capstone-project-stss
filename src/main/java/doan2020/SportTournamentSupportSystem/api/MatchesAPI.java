package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

import doan2020.SportTournamentSupportSystem.converter.MatchConverter;
import doan2020.SportTournamentSupportSystem.dto.MatchDTO;
import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IMatchService;

@RestController
@CrossOrigin
@RequestMapping("/matches")
public class MatchesAPI {
	@Autowired
	private MatchConverter converter;

	@Autowired
	private IMatchService service;

	@GetMapping("/getByCompetitionId")
	public ResponseEntity<Response> getByCompetitionId(@RequestParam(value = "competitionId") Long competitionId) {
		System.out.println("MatchsAPI: getByCompetitionId: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		Collection<MatchEntity> matchEntites = new ArrayList<MatchEntity>();
		List<MatchDTO> matchDTOs = new ArrayList<MatchDTO>();
		HashMap<Long, HashMap<String, Integer>> scores = new HashMap<>();
		try {

			if (competitionId == null) {// userId null
				result.put("Matchs", matchDTOs);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param competitionId");
			} else {// userId not null

				matchEntites = service.findByCompetitionId(competitionId);
				
				System.out.println("totalMatch: " + matchEntites.size());

				if (matchEntites.isEmpty()) { // not found
					result.put("Matchs", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found

					for (MatchEntity entity : matchEntites) {
						MatchDTO dto = converter.toDTO(entity);
						matchDTOs.add(dto);

						ArrayList<ResultEntity> results = new ArrayList<>();
						results.addAll(entity.getResults());

						Integer team1Score = 0;
						Integer team2Score = 0;

						for (ResultEntity r : results) {
							Double tmpDiff1 = new Double(r.getTeam1Score() - r.getTeam2Score());
							Double tmpDiff2 = 0 - tmpDiff1;
							
							if (tmpDiff1 > 0)
								team1Score++;

							if (tmpDiff2 > 0)
								team2Score++;
						}
						
						HashMap<String, Integer> score = new HashMap<>();
						score.put("team1", team1Score);
						score.put("team2", team2Score);
						scores.put(entity.getId(), score);
					}

					result.put("Matchs", matchDTOs);
					result.put("Scores", scores);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("MatchsAPI: getByCompetitionId: no exception");
		} catch (Exception e) {
			System.out.println("MatchsAPI: getByCompetitionId: has exception");
			result.put("Match", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("MatchsAPI: getByCompetitionId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("")
	public ResponseEntity<Response> getAllMatch() {
		System.out.println("MatchsAPI: getAllMatch: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		Collection<MatchEntity> matchEntites = new ArrayList<MatchEntity>();
		MatchDTO dto = new MatchDTO();
		List<MatchDTO> matchDTOs = new ArrayList<MatchDTO>();
		try {

			matchEntites = service.findAll();

			if (matchEntites.isEmpty()) { // not found
				result.put("Matchs", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Not found");
			} else { // found

				for (MatchEntity entity : matchEntites) {
					dto = converter.toDTO(entity);
					matchDTOs.add(dto);
				}

				result.put("Matchs", matchDTOs);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
			}
			System.out.println("MatchsAPI: getAllMatch: no exception");
		} catch (Exception e) {
			System.out.println("MatchsAPI: getAllMatch: has exception");
			result.put("Match", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("MatchsAPI: getAllMatch: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
}
