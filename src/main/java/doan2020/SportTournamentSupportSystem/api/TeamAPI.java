package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
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

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.converter.PermissionConverter;
import doan2020.SportTournamentSupportSystem.converter.TeamConverter;
import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.dto.TeamDTO;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.model.Entity.Player;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IPermissionService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;
import doan2020.SportTournamentSupportSystem.service.IUserService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamAPI {
	@Autowired
	private ITeamService service;

	@Autowired
	private TeamConverter converter;

	@Autowired
	private IUserService userService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private PermissionConverter permissionConverter;

	@Autowired
	private JwtService jwtService;

	/*
	 * Get team theo id
	 */
	@GetMapping("")
	public ResponseEntity<Response> getOneTeam(@RequestHeader(value = Const.TOKEN_HEADER, required = false) String jwt,
			@RequestParam(value = "id", required = true) Long id) {
		System.out.println("TeamAPI - getOneTeam - start");
		System.out.println(id);
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		TeamDTO dto = new TeamDTO();
		UserEntity user = new UserEntity();
		PermissionEntity permissionEntity = new PermissionEntity();
		PermissionDTO permissionDTO = new PermissionDTO();
		try {

			if (id == null) {// id not exist
				System.out.println("TeamAPI - getOneTeam - cp1");
				result.put("Team", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Requried id");
			} else {// id exist
				teamEntity = service.findOneById(id);
				System.out.println("TeamAPI - getOneTeam - cp1");
				if (teamEntity == null) {// competition is not exist
					result.put("Team", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Team is not exist");
				} else {// competition is exist

					dto = converter.toDTO(teamEntity);
					ArrayList<Player> players = (ArrayList<Player>) service
							.getTeamPlayerFromFile(teamEntity.getCompetition().getId(), id);
					dto.setPlayers(players);

					Long curentUserId = -1l;

					try {
						String curentUserName = jwtService.getUserNameFromJwtToken(jwt);
						user = userService.findByUsername(curentUserName);
						curentUserId = user.getId();
					} catch (Exception e) {

					}

					if (curentUserId == teamEntity.getCompetition().getTournament().getCreator().getId()) {
						permissionEntity = permissionService.findOneByName(Const.OWNER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					} else {
						permissionEntity = permissionService.findOneByName(Const.VIEWER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					}

					result.put("Team", dto);
					config.put("Global", permissionDTO);
					error.put("MessageCode", 0);
					error.put("Message", "get Team Successfully");
				}

			}

		} catch (Exception e) {
			System.out.println("TeamAPI - getOneTeam - cp5");
			result.put("Team", null);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "exception");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("TeamAPI - getOneTeam - cp pass");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Create one Team
	 * 
	 */
	@PostMapping()
	@CrossOrigin
	public ResponseEntity<Response> createTeam(@RequestBody TeamDTO dto) {
		System.out.println("Team API - createTeam - start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		TeamDTO teamDTO = null;
		ArrayList<Player> players = null;

		try {
			teamEntity = converter.toEntity(dto);

			System.out.println("Team API: createTeam: CP1");
			System.out.println(dto.toString());

			players = new ArrayList<>();

			if (teamEntity == null) {
				result.put("Team", dto);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Team info invalid");
			} else {

				TournamentEntity tour = teamEntity.getCompetition().getTournament();
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)
						|| tour.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {

					teamEntity.setStatus(Const.TEAM_STATUS_JOINED);
					teamEntity = service.create(teamEntity);

					System.out.println(teamEntity.toString());

					teamDTO = converter.toDTO(teamEntity);
					teamDTO.setPlayers(players);

					result.put("Team", teamDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Successful");

					// save file

					service.saveTeamPlayersToFile(teamEntity, players);
				} else {
					String message = "Unknown error";
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					result.put("Competition", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}
			}

			System.out.println("Team API - createTeam - no exception");
		} catch (Exception e) {
			System.out.println("Team API - createTeam - has exception");
			result.put("Team", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - createTeam - pass");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit one Team
	 * 
	 */
	@PutMapping
	public ResponseEntity<Response> editTeam(@RequestParam("id") Long id, @RequestBody TeamDTO teamDTO) {
		System.out.println("Team API - editTeam - start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		TeamDTO dto = null;
		ArrayList<Player> players = null;

		try {
			teamEntity = converter.toEntity(teamDTO);

			if (teamEntity == null) {
				result.put("Team", teamDTO);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Team info invalid");
			} else {

				TournamentEntity tour = teamEntity.getCompetition().getTournament();
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)
						|| tour.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {

					teamEntity = service.update(id, teamEntity);
					players = teamDTO.getPlayers();

					if (players != null) {
						service.saveTeamPlayersToFile(teamEntity, players);
					} else {
						players = (ArrayList<Player>) service.getTeamPlayerFromFile(teamEntity.getCompetition().getId(),
								id);
					}

					dto = converter.toDTO(teamEntity);
					dto.setPlayers(players);

					result.put("Team", dto);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Successful");

					// save file

					service.saveTeamPlayersToFile(teamEntity, players);
				} else {
					String message = "Unknown error";

					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					result.put("Competition", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}

			}

			System.out.println("Team API - editTeam - no exception");
		} catch (Exception e) {
			System.out.println("Team API - editTeam - has exception");
			result.put("Team", null);
			result.put("PlayerList", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - editTeam - finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/* delete one Team */
	@DeleteMapping("")
	public ResponseEntity<Response> deleteTeam(@RequestParam("id") Long id) {
		System.out.println("Team API - deleteTeam - start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		TeamDTO resDTO = new TeamDTO();
		try {

			if (id == null) {// id is not exist
				System.out.println("Team API - deleteTeam - cp1");
				result.put("Team", resDTO);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required Id");
			} else {// id is exist
				TournamentEntity tour = service.findOneById(id).getCompetition().getTournament();
				if (!tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {

					teamEntity = service.delete(id);
					resDTO = converter.toDTO(teamEntity);
					result.put("Team", resDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Delete Team successfull");
					System.out.println("Team API - deleteTeam - cp2");
				} else {
					String message = "Unknown error";
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					result.put("Competition", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}
			}

			System.out.println("Team API - deleteTeam - has no exception");
		} catch (Exception e) {
			System.out.println("Team API - deleteTeam - has exception");
			result.put("Team", resDTO);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - deleteTeam - pass");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/registerTeam")
	public ResponseEntity<Response> createTeamByRegister(@RequestBody TeamDTO teamDTO) {
		System.out.println("Team API - createTeamByRegister - start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		TeamDTO dto = null;
		ArrayList<Player> players = null;

		try {
			teamEntity = converter.toEntity(teamDTO);

			if (teamEntity == null) {
				result.put("Team", teamDTO);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Team info invalid");
			} else {

			}
			TournamentEntity tour = teamEntity.getCompetition().getTournament();
			if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {

				teamEntity.setStatus(Const.TEAM_STATUS_PENDING);
				teamEntity = service.create(teamEntity);
				dto = converter.toDTO(teamEntity);
				dto.setPlayers(players);

				result.put("Team", dto);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Successful");
				// save file

				service.saveTeamPlayersToFile(teamEntity, players);
			} else {
				String message = "Unknown error";
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)) {
					message = Const.TOURNAMENT_MESSAGE_INITIALIZING;
				}
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
					message = Const.TOURNAMENT_MESSAGE_PROCESSING;
				}
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
					message = Const.TOURNAMENT_MESSAGE_FINISHED;
				}
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
					message = Const.TOURNAMENT_MESSAGE_STOPPED;
				}
				result.put("Competition", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", message);
			}

			System.out.println("Team API - createTeamByRegister - no exception");
		} catch (Exception e) {
			System.out.println("Team API - createTeamByRegister - has exception");
			result.put("Team", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - createTeamByRegister - finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/approve")
	public ResponseEntity<Response> approveTeam(@RequestParam("id") Long id) {
		System.out.println("Team API - editTeam - start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		ArrayList<Player> players = null;

		try {

			teamEntity = service.findOneById(id);

			if (teamEntity == null) {
				result.put("Team", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Team not exist");
			} else {
				TournamentEntity tour = teamEntity.getCompetition().getTournament();
				if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_INITIALIZING)
						|| tour.getStatus().contains(Const.TOURNAMENT_STATUS_REGISTRATION_OPENING)) {

					teamEntity.setStatus(Const.TEAM_STATUS_JOINED);
					Long competitionId = teamEntity.getCompetition().getId();
					Long maxSeedNo = service.getMaxSeedNoByCompetitionId(competitionId);
					teamEntity.setSeedNo(maxSeedNo + 1);
					teamEntity = service.update(id, teamEntity);

					players = (ArrayList<Player>) service.getTeamPlayerFromFile(teamEntity.getCompetition().getId(),
							id);

					ArrayList<TeamEntity> newPendingList = new ArrayList<>();
					newPendingList
							.addAll(service.findByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_PENDING));

					ArrayList<TeamDTO> dtos = new ArrayList<>();

					for (TeamEntity team : newPendingList) {
						TeamDTO dto = converter.toDTO(team);
						dto.setPlayers((ArrayList<Player>)service.getTeamPlayerFromFile(competitionId, team.getId()));
						dtos.add(dto);

					}

					result.put("Teams", dtos);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Successful");

					// save file
					service.saveTeamPlayersToFile(teamEntity, players);
				} else {
					String message = "Unknown error";

					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_PROCESSING)) {
						message = Const.TOURNAMENT_MESSAGE_PROCESSING;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_FINISHED)) {
						message = Const.TOURNAMENT_MESSAGE_FINISHED;
					}
					if (tour.getStatus().contains(Const.TOURNAMENT_STATUS_STOPPED)) {
						message = Const.TOURNAMENT_MESSAGE_STOPPED;
					}
					result.put("Teams", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", message);
				}

			}

			System.out.println("Team API - editTeam - no exception");
		} catch (Exception e) {
			System.out.println("Team API - editTeam - has exception");
			result.put("Team", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - editTeam - finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PutMapping("/reject")
	public ResponseEntity<Response> rejectTeam(@RequestParam("id") Long id) {
		System.out.println("Team API: rejectTeam: start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		TeamEntity teamEntity = new TeamEntity();
		ArrayList<Player> players = null;

		try {

			teamEntity = service.findOneById(id);

			if (teamEntity == null) {
				result.put("Team", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Team not exist");
			} else {

				teamEntity.setStatus(Const.TEAM_STATUS_REJECTED);
				teamEntity = service.update(id, teamEntity);
				Long competitionId = teamEntity.getCompetition().getId();
				
				players = (ArrayList<Player>) service.getTeamPlayerFromFile(teamEntity.getCompetition().getId(), id);

				ArrayList<TeamEntity> newPendingList = new ArrayList<>();
				newPendingList
						.addAll(service.findByCompetitionIdAndStatus(competitionId, Const.TEAM_STATUS_PENDING));

				ArrayList<TeamDTO> dtos = new ArrayList<>();

				for (TeamEntity team : newPendingList) {
					TeamDTO dto = converter.toDTO(team);
					dto.setPlayers((ArrayList<Player>)service.getTeamPlayerFromFile(competitionId, team.getId()));
					dtos.add(dto);

				}

				result.put("Teams", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Successful");

				// save file

				service.saveTeamPlayersToFile(teamEntity, players);

			}

			System.out.println("Team API: rejectTeam - no exception");
		} catch (Exception e) {
			System.out.println("Team API: rejectTeam - has exception");
			result.put("Team", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("Team API - rejectTeam - finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
