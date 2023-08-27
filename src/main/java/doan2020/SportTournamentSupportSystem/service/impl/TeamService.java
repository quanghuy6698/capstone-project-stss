
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.model.Entity.Player;
import doan2020.SportTournamentSupportSystem.repository.TeamRepository;
import doan2020.SportTournamentSupportSystem.service.IFileStorageService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;

@Service
public class TeamService implements ITeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private IFileStorageService fileService;

	@Override
	public TeamEntity create(TeamEntity teamEntity) {
		System.out.println("TeamService: create: start");
		TeamEntity newEntity = null;
		try {
			if (teamEntity.getStatus() == Const.TEAM_STATUS_PENDING) {
				System.out.println("TeamService: create: TEAM_STATUS_PENDING");
				newEntity = teamRepository.save(teamEntity);
			} else if (teamEntity.getStatus() == Const.TEAM_STATUS_JOINED) {
				System.out.println("TeamService: create: TEAM_STATUS_JOINED");
				Long competitionId = teamEntity.getCompetition().getId();
				Long maxSeedNo = getMaxSeedNoByCompetitionId(competitionId);
				teamEntity.setSeedNo(maxSeedNo + 1l);
				newEntity = teamRepository.save(teamEntity);
				System.out.println("TeamService: create: no exception");
			}
		} catch (Exception e) {
			System.out.println("TeamService: create: has exception");
			System.out.println(e);
			
		}
		System.out.println("TeamService: create: finish");
		return newEntity;
	}

	@Override
	public TeamEntity update(Long id, TeamEntity newEntity) {
		TeamEntity updatedEntity = null;
		try {
			updatedEntity = teamRepository.findOneById(id);

			updatedEntity.setFullName(newEntity.getFullName());
			updatedEntity.setShortName(newEntity.getShortName());
			updatedEntity.setDescription(newEntity.getDescription());
			updatedEntity.setCreator(newEntity.getCreator());
			updatedEntity.setCompetition(newEntity.getCompetition());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = teamRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public TeamEntity delete(Long id) {
		TeamEntity deletedEntity = null;
		try {
			deletedEntity = teamRepository.findOneById(id);
			teamRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = teamRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public TeamEntity findOneById(Long id) {
		TeamEntity foundEntity = null;
		try {
			foundEntity = teamRepository.getOne(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<TeamEntity> findAll(Pageable pageable) {
		Collection<TeamEntity> foundEntitys = null;
		try {
			foundEntitys = teamRepository.findAll(pageable).getContent();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

	@Override
	public Collection<TeamEntity> findAll() {
		Collection<TeamEntity> foundEntitys = null;
		try {
			foundEntitys = teamRepository.findAll();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

	@Override
	public Collection<TeamEntity> findByCreatorId(Long creatorId) {
		Collection<TeamEntity> foundEntitys = null;
		try {
			foundEntitys = teamRepository.findByCreatorId(creatorId);
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

	@Override
	public Collection<TeamEntity> findByCompetitionId(Long competitionId) {
		Collection<TeamEntity> foundEntities = null;
		try {
			foundEntities = teamRepository.findByCompetitionId(competitionId);
		} catch (Exception e) {
			return null;
		}
		return foundEntities;
	}

	@Override
	public Collection<TeamEntity> swap(Long team1Id, Long team2Id) {
		Collection<TeamEntity> teams = null;
		try {
			TeamEntity team1 = teamRepository.findOneById(team1Id);
			TeamEntity team2 = teamRepository.findOneById(team2Id);
			CompetitionEntity thisComp = team1.getCompetition();
			Long compId = thisComp.getId();
			
			Long tmp = team1.getSeedNo();
			team1.setSeedNo(team2.getSeedNo());
			team2.setSeedNo(tmp);
			teamRepository.save(team1);
			teamRepository.save(team2);
			
			teams = teamRepository.findByCompetitionId(compId);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return teams;
	}
	
	public String saveTeamPlayersToFile(TeamEntity team, Collection<Player> players) {

		String absFolderPath = null;
		String absFilePath = null;

		try {
			CompetitionEntity comp = team.getCompetition();

			String folder = Const.COMPETITION_FILESYSTEM + Const.COMPETITION_FOLDER_NAMING + comp.getId();
			absFolderPath = fileService.getFileStorageLocation(folder).toString();
			String fileName = Const.COMPETITION_TEAM_PLAYERS_NAMING + team.getId()
					+ Const.FILE_EXTENDED;
			absFilePath = absFolderPath + "\\" + fileName;
			absFilePath = fileService.saveObjectToFile(players, absFilePath);

			System.out.println("TeamAPI: saveTeamConfig: absFileName: \n[" + absFilePath + "]");
		} catch (Exception e) {
		}

		return absFilePath;
	}

	@SuppressWarnings("unchecked")
	public Collection<Player> getTeamPlayerFromFile(Long competitionId, Long teamId) {

		String absFolderPath = null;
		String absFilePath = null;
		ArrayList<Player> players = new ArrayList<>();

		try {

			String folder = Const.COMPETITION_FILESYSTEM + Const.COMPETITION_FOLDER_NAMING + competitionId;
			absFolderPath = fileService.getFileStorageLocation(folder).toString();
			String fileName = Const.COMPETITION_TEAM_PLAYERS_NAMING + teamId
					+ Const.FILE_EXTENDED;
			absFilePath = absFolderPath + "\\" + fileName;
			players = (ArrayList<Player>) fileService.getObjectFromFile(absFilePath);

		} catch (Exception e) {
		}

		return players;
	}
	
	@Override
	public Collection<TeamEntity> findByCompetitionIdAndStatus(Long competitionId, String status) {
		Collection<TeamEntity> foundEntities = null;
		try {
			foundEntities = teamRepository.findByCompetitionIdAndStatus(competitionId, status);
		} catch (Exception e) {
			return null;
		}
		return foundEntities;
	}
	
	@Override
	public Long getMaxSeedNoByCompetitionId(Long competitionId) {
		Long maxSeedNo = 0l;
		try {
			maxSeedNo = teamRepository.getMaxSeedNoByCompetitionId(competitionId);
			if (maxSeedNo == null) {
				return 0l;
			}
		} catch (Exception e) {
		}
		
		return maxSeedNo;
	}
	
	@Override
	public Long countByCompetitionIdAndStatus(Long competitionId, String status) {
		Long totalTeam = 0l;
		try {
			totalTeam = teamRepository.countByCompetitionIdAndStatus(competitionId, status);
			if (totalTeam == null) {
				return 0l;
			}
		} catch (Exception e) {
		}
		
		return totalTeam;
	}
	
	@Override
	public Collection<TeamEntity> findByTournamentIdAndStatus(Long tournamentId, String status) {
		Collection<TeamEntity> foundEntities = null;
		try {
			System.out.println("TeamService: findByTournamentIdAndStatus: start");
			System.out.println("tour: " + tournamentId);
			System.out.println("status: " + status);
			foundEntities = teamRepository.findByTournamentIdAndStatus(tournamentId, status);
			System.out.println("TeamService: findByTournamentIdAndStatus: size " + foundEntities.size());
		} catch (Exception e) {
			System.out.println("TeamService: findByTournamentIdAndStatus: has exception");
			return null;
		}
		System.out.println("TeamService: findByTournamentIdAndStatus: finish");
		return foundEntities;
	}
	
	@Override
	public Long countByCompetitionTournamentIdAndStatus(Long tournamentId, String status) {
		Long totalTeam = 0l;
		try {
			totalTeam = teamRepository.countByCompetitionTournamentIdIdAndStatus(tournamentId, status);
			if (totalTeam == null) {
				return 0l;
			}
		} catch (Exception e) {
		}
		
		return totalTeam;
	}

}