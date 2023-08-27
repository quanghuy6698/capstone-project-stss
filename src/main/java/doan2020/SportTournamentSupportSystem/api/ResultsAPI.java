package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.ResultConverter;
import doan2020.SportTournamentSupportSystem.dto.ResultDTO;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IResultService;

@RestController
@CrossOrigin
@RequestMapping("/results")
public class ResultsAPI {

	@Autowired
	private ResultConverter converter;

	@Autowired
	private IResultService service;

	@GetMapping("/getByMatchId")
	public ResponseEntity<Response> getByMatchId(@RequestParam(value = "matchId") Long matchId) {
		System.out.println("ResultsAPI: getByMatchId: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		Collection<ResultEntity> resultEntites = new ArrayList<ResultEntity>();
		List<ResultDTO> resultDTOs = new ArrayList<ResultDTO>();
		try {

			if (matchId == null) {// matchId null
				result.put("Results", resultDTOs);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param matchId");
			} else {// matchId not null

				resultEntites = service.findByMatchId(matchId);
				if (resultEntites == null) {
					result.put("Results", resultDTOs);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				} else {

					for (ResultEntity entity : resultEntites) {
						ResultDTO dto = converter.toDTO(entity);
						resultDTOs.add(dto);
					}

					result.put("Results", resultDTOs);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("ResultsAPI: getByMatchId: no exception");
		} catch (Exception e) {
			System.out.println("ResultsAPI: getByMatchId: has exception");
			result.put("Result", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ResultsAPI: getByMatchId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/updateByMatchId")
	public ResponseEntity<Response> updateByMatchId(@RequestParam(value = "matchId") Long matchId,
			@RequestBody HashMap<String, HashMap<String, ArrayList<ResultDTO>>> data) {
		System.out.println("ResultsAPI: updateByMatchId: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ArrayList<ResultEntity> resultEntites = new ArrayList<ResultEntity>();
		ArrayList<ResultDTO> resultDTOs = new ArrayList<ResultDTO>();
		try {

			if (matchId == null) {// matchId null
				result.put("Results", resultDTOs);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param matchId");
			} else {// matchId not null
				if (data == null) {
					result.put("Results", resultDTOs);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Invalid data");
				} else {

					ArrayList<ResultEntity> entities = new ArrayList<>();
					System.out.println("CP1");

					HashMap<String, ArrayList<ResultDTO>> results = (HashMap<String, ArrayList<ResultDTO>>)data.get("results");
					ArrayList<ResultDTO> dtos = results.get("data");
					System.out.println("CP2");
					System.out.println(dtos);
					for (ResultDTO resultDTO: dtos) {
						System.out.println("CP2-1");
//						ResultDTO resultDTO = (ResultDTO) map;
//						System.out.println("CP2-2");
						System.out.println(resultDTO.toString());
//						resultDTO.setMatchId((long)map.get("matchId"));
//						resultDTO.setSetNo((int)map.get("setNo"));
//						resultDTO.setTeam1Score((int)map.get("team1Score"));
//						resultDTO.setTeam2Score((int)map.get("team2Score"));
						ResultEntity entity = converter.toEntity(resultDTO);
						System.out.println("CP2-2");
						entities.add(entity);
					}
					System.out.println("CP3");
					resultEntites.addAll(service.findByMatchId(matchId));

					Collections.sort(entities, new ResultEntity());
					Collections.sort(resultEntites, new ResultEntity());
					System.out.println("CP4");
					if (entities.size() == resultEntites.size()) {
						for (int i=0; i< entities.size(); i++) {
							entities.set(i, service.update(resultEntites.get(i).getId(), entities.get(i)));
						}
					}
					System.out.println("CP5");
					if (entities.size() < resultEntites.size()) {
						for (int i=0; i< entities.size(); i++) {
							entities.set(i, service.update(resultEntites.get(i).getId(), entities.get(i)));
						}
						for (int i=entities.size(); i< resultEntites.size(); i++) {
							service.delete(resultEntites.get(i).getId());
						}
					}
					System.out.println("CP6");
					if (entities.size() > resultEntites.size()) {
						for (int i=0; i< entities.size(); i++) {
							if (i < resultEntites.size())
								entities.set(i, service.update(resultEntites.get(i).getId(), entities.get(i)));
							else
								entities.set(i, service.create(entities.get(i)));
						}
						
					}
					System.out.println("CP7");
					for (ResultEntity entity : entities) {
						ResultDTO dto = converter.toDTO(entity);
						resultDTOs.add(dto);
					}
					System.out.println("CP8");
					result.put("Results", resultDTOs);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");

				}
			}
			System.out.println("ResultsAPI: updateByMatchId: no exception");
		} catch (

		Exception e) {
			System.out.println("ResultsAPI: updateByMatchId: has exception");
			result.put("Result", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ResultsAPI: updateByMatchId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
}
