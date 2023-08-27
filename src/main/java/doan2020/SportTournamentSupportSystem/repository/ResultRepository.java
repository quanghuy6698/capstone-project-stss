<<<<<<< HEAD

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.ResultEntity;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
	ResultEntity findOneById(Long id);

	Collection<ResultEntity> findByMatchId(Long matchId);

	Collection<ResultEntity> findByTeamId(Long teamId);
=======

package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.ResultEntity;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
	ResultEntity findOneById(Long id);

	Collection<ResultEntity> findByMatchId(Long matchId);
>>>>>>> develop
}