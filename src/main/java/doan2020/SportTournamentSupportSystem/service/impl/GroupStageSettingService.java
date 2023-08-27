package doan2020.SportTournamentSupportSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.GroupStageSettingEntity;
import doan2020.SportTournamentSupportSystem.repository.GroupStageSettingRepository;
import doan2020.SportTournamentSupportSystem.service.IGroupStageSettingService;

@Service
public class GroupStageSettingService implements IGroupStageSettingService{

	@Autowired
	private GroupStageSettingRepository groupStageSettingRepository;
	
	@Override
	public GroupStageSettingEntity create(GroupStageSettingEntity groupStageSettingEntity) {
		GroupStageSettingEntity newEntity = null;
		try {
			newEntity = groupStageSettingRepository.save(groupStageSettingEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}
	
	@Override
	public GroupStageSettingEntity update(Long id, GroupStageSettingEntity newEntity) {
		GroupStageSettingEntity updatedEntity = null;
		try {
			updatedEntity = groupStageSettingRepository.findOneById(id);

			updatedEntity.setCompetition(newEntity.getCompetition());
			updatedEntity.setFormat(newEntity.getFormat());
			updatedEntity.setHasHomeMatch(newEntity.isHasHomeMatch());
			updatedEntity.setAdvanceTeamPerTable(newEntity.getAdvanceTeamPerTable());
			updatedEntity.setMaxTeamPerTable(newEntity.getMaxTeamPerTable());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = groupStageSettingRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public GroupStageSettingEntity delete(Long id) {
		GroupStageSettingEntity deletedEntity = null;
		try {
			deletedEntity = groupStageSettingRepository.findOneById(id);
			groupStageSettingRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = groupStageSettingRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public GroupStageSettingEntity findOneById(Long id) {
		GroupStageSettingEntity foundEntity = null;
		try {
			foundEntity = groupStageSettingRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}
	
	@Override
	public GroupStageSettingEntity findByCompetitionId(Long competitionId) {
		GroupStageSettingEntity foundEntity = null;
		try {
			foundEntity = groupStageSettingRepository.findByCompetitionId(competitionId);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}


}
