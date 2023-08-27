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

import doan2020.SportTournamentSupportSystem.converter.FormatConverter;
import doan2020.SportTournamentSupportSystem.dto.FormatDTO;
import doan2020.SportTournamentSupportSystem.entity.FormatEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IFormatService;

@RestController
@CrossOrigin
@RequestMapping("/format")
public class FormatAPI {

	@Autowired
	private FormatConverter converter;
	
	@Autowired
	private IFormatService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getFormat(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("FormatAPI: getFormat: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		FormatEntity formatEntity = new FormatEntity();
		FormatDTO formatDTO = new FormatDTO();
		try {
			if (id == null) { // id null
				result.put("Format", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				formatEntity = service.findOneById(id);
				
				if (formatEntity == null) { // not found
					result.put("Format", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					formatDTO = converter.toDTO(formatEntity);
					
					result.put("Format", formatDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("FormatAPI: getFormat: no exception");
		} catch (Exception e) {
			System.out.println("FormatAPI: getFormat: has exception");
			result.put("Format", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("FormatAPI: getFormat: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	
	
}
