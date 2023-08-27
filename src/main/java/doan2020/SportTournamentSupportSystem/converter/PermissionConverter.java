package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;

@Component
public class PermissionConverter {
	
	public PermissionEntity toEntity(PermissionDTO dto) {
		System.out.println("PermissionConverter: toEntity: start");
		PermissionEntity entity = new PermissionEntity();
		try {
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setCanEdit(dto.getCanEdit());
			entity.setCanDelete(dto.getCanDelete());
			
			
			System.out.println("PermissionConverter: toEntity: no exception");
		} catch (Exception e) {
			System.out.println("PermissionConverter: toEntity: has exception");
			return null;
		}
		System.out.println("PermissionConverter: toEntity: finish");
		return entity;
	}
	
	public PermissionDTO toDTO(PermissionEntity entity) {
		System.out.println("PermissionConverter: toDTO: start");
		System.out.println("PermissionConverter: toDTO: entity: " + entity);
		PermissionDTO dto = new PermissionDTO();
		try {
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setDescription(entity.getDescription());
			dto.setCanEdit(entity.getCanEdit());
			dto.setCanDelete(entity.getCanDelete());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("PermissionConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("PermissionConverter: toDTO: has exception");
			return null;
		}
		System.out.println("PermissionConverter: toDTO: finish");
		return dto;
	}
}
