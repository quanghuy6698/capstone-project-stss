package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;

public interface FinalStageSettingRepository extends JpaRepository<FinalStageSettingEntity, Long>{
	public FinalStageSettingEntity findOneById(Long id);
	
	public FinalStageSettingEntity findByCompetitionId(Long competitionId);
}
