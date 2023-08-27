package doan2020.SportTournamentSupportSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.repository.FinalStageSettingRepository;
import doan2020.SportTournamentSupportSystem.service.IFinalStageSettingService;

@Service
public class FinalStageSettingService implements IFinalStageSettingService{

	@Autowired
	private FinalStageSettingRepository finalStageSettingRepository;
	
	@Override
	public FinalStageSettingEntity create(FinalStageSettingEntity finalStageSettingEntity) {
		FinalStageSettingEntity newEntity = null;
		try {
			newEntity = finalStageSettingRepository.save(finalStageSettingEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}
	
	@Override
	public FinalStageSettingEntity update(Long id, FinalStageSettingEntity newEntity) {
		FinalStageSettingEntity updatedEntity = null;
		try {
			updatedEntity = finalStageSettingRepository.findOneById(id);
			
			updatedEntity.setCompetition(newEntity.getCompetition());
			updatedEntity.setFormat(newEntity.getFormat());
			updatedEntity.setHasHomeMatch(newEntity.isHasHomeMatch());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = finalStageSettingRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public FinalStageSettingEntity delete(Long id) {
		FinalStageSettingEntity deletedEntity = null;
		try {
			deletedEntity = finalStageSettingRepository.findOneById(id);
			finalStageSettingRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = finalStageSettingRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public FinalStageSettingEntity findOneById(Long id) {
		FinalStageSettingEntity foundEntity = null;
		try {
			foundEntity = finalStageSettingRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}
	
	@Override
	public FinalStageSettingEntity findByCompetitionId(Long competitionId) {
		FinalStageSettingEntity foundEntity = null;
		try {
			foundEntity = finalStageSettingRepository.findByCompetitionId(competitionId);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}


}
