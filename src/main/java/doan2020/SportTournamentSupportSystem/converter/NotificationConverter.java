package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.NotificationDTO;
import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;

@Component
public class NotificationConverter {
	
	public NotificationEntity toEntity(NotificationDTO dto) {
		System.out.println("NotificationConverter: toEntity: start");
		NotificationEntity entity = new NotificationEntity();
		try {
			entity.setTitle(dto.getTitle());
			
			entity.setContent(dto.getContent());
			
			
			System.out.println("NotificationConverter: toEntity: no exception");
		} catch (Exception e) {
			System.out.println("NotificationConverter: toEntity: has exception");
			return null;
		}
		System.out.println("NotificationConverter: toEntity: finish");
		return entity;
	}
	
	public NotificationDTO toDTO(NotificationEntity entity) {
		System.out.println("NotificationConverter: toDTO: start");
		NotificationDTO dto = new NotificationDTO();
		try {
			dto.setId(entity.getId());
			dto.setTitle(entity.getTitle());
			dto.setContent(entity.getContent());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("NotificationConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("NotificationConverter: toDTO: has exception");
			return null;
		}
		System.out.println("NotificationConverter: toDTO: finish");
		return dto;
	}
}
