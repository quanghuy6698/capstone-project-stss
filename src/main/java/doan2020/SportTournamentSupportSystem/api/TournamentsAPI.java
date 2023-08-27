package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
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

import doan2020.SportTournamentSupportSystem.converter.TournamentConverter;
import doan2020.SportTournamentSupportSystem.dto.TournamentDTO;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;
import doan2020.SportTournamentSupportSystem.service.IUserService;

@RestController
@CrossOrigin
@RequestMapping("/tournaments")
public class TournamentsAPI {

	@Autowired
	private ITournamentService service;

	@Autowired
	private IUserService userService;

	@Autowired
	private TournamentConverter converter;

	@GetMapping("")
	public ResponseEntity<Response> getAllTournament(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("TournamentsAPI: getAllTournament: start");

		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		HttpStatus httpStatus = HttpStatus.OK;

		Collection<TournamentEntity> findPage = new ArrayList<>();
		List<Map<String, Object>> tournaments = new ArrayList<Map<String,Object>>();
		try {
			if (limit == null)
				limit = 10;
			if (limit == 0)
				limit = 10;
			if (page == null)
				page = 1;

			Pageable pageable = PageRequest.of(page - 1, limit);
			findPage = service.findAll(pageable);

			for (TournamentEntity entity : findPage) {
				
				Map<String, Object> tournament = new HashMap<String, Object>();
				Map<String, Object> otherInformation = new HashMap<String, Object>();
				TournamentDTO tournamentDTO = converter.toDTO(entity);

				otherInformation = service.getOtherInformation(entity.getId());
				tournament.put("OtherInformation", otherInformation);
				tournament.put("Tournament", tournamentDTO);
				
				tournaments.add(tournament);
				
			}
			
			long total = service.countAll();
			long totalPage = total / limit;
			if (total % limit != 0) {
				totalPage++;
			}
			result.put("TotalPage", totalPage);
			result.put("Tournaments", tournaments);
			error.put("MessageCode", 0);
			error.put("Message", "Get page successfully");

			System.out.println("TournamentsAPI: getAllTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentsAPI: getAllTournament: has exception");
			result.put("Users", tournaments);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentsAPI: getAllTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/getByUserId")
	public ResponseEntity<Response> getTournamentsPagingByUserId(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "userId") Long userId) {

		System.out.println("TournamentsAPI: getTournamentsPagingByUserId: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		List<TournamentEntity> entities = new ArrayList<TournamentEntity>();
		List<Map<String, Object>> tournaments = new ArrayList<Map<String,Object>>();
		if (limit == null || limit <= 0)
			limit = 3;

		if (page == null || page <= 0)
			page = 1;

		if (userId == null) {// userId null
			result.put("Tournaments", tournaments);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param userId");
		} else {// userId not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<TournamentEntity>) service.findByCreatorId(pageable, userId);
				int totalPage = 0;
				UserEntity creator = userService.findOneById(userId);
				int totalEntity = creator.getTournaments().size();
				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;

				for (TournamentEntity entity : entities) {
					TournamentDTO tournamentDTO = converter.toDTO(entity);

					Map<String, Object> tournament = new HashMap<String, Object>();
					Map<String, Object> otherInformation = new HashMap<String, Object>();

					otherInformation = service.getOtherInformation(entity.getId());
					tournament.put("OtherInformation", otherInformation);
					tournament.put("Tournament", tournamentDTO);
					
					tournaments.add(tournament);
				}

				result.put("TotalPage", totalPage);
				result.put("Tournaments", tournaments);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("TournamentsAPI: getTournamentsPagingByUserId: no exception");
			} catch (Exception e) {
				System.out.println("TournamentsAPI: getTournamentsPagingByUserId: has exception");
				result.put("TotalPage", null);
				result.put("Tournaments", tournaments);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}

		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("TournamentsAPI: getTournamentsPagingByUserId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/getBySearchString")
	public ResponseEntity<Response> getBySearchString(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "searchString") String searchString) {
		System.out.println("TournamentsAPI: getBySearchString: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		List<TournamentEntity> entities = new ArrayList<TournamentEntity>();
		List<Map<String, Object>> tournaments = new ArrayList<Map<String,Object>>();
		Long totalPage = 0l;
		if (limit == null || limit <= 0)
			limit = 3;

		if (page == null || page <= 0)
			page = 1;

		if (searchString == null) {// searchString null
			result.put("Tournaments", tournaments);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param searchString");
		} else {// searchString not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<TournamentEntity>) service.findBySearchString(pageable, searchString);
				
				
				
				Long totalEntity = service.countBySearchString(searchString);
				
				System.out.println("TournamentsAPI: getBySearchString: totalEntity: " + new Long(totalEntity).toString());
				
				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;

				for (TournamentEntity entity : entities) {
					TournamentDTO tournamentDTO = converter.toDTO(entity);

					Map<String, Object> tournament = new HashMap<String, Object>();
					Map<String, Object> otherInformation = new HashMap<String, Object>();

					otherInformation = service.getOtherInformation(entity.getId());
					tournament.put("OtherInformation", otherInformation);
					tournament.put("Tournament", tournamentDTO);
					
					tournaments.add(tournament);
				}

				result.put("TotalPage", totalPage);
				result.put("Tournaments", tournaments);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("TournamentsAPI: getBySearchString: no exception");
			} catch (Exception e) {
				System.out.println("TournamentsAPI: getBySearchString: has exception");
				result.put("TotalPage", totalPage);
				result.put("Tournaments", tournaments);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}

		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("TournamentsAPI: getBySearchString: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
