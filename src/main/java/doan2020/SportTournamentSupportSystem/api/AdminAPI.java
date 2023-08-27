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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.converter.CompetitionConverter;
import doan2020.SportTournamentSupportSystem.converter.PermissionConverter;
import doan2020.SportTournamentSupportSystem.converter.ReportConverter;
import doan2020.SportTournamentSupportSystem.converter.TeamConverter;
import doan2020.SportTournamentSupportSystem.converter.TournamentConverter;
import doan2020.SportTournamentSupportSystem.converter.UserConverter;
import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.dto.ReportDTO;
import doan2020.SportTournamentSupportSystem.dto.TournamentDTO;
import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;
import doan2020.SportTournamentSupportSystem.entity.ReportEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.ICompetitionService;
import doan2020.SportTournamentSupportSystem.service.IFinalStageSettingService;
import doan2020.SportTournamentSupportSystem.service.IGroupStageSettingService;
import doan2020.SportTournamentSupportSystem.service.IPermissionService;
import doan2020.SportTournamentSupportSystem.service.IReportService;
import doan2020.SportTournamentSupportSystem.service.IScheduleService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;
import doan2020.SportTournamentSupportSystem.service.IUserService;
import doan2020.SportTournamentSupportSystem.service.impl.FileStorageService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminAPI {

	@Autowired
	private ITournamentService tournamentService;

	@Autowired
	private IUserService userService;

	@Autowired
	private TournamentConverter tournamentconverter;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private PermissionConverter permissionConverter;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private ICompetitionService competitionService;

	@Autowired
	private CompetitionConverter competitionConverter;

	@Autowired
	private IScheduleService scheduleService;

	@Autowired
	private ITeamService teamService;

	@Autowired
	private TeamConverter teamConverter;

	@Autowired
	private IReportService reportService;

	@Autowired
	private ReportConverter reportConverter;

	@Autowired
	private IFinalStageSettingService finalStageService;

	@Autowired
	private IGroupStageSettingService groupStageService;

	@GetMapping("/searchTournament")
	public ResponseEntity<Response> searchTournament(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "searchString") String searchString) {
		System.out.println("AdminAPI: getBySearchString: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		List<TournamentEntity> entities = new ArrayList<TournamentEntity>();
		List<Map<String, Object>> tournaments = new ArrayList<Map<String, Object>>();
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
				entities = (List<TournamentEntity>) tournamentService.findBySearchString(pageable, searchString);

				Long totalEntity = tournamentService.countBySearchString(searchString);

				System.out.println("AdminAPI: getBySearchString: totalEntity: " + new Long(totalEntity).toString());

				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;

				for (TournamentEntity entity : entities) {
					TournamentDTO tournamentDTO = tournamentconverter.toDTO(entity);

					Map<String, Object> tournament = new HashMap<String, Object>();
					Map<String, Object> otherInformation = new HashMap<String, Object>();

					otherInformation = tournamentService.getOtherInformation(entity.getId());
					tournament.put("OtherInformation", otherInformation);
					tournament.put("Tournament", tournamentDTO);

					tournaments.add(tournament);
				}

				result.put("TotalPage", totalPage);
				result.put("Tournaments", tournaments);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("AdminAPI: getBySearchString: no exception");
			} catch (Exception e) {
				System.out.println("AdminAPI: getBySearchString: has exception");
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
		System.out.println("AdminAPI: getBySearchString: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/searchUser")
	public ResponseEntity<Response> searchUser(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "searchString") String searchString) {
		System.out.println("AdminAPI: getBySearchString: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		List<UserDTO> dtos = new ArrayList<UserDTO>();
		List<UserEntity> entities = new ArrayList<UserEntity>();

		if (limit == null || limit <= 0)
			limit = 3;

		if (page == null || page <= 0)
			page = 1;

		if (searchString == null) {// searchString null
			result.put("Users", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param searchString");
		} else {// searchString not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<UserEntity>) userService.findBySearchString(pageable, searchString);

				Long totalPage = 0l;

				Long totalEntity = userService.countBySearchString(searchString);

				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;

				for (UserEntity entity : entities) {
					UserDTO dto = userConverter.toDTO(entity);
					dtos.add(dto);
				}

				result.put("TotalPage", totalPage);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("AdminAPI: getBySearchString: no exception");
			} catch (Exception e) {
				System.out.println("AdminAPI: getBySearchString: has exception");
				result.put("TotalPage", null);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}

		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("AdminAPI: getBySearchString: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/searchUserWithStatus")
	public ResponseEntity<Response> searchUserWithStatus(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "searchString") String searchString, @RequestParam(value = "status") String status) {
		System.out.println("UsersAPI: searchUserWithStatus: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		List<UserDTO> dtos = new ArrayList<UserDTO>();
		List<UserEntity> entities = new ArrayList<UserEntity>();

		if (limit == null || limit <= 0)
			limit = 10;

		if (page == null || page <= 0)
			page = 1;

		if (searchString == null) {// searchString null
			result.put("Users", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param searchString");
		} else {// searchString not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<UserEntity>) userService.findBySearchStringAndStatus(pageable, searchString, status);

				Long totalPage = 0l;

				Long totalEntity = userService.countBySearchStringAndStatus(searchString, status);

				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;

				for (UserEntity entity : entities) {
					UserDTO dto = userConverter.toDTO(entity);
					dtos.add(dto);
				}

				result.put("TotalPage", totalPage);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("UsersAPI: searchUserWithStatus: no exception");
			} catch (Exception e) {
				System.out.println("UsersAPI: searchUserWithStatus: has exception");
				result.put("TotalPage", null);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}

		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("UsersAPI: searchUserWithStatus: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/* get One User */

	@GetMapping("/viewUserInformation")
	public ResponseEntity<Response> viewUserInformation(
			@RequestHeader(value = Const.TOKEN_HEADER, required = false) String jwt,
			@RequestParam(value = "id", required = false) Long id) {
		System.out.println("AdminAPI: getById: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		UserEntity user = new UserEntity();
		UserDTO dto = new UserDTO();
		PermissionEntity permissionEntity = new PermissionEntity();
		PermissionDTO permissionDTO = new PermissionDTO();

		try {

			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				user = userService.findOneById(id);
				if (user == null) {// not found
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else {// found

					dto = userConverter.toDTO(user);
					System.out.println("AdminAPI: getById: CP1");
					Long curentUserId = -1l;

					try {
						System.out.println("AdminAPI: getById: jwt: " + jwt);
						String curentUserName = jwtService.getUserNameFromJwtToken(jwt);
						user = userService.findByUsername(curentUserName);
						curentUserId = user.getId();
					} catch (Exception e) {

					}

					if (curentUserId == id) {
						permissionEntity = permissionService.findOneByName(Const.OWNER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					} else {
						permissionEntity = permissionService.findOneByName(Const.VIEWER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					}
					result.put("User", dto);
					config.put("Global", permissionDTO);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("AdminAPI: getById: no exception");

		} catch (Exception e) {
			System.out.println("AdminAPI: getById: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: getById: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	// Stop tournament
	@PutMapping("/stopTournament")
	public ResponseEntity<Response> stopTournament(@RequestParam(value = "tournamentId") Long id) {
		System.out.println("AdminAPI: stopTournament: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		TournamentEntity thisTournament = new TournamentEntity();
		TournamentDTO thisTournamentDTO = new TournamentDTO();

		try {
			if (id == null) {// id null
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null

				thisTournament = tournamentService.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {

					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						thisTournament = tournamentService.updateStatus(thisTournament,
								Const.TOURNAMENT_STATUS_STOPPED);

						thisTournamentDTO = tournamentconverter.toDTO(thisTournament);

						result.put("Tournament", thisTournamentDTO);
						config.put("Global", 0);
						error.put("MessageCode", 0);
						error.put("Message", "Success");
					} else {
						String message = "Unknown error";
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
							message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
							message = Const.TOURNAMENT_MESSAGE_INITIALIZING;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
							message = Const.TOURNAMENT_MESSAGE_FINISHED;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
							message = Const.TOURNAMENT_MESSAGE_STOPPED;
						}
						result.put("Tournament", null);
						config.put("Global", 0);
						error.put("MessageCode", 1);
						error.put("Message", message);
					}
				}

			}
			System.out.println("AdminAPI: stopTournament: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: stopTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: stopTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// Continue tournament
	@PutMapping("/continueTournament")
	public ResponseEntity<Response> continueTournament(@RequestParam(value = "tournamentId") Long id) {
		System.out.println("AdminAPI: continueTournament: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		TournamentEntity thisTournament = new TournamentEntity();
		TournamentDTO thisTournamentDTO = new TournamentDTO();

		try {
			if (id == null) {// id null
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null

				thisTournament = tournamentService.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						thisTournament = tournamentService.updateStatus(thisTournament,
								Const.TOURNAMENT_STATUS_PROCESSING);

						thisTournamentDTO = tournamentconverter.toDTO(thisTournament);

						result.put("Tournament", thisTournamentDTO);
						config.put("Global", 0);
						error.put("MessageCode", 0);
						error.put("Message", "Success");
					} else {
						String message = "Unknown error";
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
							message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
							message = Const.TOURNAMENT_MESSAGE_INITIALIZING;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
							message = Const.TOURNAMENT_MESSAGE_FINISHED;
						}
						if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
							message = Const.TOURNAMENT_MESSAGE_PROCESSING;
						}
						result.put("Tournament", null);
						config.put("Global", 0);
						error.put("MessageCode", 1);
						error.put("Message", message);
					}
				}

			}
			System.out.println("AdminAPI: continueTournament: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: continueTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: continueTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// Change account role
	@PutMapping("/changeAccountRole")
	public ResponseEntity<Response> changeAccountRole(@RequestParam(value="userId") Long id) {
		System.out.println("AdminAPI: changeAccountRole: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		UserEntity userEntity = new UserEntity();
		UserDTO userDTO = new UserDTO();
		try {
			if (id == null) {// id null or roleName null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param userId");
			} else {// id not null or roleName null

				userEntity = userService.findOneById(id);
				if (userEntity == null) {
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "User is not exist");
				} else {

					userEntity = userService.updateRole(userEntity, Const.ROLE_ADMIN);

					userDTO = userConverter.toDTO(userEntity);

					result.put("User", userDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "ChangeAccountRole success");
				}

			}
		} catch (Exception e) {
			System.out.println("AdminAPI: ChangeAccountRole: has exception");
			result.put("User", userDTO);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: editUser: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// Activate account
	@PutMapping("/activateAccount")
	public ResponseEntity<Response> activateAccount(@RequestParam(value = "userId") Long id) {
		System.out.println("AdminAPI: activateAccount: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		UserEntity thisUser = new UserEntity();
		UserDTO thisUserDTO = new UserDTO();

		try {
			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null

				thisUser = userService.findOneById(id);
				if (thisUser == null) {
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "User is not exist");
				} else {

					thisUser = userService.updateStatus(thisUser, Const.STATUS_ACTIVE);

					thisUserDTO = userConverter.toDTO(thisUser);

					result.put("User", thisUserDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Success");
				}

			}
			System.out.println("AdminAPI: activateAccount: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: activateAccount: has exception");
			result.put("User", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: activateAccount: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// Deactivate account
	@PutMapping("/deactivateAccount")
	public ResponseEntity<Response> deactivateAccount(@RequestParam(value = "userId") Long id) {
		System.out.println("AdminAPI: deactivateAccount: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		UserEntity thisUser = new UserEntity();
		UserDTO thisUserDTO = new UserDTO();

		try {
			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null

				thisUser = userService.findOneById(id);
				if (thisUser == null) {
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "User is not exist");
				} else {

					thisUser = userService.updateStatus(thisUser, Const.STATUS_DEACTIVE);

					thisUserDTO = userConverter.toDTO(thisUser);

					result.put("User", thisUserDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Success");
				}

			}
			System.out.println("AdminAPI: deactivateAccount: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: deactivateAccount: has exception");
			result.put("User", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("AdminAPI: deactivateAccount: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// View system report
	@GetMapping("/viewSystemReport")
	public ResponseEntity<Response> viewSystemReports(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("AdminAPI: ViewSystemReport: start");

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

		try {
			Pageable pageable = PageRequest.of(page - 1, limit);
			entities = (List<ReportEntity>) reportService.findByType(pageable, Const.REPORT_SYSTEM_ERROR);
			int totalPage = 0;
			List<ReportEntity> entities2 = (List<ReportEntity>) reportService.findByType(Const.REPORT_SYSTEM_ERROR);
			System.out.println(entities2);
			int totalEntity = entities2.size();
			totalPage = totalEntity / limit;
			System.out.println(totalPage);
			if (totalEntity % limit != 0) {
				totalPage++;
				System.out.println(totalPage);
			}
			System.out.println(totalPage);
			for (ReportEntity entity : entities) {
				ReportDTO dto = reportConverter.toDTO(entity);
				dtos.add(dto);
			}

			result.put("TotalPage", totalPage);
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Found");
			System.out.println("AdminAPI: ViewSystemReport: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: ViewSystemReport: has exception");
			result.put("TotalPage", null);
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("AdminAPI: ViewSystemReport: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

	// View violation report
	@GetMapping("/viewViolationReport")
	public ResponseEntity<Response> viewViolationReports(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("AdminAPI: ViewViolationReport: start");

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

		try {
			Pageable pageable = PageRequest.of(page - 1, limit);
			entities = (List<ReportEntity>) reportService.findByType(pageable, Const.REPORT_VIOLATION);
			int totalPage = 0;
			List<ReportEntity> entities2 = (List<ReportEntity>) reportService.findByType(Const.REPORT_VIOLATION);
			System.out.println(entities2);
			int totalEntity = entities2.size();
			totalPage = totalEntity / limit;
			System.out.println(totalPage);
			if (totalEntity % limit != 0) {
				totalPage++;
				System.out.println(totalPage);
			}
			System.out.println(totalPage);
			for (ReportEntity entity : entities) {
				ReportDTO dto = reportConverter.toDTO(entity);
				dtos.add(dto);
			}

			result.put("TotalPage", totalPage);
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Found");
			System.out.println("AdminAPI: ViewViolationReport: no exception");
		} catch (Exception e) {
			System.out.println("AdminAPI: ViewViolationReport: has exception");
			result.put("TotalPage", null);
			result.put("Reports", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("AdminAPI: ViewViolationReport: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}

}
