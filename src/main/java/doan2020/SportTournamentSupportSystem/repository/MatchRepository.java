
package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.MatchEntity;

public interface MatchRepository extends JpaRepository<MatchEntity, Long>{
    MatchEntity findOneById(Long id);
    
    Collection<MatchEntity> findByCompetitionId(Long competitionId); 
}