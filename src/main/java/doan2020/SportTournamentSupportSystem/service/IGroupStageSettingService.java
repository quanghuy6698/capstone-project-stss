package doan2020.SportTournamentSupportSystem.service;

import doan2020.SportTournamentSupportSystem.entity.GroupStageSettingEntity;

public interface IGroupStageSettingService {
	public GroupStageSettingEntity findOneById(Long id);
	
//	public Collection<GroupStageSettingEntity> findAll(Pageable pageable);
	
	public GroupStageSettingEntity create(GroupStageSettingEntity groupStageSettingEntity);
	
	public GroupStageSettingEntity update(Long id, GroupStageSettingEntity newEntity);
	
//	public Collection<GroupStageSettingEntity> findAll();
	
	public GroupStageSettingEntity delete(Long id);
	
	public GroupStageSettingEntity findByCompetitionId(Long competitionId);
}
