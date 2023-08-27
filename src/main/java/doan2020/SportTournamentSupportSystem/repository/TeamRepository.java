<<<<<<< HEAD

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import doan2020.SportTournamentSupportSystem.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long>{
    TeamEntity findOneById(Long id);
    
    Collection<TeamEntity> findByCreatorId(Long creatorId);
    
    Collection<TeamEntity> findByCompetitionId(Long competitionId);
    
    Long countByCompetitionId(Long competitionId);
    
    @Query(value="SELECT MAX(seed_no) FROM teams WHERE competition_id = ?1", nativeQuery = true)
    Long getMaxSeedNoByCompetitionId(Long competitionId);
=======

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
	TeamEntity findOneById(Long id);

	Collection<TeamEntity> findByCreatorId(Long creatorId);

	Collection<TeamEntity> findByCompetitionId(Long competitionId);

	Collection<TeamEntity> findByCompetitionIdAndStatus(Long competitionId, String status);

	@Query(value = "select teams.* from teams left join competitions on teams.competition_id = competitions.id "
			+ "where competitions.tournament_id = ?1 and teams.status=?2", nativeQuery=true)
	Collection<TeamEntity> findByTournamentIdAndStatus(Long tournamentId, String status);

	Long countByCompetitionIdAndStatus(Long competitionId, String status);

	@Query(value = "select count(t.id) from teams t left join competitions c on t.competition_id = c.id "
			+ "where c.tournament_id = ?1 and t.status=?2", nativeQuery = true)
	Long countByCompetitionTournamentIdIdAndStatus(Long tournamentId, String status);

	Long countByCompetitionId(Long competitionId);

	@Query(value = "SELECT MAX(seed_no) FROM teams WHERE competition_id = ?1 and status = \'" + Const.TEAM_STATUS_JOINED
			+ "\'", nativeQuery = true)
	Long getMaxSeedNoByCompetitionId(Long competitionId);
>>>>>>> develop
}