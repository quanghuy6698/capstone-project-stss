package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.FinalStageSettingDTO;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.FinalStageSettingEntity;
import doan2020.SportTournamentSupportSystem.entity.FormatEntity;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.service.ICompetitionService;
import doan2020.SportTournamentSupportSystem.service.IFormatService;

@Component
public class FinalStageSettingConverter {
	
	@Autowired
	private IFormatService formatService;
	
	@Autowired
	private ICompetitionService competitionService;
	
	public FinalStageSettingEntity toEntity(FinalStageSettingDTO dto){
		System.out.println("FinalStageSettingConverter: toEntity: start");
		FinalStageSettingEntity entity = new FinalStageSettingEntity();
		try {
			entity.setHasHomeMatch(dto.isHasHomeMatch());
			if (dto.getFormatId() != null) {
				Long formatId = dto.getFormatId();
				if (dto.getCompetitionId() != null) {
					CompetitionEntity competition = competitionService.findOneById(dto.getCompetitionId());
					entity.setCompetition(competition);;
				}
				FormatEntity format = formatService.findOneById(formatId);
				entity.setFormat(format);
				
				
			}
			System.out.println("FinalStageSettingConverter: toEntity: no exception");
		}catch (Exception e) {
			System.out.println("FinalStageSettingConverter: toEntity: has exception");
			return null;
		}
		System.out.println("FinalStageSettingConverter: toEntity: finish");
		return entity;
	}

	public FinalStageSettingDTO toDTO(FinalStageSettingEntity entity){
		System.out.println("FinalStageSettingConverter: toDTO: finish");
		FinalStageSettingDTO dto = new FinalStageSettingDTO();
		try {
			dto.setId(entity.getId());
			if (entity.getFormat() != null)
				dto.setFormatId(entity.getFormat().getId());
			if (entity.getCompetition() != null)
				dto.setCompetitionId(entity.getCompetition().getId());
			dto.setHasHomeMatch(entity.isHasHomeMatch());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("FinalStageSettingConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("FinalStageSettingConverter: toDTO: has exception");
			return null;
		}
		
		System.out.println("FinalStageSettingConverter: toDTO: finish");
		return dto;
	}
}
