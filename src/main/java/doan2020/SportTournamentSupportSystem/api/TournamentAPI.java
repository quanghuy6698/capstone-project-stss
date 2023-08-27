package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.converter.CompetitionConverter;
import doan2020.SportTournamentSupportSystem.converter.PermissionConverter;
import doan2020.SportTournamentSupportSystem.converter.TournamentConverter;
import doan2020.SportTournamentSupportSystem.dto.CompetitionDTO;
import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.dto.TournamentDTO;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IFileStorageService;
import doan2020.SportTournamentSupportSystem.service.IPermissionService;
import doan2020.SportTournamentSupportSystem.service.IScheduleService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;
import doan2020.SportTournamentSupportSystem.service.impl.AzureBlobAdapterService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;
import doan2020.SportTournamentSupportSystem.service.impl.UserService;

@RestController
@CrossOrigin
@RequestMapping("/tournament")
public class TournamentAPI {
	@Autowired
	private ITournamentService service;

	@Autowired
	private UserService userService;

	@Autowired
	private ITeamService teamService;

	@Autowired
	private CompetitionConverter competitionConverter;

	@Autowired
	private TournamentConverter converter;

	@Autowired
	private IFileStorageService fileStorageService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private PermissionConverter permissionConverter;

	@Autowired
	private IScheduleService scheduleService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AzureBlobAdapterService azureBlobAdapterService;

	@GetMapping("")
	public ResponseEntity<Response> getTournament(
			@RequestHeader(value = Const.TOKEN_HEADER, required = false) String jwt,
			@RequestParam(value = "id", required = false) Long id) {
		System.out.println("TournamentAPI: getTournament: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		TournamentEntity tournamentEntity = new TournamentEntity();
		TournamentDTO tournamentDTO = new TournamentDTO();
		UserEntity user = new UserEntity();
		PermissionEntity permissionEntity = new PermissionEntity();
		PermissionDTO permissionDTO = new PermissionDTO();
		Map<String, Object> otherInformation = new HashMap<String, Object>();

		try {
			if (id == null) { // id null
				result.put("Tournament", tournamentDTO);
				result.put("OtherInformation", otherInformation);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null

				tournamentEntity = service.findOneById(id);

				if (tournamentEntity == null) { // not found
					result.put("Tournament", tournamentDTO);
					result.put("OtherInformation", otherInformation);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found

					tournamentDTO = converter.toDTO(tournamentEntity);

					otherInformation = service.getOtherInformation(id);

					Long curentUserId = -1l;

					try {
						String curentUserName = jwtService.getUserNameFromJwtToken(jwt);
						user = userService.findByUsername(curentUserName);
						curentUserId = user.getId();
					} catch (Exception e) {

					}

					if (curentUserId == tournamentEntity.getCreator().getId()) {
						permissionEntity = permissionService.findOneByName(Const.OWNER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					} else {
						permissionEntity = permissionService.findOneByName(Const.VIEWER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					}

					result.put("Tournament", tournamentDTO);
					result.put("OtherInformation", otherInformation);
					config.put("Global", permissionDTO);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("TournamentAPI: getTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: getTournament: has exception");
			result.put("Tournament", null);
			result.put("OtherInformation", otherInformation);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("TournamentAPI: getTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Tournament
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createTournament(@RequestBody TournamentDTO newTournament) {
		System.out.println("TournamentAPI: createTournament: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TournamentEntity tournamentEntity = new TournamentEntity();

		try {
			tournamentEntity = converter.toEntity(newTournament);

			tournamentEntity = service.create(tournamentEntity);

			TournamentDTO dto = converter.toDTO(tournamentEntity);

			result.put("Tournament", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Tournament create successfuly");
			System.out.println("TournamentAPI: createTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: createTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("TournamentAPI: createTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Tournament
	 * 
	 */
	@PutMapping
	@CrossOrigin
	public ResponseEntity<Response> editTournament(@RequestBody TournamentDTO tournament, @RequestParam Long id) {
		System.out.println("TournamentAPI: editTournament: start");

		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TournamentEntity tournamentEntity = new TournamentEntity();
		TournamentEntity newEntity = new TournamentEntity();

		try {
			tournamentEntity = service.findOneById(id);
			newEntity = converter.toEntity(tournament);
			if (tournamentEntity == null) {
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Tournament not exist");
			} else {
				String message = "Unknown error";
				if (tournamentEntity.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
					message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
				}
				if (tournamentEntity.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
					message = Const.TOURNAMENT_MESSAGE_PROCESSING;
				}
				if (tournamentEntity.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
					message = Const.TOURNAMENT_MESSAGE_FINISHED;
				}
				if (tournamentEntity.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
					message = Const.TOURNAMENT_MESSAGE_STOPPED;
				}

				if (tournamentEntity.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {

					newEntity = service.update(id, newEntity);

					TournamentDTO dto = converter.toDTO(newEntity);

					message = Const.RESPONSE_SUCCESS;
					result.put("Tournament", dto);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", message);
				} else {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}
			}
			System.out.println("TournamentAPI: editTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: editTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("TournamentAPI: editTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/uploadAvatar")
	public ResponseEntity<Response> uploadAvatar(@RequestBody MultipartFile file, @RequestParam Long id) {

		System.out.println("TournamentAPI: uploadAvatar: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		System.out.println("TournamentAPI: uploadAvatar: CP1");
		System.out.println(id);
		System.out.println(file.getOriginalFilename());
		try {
			if (id == null) {// id null
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				System.out.println("TournamentAPI: uploadAvatar: CP2");
				System.out.println(file);
				String containName = "tournament-"+String.valueOf(id);

				// create container / folder to azure
				azureBlobAdapterService.createContainer(containName);

				// upload image to this container
				String urlImage = azureBlobAdapterService.upload(file, containName, Const.AVATAR).toString();

				System.out.println("TournamentAPI: uploadAvatar: CP3");
				if (urlImage == null) {// fileName invalid
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Could not store file");
				} else {// fileName valid
					System.out.println("check point");
					TournamentDTO dto = new TournamentDTO();
					dto.setAvatar(urlImage);
					TournamentEntity tournamentEntity = converter.toEntity(dto);
					tournamentEntity = service.updateAvatar(id, tournamentEntity);

					result.put("Tournament", converter.toDTO(tournamentEntity));
					error.put("MessageCode", 0);
					error.put("Message", "Upload avatar and Edit Tournament Successfull");
				}
			}
			System.out.println("TournamentAPI: uploadAvatar: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: uploadAvatar: has exception");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: uploadAvatar: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/uploadBackground")
	public ResponseEntity<Response> uploadBackground(@RequestBody MultipartFile file, @RequestParam Long id) {

		System.out.println("TournamentAPI: uploadBackground: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		System.out.println("TournamentAPI: uploadAvatar: CP1");
		System.out.println(id);
		System.out.println(file.getOriginalFilename());
		try {
			if (id == null) {// id null
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				System.out.println("TournamentAPI: uploadAvatar: CP2");
				System.out.println(file);
				
				String containName = "tournament-"+String.valueOf(id);
				// create container / folder to azure
				azureBlobAdapterService.createContainer(containName);

				// upload image to this container
				String urlImage = azureBlobAdapterService.upload(file, containName, Const.BACKGROUND).toString();
				System.out.println("TournamentAPI: uploadAvatar: CP3");
				if (urlImage == null) {// fileName invalid
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Could not store file");
				} else {// fileName valid
					System.out.println("check point");
					TournamentDTO dto = new TournamentDTO();
					dto.setBackground(urlImage);
					TournamentEntity tournamentEntity = converter.toEntity(dto);
					tournamentEntity = service.updateBackground(id, tournamentEntity);

					result.put("Tournament", converter.toDTO(tournamentEntity));
					error.put("MessageCode", 0);
					error.put("Message", "Upload background and Edit Tournament Successfull");
				}
			}
			System.out.println("TournamentAPI: uploadBackground: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: uploadBackground: has exception");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: uploadBackground: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/start")
	public ResponseEntity<Response> startTournament(@RequestParam Long id) {
		System.out.println("TournamentAPI: startTournament: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		TournamentEntity thisTournament = new TournamentEntity();
		TournamentDTO thisTournamentDTO = new TournamentDTO();

		System.out.println("CP1");
		try {
			if (id == null) {// id null
				result.put("Tournament", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				System.out.println("CP0-1");
				thisTournament = service.findOneById(id);
				System.out.println("CP2");
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {
					System.out.println("CP3");
					int code = 1;
					String message = "Unknown error";
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
						message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}

					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
						System.out.println("CP4");
						Collection<CompetitionEntity> comps = thisTournament.getCompetitions();

						message = Const.RESPONSE_SUCCESS;
						code = 0;

						ArrayList<CompetitionDTO> notReadyCompetitions = new ArrayList<>();

						for (CompetitionEntity comp : comps) {
							int totalTeam = teamService
									.countByCompetitionIdAndStatus(comp.getId(), Const.TEAM_STATUS_JOINED).intValue();
							if (totalTeam <= 0)
								notReadyCompetitions.add(competitionConverter.toDTO(comp));
						}

						if (notReadyCompetitions.size() > 0) {
							code = 1;
							message = Const.TOURNAMENT_MESSAGE_NOT_READY;
							result.put("NotReadyCompetitions", notReadyCompetitions);
						} else {
							if (comps.size() == 0) {
								code = 1;
								message = Const.TOURNAMENT_MESSAGE_NO_COMPETITION;
							} else {

								System.out.println("Try to Start Tournament");

								for (CompetitionEntity comp : comps) {
									System.out.println("Try to save to DB: comp: " + comp.getId());
									scheduleService.createMatchesInDatabase(comp);
									System.out.println("save finish");
								}
								System.out.println("CP5");
								thisTournament.setStatus(Const.TOURNAMENT_STATUS_PROCESSING);
								thisTournament = service.update(id, thisTournament);
								thisTournamentDTO = converter.toDTO(thisTournament);
							}
						}
					}
					result.put("Tournament", thisTournamentDTO);
					config.put("Global", 0);
					error.put("MessageCode", code);
					error.put("Message", message);
				}

			}
			System.out.println("TournamentAPI: startTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: startTournament: has exception");
			System.out.println(e);
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: startTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/finish")
	public ResponseEntity<Response> finishTournament(@RequestParam Long id) {
		System.out.println("TournamentAPI: finishTournament: start");
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

				thisTournament = service.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {

					String message = "Unknown error";
					int code = 1;
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
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						thisTournament = service.updateStatus(thisTournament, Const.TOURNAMENT_STATUS_FINISHED);

						thisTournamentDTO = converter.toDTO(thisTournament);
						code = 0;
					}

					result.put("Tournament", thisTournamentDTO);
					config.put("Global", 0);
					error.put("MessageCode", code);
					error.put("Message", message);
				}

			}
			System.out.println("TournamentAPI: finishTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: finishTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: finishTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/openRegistration")
	public ResponseEntity<Response> openRegistration(@RequestParam Long id) {
		System.out.println("TournamentAPI: openRegistration: start");
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

				thisTournament = service.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {

					System.out.println(thisTournament.getStatus());

					String message = "Unknown error";
					int code = 1;
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
						message = Const.TOURNAMENT_MESSAGE_REGISTRATION_OPENING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
						thisTournament = service.updateStatus(thisTournament,
								Const.TOURNAMENT_STATUS_REGISTRATION_OPENING);

						thisTournamentDTO = converter.toDTO(thisTournament);
						message = "Success";
						code = 0;
					}

					result.put("Tournament", thisTournamentDTO);
					config.put("Global", 0);
					error.put("MessageCode", code);
					error.put("Message", message);
				}

			}
			System.out.println("TournamentAPI: openRegistration: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: openRegistration: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: finishTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/closeRegistration")
	public ResponseEntity<Response> closeRegistration(@RequestParam Long id) {
		System.out.println("TournamentAPI: closeRegistration: start");
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

				thisTournament = service.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {

					String message = "Unknown error";
					int code = 1;
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
						message = Const.TOURNAMENT_MESSAGE_INITIALIZING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {
						thisTournament = service.updateStatus(thisTournament, Const.TOURNAMENT_STATUS_INITIALIZING);

						thisTournamentDTO = converter.toDTO(thisTournament);
						message = "Success";
						code = 0;
					}

					result.put("Tournament", thisTournamentDTO);
					config.put("Global", 0);
					error.put("MessageCode", code);
					error.put("Message", message);
				}

			}
			System.out.println("TournamentAPI: closeRegistration: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: closeRegistration: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: closeRegistration: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@DeleteMapping()
	public ResponseEntity<Response> deleteTournament(@RequestParam Long id) {
		System.out.println("TournamentAPI: finishTournament: start");
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

				thisTournament = service.findOneById(id);
				if (thisTournament == null) {
					result.put("Tournament", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Tournament is not exist");
				} else {

					String message = "Unknown error";
					int code = 1;
					if (thisTournament.getStatus().contains(Const.TOURNAMENT_MESSAGE_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					} else {
						thisTournament = service.delete(id);
						thisTournamentDTO = converter.toDTO(thisTournament);
						code = 0;
						message = Const.RESPONSE_SUCCESS;
					}

					result.put("Tournament", thisTournamentDTO);
					config.put("Global", 0);
					error.put("MessageCode", code);
					error.put("Message", message);
				}

			}
			System.out.println("TournamentAPI: finishTournament: no exception");
		} catch (Exception e) {
			System.out.println("TournamentAPI: finishTournament: has exception");
			result.put("Tournament", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TournamentAPI: finishTournament: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
