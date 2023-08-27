
package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>{
    PlayerEntity findOneById(Long id);
    
    Collection<PlayerEntity> findByTeamId(Long teamId);
}