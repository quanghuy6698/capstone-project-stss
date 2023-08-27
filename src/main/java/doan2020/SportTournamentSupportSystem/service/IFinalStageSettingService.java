package doan2020.SportTournamentSupportSystem.service;

import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;

public interface IFinalStageSettingService {
	public FinalStageSettingEntity findOneById(Long id);
	
//	public Collection<FinalStageSettingEntity> findAll(Pageable pageable);
	
	public FinalStageSettingEntity create(FinalStageSettingEntity finalStageSettingEntity);
	
	public FinalStageSettingEntity update(Long id, FinalStageSettingEntity newEntity);
	
//	public Collection<FinalStageSettingEntity> findAll();
	
	public FinalStageSettingEntity delete(Long id);
	
	public FinalStageSettingEntity findByCompetitionId(Long competitionId);
}
