package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.SportDTO;
import doan2020.SportTournamentSupportSystem.entity.SportEntity;

@Component
public class SportConverter {
	
	public SportEntity toEntity(SportDTO dto){
		System.out.println("SportConverter: toEntity: start");
		SportEntity entity = new SportEntity();
		try {
			if (dto.getFullName() != null)
				entity.setFullName(dto.getFullName());
			if (dto.getShortName() != null)
				entity.setShortName(dto.getShortName());
			
			entity.setDescription(dto.getDescription());
			
			
			
			System.out.println("SportConverter: toEntity: no exception");
		}catch (Exception e) {
			System.out.println("SportConverter: toEntity: has exception");
			return null;
		}
		System.out.println("SportConverter: toEntity: finish");
		return entity;
	}

	public SportDTO toDTO(SportEntity entity){
		System.out.println("SportConverter: toDTO: finish");
		SportDTO dto = new SportDTO();
		try {
			dto.setId(entity.getId());
			dto.setFullName(entity.getFullName());
			dto.setShortName(entity.getShortName());
			
			dto.setDescription(entity.getDescription());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("SportConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("SportConverter: toDTO: has exception");
			return null;
		}
		
		System.out.println("SportConverter: toDTO: finish");
		return dto;
	}
}
