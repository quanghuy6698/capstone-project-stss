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
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.FormatConverter;
import doan2020.SportTournamentSupportSystem.dto.FormatDTO;
import doan2020.SportTournamentSupportSystem.entity.FormatEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IFormatService;

@RestController
@CrossOrigin
@RequestMapping("/formats")
public class FormatsAPI {

	@Autowired
	private FormatConverter converter;
	
	@Autowired
	private IFormatService service;
	
	@GetMapping("")
	public ResponseEntity<Response> getAllFormat() {
		System.out.println("FormatsAPI: getAllFormat: start");

		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		HttpStatus httpStatus = HttpStatus.OK;

		Collection<FormatEntity> formats = new ArrayList<>();
		List<FormatDTO> dtos = new ArrayList<>();

		try {
			formats = service.findAll();

			for (FormatEntity entity : formats) {
				FormatDTO dto = converter.toDTO(entity);
				dtos.add(dto);
			}

			result.put("CompetitionSettings", dtos);
			error.put("MessageCode", 0);
			error.put("Message", "Get page successfully");

			System.out.println("FormatsAPI: getAllFormat: no exception");
		} catch (Exception e) {
			System.out.println("FormatsAPI: getAllFormat: has exception");
			result.put("Users", dtos);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("FormatsAPI: getAllFormat: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
}
