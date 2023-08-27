package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.converter.ReportConverter;
import doan2020.SportTournamentSupportSystem.dto.ReportDTO;
import doan2020.SportTournamentSupportSystem.entity.ReportEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IReportService;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;
import doan2020.SportTournamentSupportSystem.service.IUserService;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class ReportsAPI {

	@Autowired
	private ReportConverter converter;
	
	@Autowired
	private IReportService service;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITournamentService tournamentService;
	
	@GetMapping()
	public ResponseEntity<Response> getAll(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("ReportsAPI: getBySenderId: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		List<ReportDTO> dtos = new ArrayList<ReportDTO>();
		List<ReportEntity> entities = new ArrayList<ReportEntity>();
		
		if (limit == null || limit <= 0)
			limit = 10;
		
		if (page == null || page <= 0)
			page = 1;
		
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<ReportEntity>) service.findAll(pageable);
				int totalPage = 0;
		
				int totalEntity = service.countReports();
				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;
				
				for (ReportEntity entity: entities) {
					ReportDTO dto = converter.toDTO(entity);
					dtos.add(dto);
				}
				
				result.put("TotalPage", totalPage);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("ReportsAPI: getBySenderId: no exception");
			} catch (Exception e) {
				System.out.println("ReportsAPI: getBySenderId: has exception");
				result.put("TotalPage", null);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}
			
		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportsAPI: getBySenderId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
	
	@GetMapping("/getBySenderId")
	public ResponseEntity<Response> getBySenderId(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "senderId") Long senderId) {
		System.out.println("ReportsAPI: getBySenderId: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		List<ReportDTO> dtos = new ArrayList<ReportDTO>();
		List<ReportEntity> entities = new ArrayList<ReportEntity>();
		
		if (limit == null || limit <= 0)
			limit = 10;
		
		if (page == null || page <= 0)
			page = 1;
		
		if (senderId == null) {// senderId null
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param senderId");
		} else {//senderId not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<ReportEntity>) service.findBySenderId(pageable, senderId);
				int totalPage = 0;
				UserEntity creator = userService.findOneById(senderId);
				int totalEntity = creator.getReports().size();
				System.out.println(totalEntity);
				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;
				System.out.println(totalPage);
				for (ReportEntity entity: entities) {
					ReportDTO dto = converter.toDTO(entity);
					dtos.add(dto);
				}
				
				result.put("TotalPage", totalPage);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("ReportsAPI: getBySenderId: no exception");
			} catch (Exception e) {
				System.out.println("ReportsAPI: getBySenderId: has exception");
				result.put("TotalPage", null);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}
			
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportsAPI: getBySenderId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
	
	@GetMapping("/getByTournamentId")
	public ResponseEntity<Response> getByTournamentId(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "tournamentId") Long tournamentId) {
		System.out.println("ReportsAPI: getByTournamentId: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		List<ReportDTO> dtos = new ArrayList<ReportDTO>();
		List<ReportEntity> entities = new ArrayList<ReportEntity>();
		
		if (limit == null || limit <= 0)
			limit = 10;
		
		if (page == null || page <= 0)
			page = 1;
		
		if (tournamentId == null) {// senderId null
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param senderId");
		} else {//senderId not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<ReportEntity>) service.findByTournamentIdAndType(pageable, tournamentId, Const.REPORT_FRAUD);
				int totalPage = 0;
				TournamentEntity tournamentEntity = tournamentService.findOneById(tournamentId);
				int totalEntity = tournamentEntity.getReports().size();
				System.out.println(totalEntity);
				totalPage = totalEntity / limit;
				System.out.println(totalPage);
				if (totalEntity % limit != 0) {
					totalPage++;
					System.out.println(totalPage);
				}
				System.out.println(totalPage);
				for (ReportEntity entity: entities) {
					ReportDTO dto = converter.toDTO(entity);
					dtos.add(dto);
				}
				
				result.put("TotalPage", totalPage);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("ReportsAPI: getByTournamentId: no exception");
			} catch (Exception e) {
				System.out.println("ReportsAPI: getByTournamentId: has exception");
				result.put("TotalPage", null);
				result.put("Reports", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}
			
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("ReportsAPI: getByTournamentId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
}
