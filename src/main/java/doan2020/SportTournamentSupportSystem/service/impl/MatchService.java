
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.repository.MatchRepository;
import doan2020.SportTournamentSupportSystem.service.IMatchService;

@Service
public class MatchService implements IMatchService {

	@Autowired
	private MatchRepository matchRepository;

	@Override
	public MatchEntity create(MatchEntity matchEntity) {
		MatchEntity newEntity = null;
		try {
			newEntity = matchRepository.save(matchEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public MatchEntity update(Long id, MatchEntity newEntity) {
		MatchEntity updatedEntity = null;
		try {
			updatedEntity = matchRepository.findOneById(id);

			updatedEntity.setName(newEntity.getName());
//			updatedEntity.setCompetition(newEntity.getCompetition());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity.setLocation(newEntity.getLocation());
			updatedEntity.setTime(newEntity.getTime());
			updatedEntity.setLoser(newEntity.getLoser());
//			updatedEntity.setResults(newEntity.getResults());
			updatedEntity.setWinnner(newEntity.getWinnner());
			updatedEntity.setTeam1(newEntity.getTeam1());
			updatedEntity.setTeam2(newEntity.getTeam2());
			updatedEntity.setTeam1Bonus(newEntity.getTeam1Bonus());
			updatedEntity.setTeam2Bonus(newEntity.getTeam2Bonus());
			updatedEntity = matchRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public MatchEntity delete(Long id) {
		MatchEntity deletedEntity = null;
		try {
			deletedEntity = matchRepository.findOneById(id);
			matchRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = matchRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public MatchEntity findOneById(Long id) {
		MatchEntity foundEntity = null;
		try {
			foundEntity = matchRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}
	
	@Override
	public Collection<MatchEntity> schedule(Long competitionId, Long competitionFormatId) {
		return null;
	}

	@Override
	public Collection<MatchEntity> findAll() {
		Collection<MatchEntity> foundEntitys = null;
		try {
			foundEntitys = matchRepository.findAll();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

	@Override
	public Collection<MatchEntity> findByCompetitionId(Long competitionId) {
		Collection<MatchEntity> foundEntitys = null;
		try {
			foundEntitys = matchRepository.findByCompetitionId(competitionId);
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

}