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

import doan2020.SportTournamentSupportSystem.converter.ResultConverter;
import doan2020.SportTournamentSupportSystem.dto.ResultDTO;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IResultService;

@RestController
@CrossOrigin
@RequestMapping("/result")
public class ResultAPI {

	@Autowired
	private ResultConverter converter;
	
	@Autowired
	private IResultService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getResult(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("ResultAPI: getResult: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ResultEntity resultEntity = new ResultEntity();
		ResultDTO resultDTO = new ResultDTO();
		try {
			if (id == null) { // id null
				result.put("Result", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				resultEntity = service.findOneById(id);
				
				if (resultEntity == null) { // not found
					result.put("Result", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					resultDTO = converter.toDTO(resultEntity);
					
					result.put("Result", resultDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("ResultAPI: getResult: no exception");
		} catch (Exception e) {
			System.out.println("ResultAPI: getResult: has exception");
			result.put("Result", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ResultAPI: getResult: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Result
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createResult(@RequestBody ResultDTO newResult) {
		System.out.println("ResultAPI: createResult: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ResultEntity resultEntity = new ResultEntity();
		
		try {
			resultEntity = converter.toEntity(newResult);
			
			resultEntity = service.create(resultEntity);
			
			ResultDTO dto = converter.toDTO(resultEntity);

			result.put("Result", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Result create successfuly");
			System.out.println("ResultAPI: createResult: no exception");
		} catch (Exception e) {
			System.out.println("ResultAPI: createResult: has exception");
			result.put("Result", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ResultAPI: createResult: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Result
	 * 
	 */
	@PutMapping
	@CrossOrigin
	public ResponseEntity<Response> editResult(
			@RequestBody ResultDTO res,
			@RequestParam Long id) {
		System.out.println("ResultAPI: editResult: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ResultEntity resultEntity = new ResultEntity();
		
		try {
			resultEntity = converter.toEntity(res);
			
			resultEntity = service.update(id, resultEntity);
			
			ResultDTO dto = converter.toDTO(resultEntity);

			result.put("Result", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Result update successfuly");
			System.out.println("ResultAPI: editResult: no exception");
		} catch (Exception e) {
			System.out.println("ResultAPI: editResult: has exception");
			result.put("Result", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ResultAPI: editResult: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
