package doan2020.SportTournamentSupportSystem.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.ApiConverter;
import doan2020.SportTournamentSupportSystem.response.Response;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiAPI {

	@Autowired
	private ApiConverter converter;

	@GetMapping("")
	public ResponseEntity<Response> getOneApi(@RequestParam(value = "id") Long id) {
		System.out.println("ApiAPI - getOneApi");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		try {
			System.out.println("ApiAPI - cp1");
			result.put("api", null);
			config.put("global", 0);
			error.put("messageCode", 0);
			error.put("message", "");
			System.out.println("ApiAPI - cp2");
		} catch (Exception e) {
			System.out.println("ApiAPI - exception");
			result.put("api", null);
			config.put("global", 0);
			error.put("messageCode", 1);
			error.put("message", "");
		}

		System.out.println("ApiAPI - cp3");
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("ApiAPI - cp pass");
		return new ResponseEntity<Response>(response, httpStatus);

	}
	
//	@GetMapping("/getAll")
//	public ResponseEntity<Response> getAllApi(@RequestParam(value = "page") Integer page) {
//		System.out.println("ApiAPI - getAllApi");
//		HttpStatus httpStatus = null;
//		httpStatus = HttpStatus.OK;
//		Response response = new Response();
//		Map<String, Object> config = new HashMap<String, Object>();
//		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> error = new HashMap<String, Object>();
//
//		try {
//			System.out.println("ApiAPI - cp1");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 0);
//			error.put("message", "");
//			System.out.println("ApiAPI - cp2");
//		} catch (Exception e) {
//			System.out.println("ApiAPI - exception");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 1);
//			error.put("message", "");
//		}
//
//		System.out.println("ApiAPI - cp3");
//		response.setError(error);
//		response.setResult(result);
//		response.setConfig(config);
//		System.out.println("ApiAPI - cp pass");
//		return new ResponseEntity<Response>(response, httpStatus);
//
//	}
	
//	@PostMapping("")
//	public ResponseEntity<Response> createApi(@RequestBody ApiDTO apiDTO) {
//		System.out.println("ApiAPI - createApi");
//		HttpStatus httpStatus = null;
//		httpStatus = HttpStatus.OK;
//		Response response = new Response();
//		Map<String, Object> config = new HashMap<String, Object>();
//		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> error = new HashMap<String, Object>();
//
//		try {
//			System.out.println("ApiAPI - cp1");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 0);
//			error.put("message", "");
//			System.out.println("ApiAPI - cp2");
//		} catch (Exception e) {
//			System.out.println("ApiAPI - exception");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 1);
//			error.put("message", "");
//		}
//
//		System.out.println("ApiAPI - cp3");
//		response.setError(error);
//		response.setResult(result);
//		response.setConfig(config);
//		System.out.println("ApiAPI - cp pass");
//		return new ResponseEntity<Response>(response, httpStatus);
//
//	}
//	
//	@PutMapping("")
//	public ResponseEntity<Response> editApi(@RequestParam(value = "id") Long id) {
//		System.out.println("ApiAPI - editAPI");
//		HttpStatus httpStatus = null;
//		httpStatus = HttpStatus.OK;
//		Response response = new Response();
//		Map<String, Object> config = new HashMap<String, Object>();
//		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> error = new HashMap<String, Object>();
//
//		try {
//			System.out.println("ApiAPI - cp1");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 0);
//			error.put("message", "");
//			System.out.println("ApiAPI - cp2");
//		} catch (Exception e) {
//			System.out.println("ApiAPI - exception");
//			result.put("api", null);
//			config.put("global", 0);
//			error.put("messageCode", 1);
//			error.put("message", "");
//		}
//
//		System.out.println("ApiAPI - cp3");
//		response.setError(error);
//		response.setResult(result);
//		response.setConfig(config);
//		System.out.println("ApiAPI - cp pass");
//		return new ResponseEntity<Response>(response, httpStatus);
//
//	}
	
	@DeleteMapping("")
	public ResponseEntity<Response> deleteApi(@RequestParam(value = "id") Long id) {
		System.out.println("ApiAPI - deleteApi");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		try {
			System.out.println("ApiAPI - cp1");
			result.put("api", null);
			config.put("global", 0);
			error.put("messageCode", 0);
			error.put("message", "");
			System.out.println("ApiAPI - cp2");
		} catch (Exception e) {
			System.out.println("ApiAPI - exception");
			result.put("api", null);
			config.put("global", 0);
			error.put("messageCode", 1);
			error.put("message", "");
		}

		System.out.println("ApiAPI - cp3");
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("ApiAPI - cp pass");
		return new ResponseEntity<Response>(response, httpStatus);

	}

}
