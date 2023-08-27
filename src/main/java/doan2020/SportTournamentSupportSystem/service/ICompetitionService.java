
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;

public interface ICompetitionService {
	
	public CompetitionEntity findOneById(Long id);
	
	public Collection<CompetitionEntity> findAll(Pageable pageable);
	
	public Collection<CompetitionEntity> findByTournamentId(Long tournamentId);
	
	public Collection<CompetitionEntity> findByTournamentId(Pageable pageable, Long tournamentId);
	
	public Collection<CompetitionEntity> findByTournamentIdAndSportId(Long tournamentId, Long sportId);
	
	public CompetitionEntity create(CompetitionEntity competitionEntity);
	
	public CompetitionEntity update(Long id, CompetitionEntity newEntity);
	
//	public Collection<CompetitionEntity> findAll();
	
	public CompetitionEntity delete(Long id);
	
	public CompetitionEntity updateStatus(CompetitionEntity entity, String status);
}