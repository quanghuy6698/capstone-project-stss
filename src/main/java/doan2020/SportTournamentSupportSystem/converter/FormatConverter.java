package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.FormatDTO;
import doan2020.SportTournamentSupportSystem.entity.FormatEntity;

@Component
public class FormatConverter {
	
	public FormatEntity toEntity(FormatDTO dto){
		System.out.println("CompetitionFormatConverter: toEntity: start");
		FormatEntity entity = new FormatEntity();
		try {
			if (dto.getName() != null)
				entity.setName(dto.getName());

			entity.setDescription(dto.getDescription());
			
			
			System.out.println("CompetitionFormatConverter: toEntity: no exception");
		}catch (Exception e) {
			System.out.println("CompetitionFormatConverter: toEntity: has exception");
			return null;
		}
		System.out.println("CompetitionFormatConverter: toEntity: finish");
		return entity;
	}

	public FormatDTO toDTO(FormatEntity entity){
		System.out.println("CompetitionFormatConverter: toDTO: finish");
		FormatDTO dto = new FormatDTO();
		try {
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setDescription(entity.getDescription());
			
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("CompetitionFormatConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("CompetitionFormatConverter: toDTO: has exception");
			return null;
		}
		
		System.out.println("CompetitionFormatConverter: toDTO: finish");
		return dto;
	}

}
