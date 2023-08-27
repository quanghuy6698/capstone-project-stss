
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.model.Entity.Player;

public interface ITeamService {

	public TeamEntity findOneById(Long id);

	public Collection<TeamEntity> findAll(Pageable pageable);

	public TeamEntity create(TeamEntity teamEntity);

	public TeamEntity update(Long id, TeamEntity newEntity);

	public Collection<TeamEntity> findAll();

	public TeamEntity delete(Long id);

	public Collection<TeamEntity> findByCreatorId(Long creatorId);
	
	public Collection<TeamEntity> findByCompetitionId(Long creatorId);
	
	public Collection<TeamEntity> swap(Long team1Id, Long team2Id);
	
	public String saveTeamPlayersToFile(TeamEntity team, Collection<Player> players);

	public Collection<Player> getTeamPlayerFromFile(Long competitionId, Long teamId);
	
	public Collection<TeamEntity> findByCompetitionIdAndStatus(Long competitionId, String status);
	
	public Collection<TeamEntity> findByTournamentIdAndStatus(Long tournamentId, String status);
	
	public Long getMaxSeedNoByCompetitionId(Long competitionId);
	
	public Long countByCompetitionIdAndStatus(Long competitionId, String status);
	
	public Long countByCompetitionTournamentIdAndStatus(Long tournamentId, String status);
}