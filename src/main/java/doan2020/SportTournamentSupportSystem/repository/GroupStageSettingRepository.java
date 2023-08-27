package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.GroupStageSettingEntity;

public interface GroupStageSettingRepository extends JpaRepository<GroupStageSettingEntity, Long>{
	public GroupStageSettingEntity findOneById(Long id);
	
	public GroupStageSettingEntity findByCompetitionId(Long competitionId);
}
