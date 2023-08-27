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

import doan2020.SportTournamentSupportSystem.converter.ReportConverter;
import doan2020.SportTournamentSupportSystem.dto.ReportDTO;
import doan2020.SportTournamentSupportSystem.entity.ReportEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IReportService;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportAPI {

	@Autowired
	private ReportConverter converter;
	
	@Autowired
	private IReportService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getReport(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("ReportAPI: getReport: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ReportEntity reportEntity = new ReportEntity();
		ReportDTO reportDTO = new ReportDTO();
		try {
			if (id == null) { // id null
				result.put("Report", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				reportEntity = service.findOneById(id);
				
				if (reportEntity == null) { // not found
					result.put("Report", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					reportDTO = converter.toDTO(reportEntity);
					
					result.put("Report", reportDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("ReportAPI: getReport: no exception");
		} catch (Exception e) {
			System.out.println("ReportAPI: getReport: has exception");
			result.put("Report", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportAPI: getReport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Report
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createReport(@RequestBody ReportDTO newReport) {
		System.out.println("ReportAPI: createReport: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ReportEntity reportEntity = new ReportEntity();
		
		try {
			reportEntity = converter.toEntity(newReport);
			
			reportEntity = service.create(reportEntity);
			
			ReportDTO dto = converter.toDTO(reportEntity);

			result.put("Report", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Report create successfuly");
			System.out.println("ReportAPI: createReport: no exception");
		} catch (Exception e) {
			System.out.println("ReportAPI: createReport: has exception");
			result.put("Report", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportAPI: createReport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Report
	 * 
	 */
	@PutMapping
	@CrossOrigin
	public ResponseEntity<Response> editReport(
			@RequestBody ReportDTO report,
			@RequestParam Long id) {
		System.out.println("ReportAPI: editReport: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		ReportEntity reportEntity = new ReportEntity();
		
		try {
			reportEntity = converter.toEntity(report);
			
			reportEntity = service.update(id, reportEntity);
			
			ReportDTO dto = converter.toDTO(reportEntity);

			result.put("Report", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Report update successfuly");
			System.out.println("ReportAPI: editReport: no exception");
		} catch (Exception e) {
			System.out.println("ReportAPI: editReport: has exception");
			result.put("Report", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportAPI: editReport: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
