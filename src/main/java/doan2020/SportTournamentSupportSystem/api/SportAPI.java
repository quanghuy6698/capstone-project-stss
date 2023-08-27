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

import doan2020.SportTournamentSupportSystem.converter.SportConverter;
import doan2020.SportTournamentSupportSystem.dto.SportDTO;
import doan2020.SportTournamentSupportSystem.entity.SportEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.ISportService;

@RestController
@CrossOrigin
@RequestMapping("/sport")
public class SportAPI {
	
	@Autowired
	private SportConverter converter;
	
	@Autowired
	private ISportService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getSport(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("SportAPI: getSport: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		SportEntity sportEntity = new SportEntity();
		SportDTO sportDTO = new SportDTO();
		try {
			if (id == null) { // id null
				result.put("Sport", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				sportEntity = service.findOneById(id);
				
				if (sportEntity == null) { // not found
					result.put("Sport", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					sportDTO = converter.toDTO(sportEntity);
					
					result.put("Sport", sportDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("SportAPI: getSport: no exception");
		} catch (Exception e) {
			System.out.println("SportAPI: getSport: has exception");
			result.put("Sport", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("SportAPI: getSport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Sport
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createSport(@RequestBody SportDTO newSport) {
		System.out.println("SportAPI: createSport: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		SportEntity sportEntity = new SportEntity();
		
		try {
			sportEntity = converter.toEntity(newSport);
			
			sportEntity = service.create(sportEntity);
			
			SportDTO dto = converter.toDTO(sportEntity);

			result.put("Sport", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Sport create successfuly");
			System.out.println("SportAPI: createSport: no exception");
		} catch (Exception e) {
			System.out.println("SportAPI: createSport: has exception");
			result.put("Sport", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("SportAPI: createSport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Sport
	 * 
	 */
	@PutMapping
	@CrossOrigin
	public ResponseEntity<Response> editSport(
			@RequestBody SportDTO sport,
			@RequestParam Long id) {
		System.out.println("SportAPI: editSport: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		SportEntity sportEntity = new SportEntity();
		
		try {
			sportEntity = converter.toEntity(sport);
			
			sportEntity = service.update(id, sportEntity);
			
			SportDTO dto = converter.toDTO(sportEntity);

			result.put("Sport", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Sport update successfuly");
			System.out.println("SportAPI: editSport: no exception");
		} catch (Exception e) {
			System.out.println("SportAPI: editSport: has exception");
			result.put("Sport", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("SportAPI: editSport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
