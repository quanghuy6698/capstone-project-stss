<<<<<<< HEAD

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.SportEntity;

public interface SportRepository extends JpaRepository<SportEntity, Long>{
    SportEntity findOneById(Long id);
    
    Collection<SportEntity> findByScoringUnitId(Long scoringUnitId);
=======

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.SportEntity;

public interface SportRepository extends JpaRepository<SportEntity, Long>{
    SportEntity findOneById(Long id);
    
>>>>>>> develop
}