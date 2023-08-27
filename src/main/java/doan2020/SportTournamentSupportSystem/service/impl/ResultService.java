
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.repository.ResultRepository;
import doan2020.SportTournamentSupportSystem.service.IResultService;

@Service
public class ResultService implements IResultService {

	@Autowired
	private ResultRepository resultRepository;

	@Override
	public ResultEntity create(ResultEntity resultEntity) {
		ResultEntity newEntity = null;
		try {
			newEntity = resultRepository.save(resultEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public ResultEntity update(Long id, ResultEntity newEntity) {
		ResultEntity updatedEntity = null;
		try {
			updatedEntity = resultRepository.findOneById(id);

			updatedEntity.setMatch(newEntity.getMatch());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity.setSetNo(newEntity.getSetNo());
			updatedEntity.setTeam1Score(newEntity.getTeam1Score());
			updatedEntity.setTeam2Score(newEntity.getTeam2Score());
			updatedEntity = resultRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public ResultEntity delete(Long id) {
		ResultEntity deletedEntity = null;
		try {
			deletedEntity = resultRepository.findOneById(id);
			resultRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = resultRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public ResultEntity findOneById(Long id) {
		ResultEntity foundEntity = null;
		try {
			foundEntity = resultRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<ResultEntity> findAll(Pageable pageable) {
		Collection<ResultEntity> foundEntitys = null;
		try {
			foundEntitys = resultRepository.findAll(pageable).getContent();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

	@Override
	public Collection<ResultEntity> findByMatchId(Long matchId) {
		Collection<ResultEntity> foundEntitys = null;
		try {
			foundEntitys = resultRepository.findByMatchId(matchId);
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

}