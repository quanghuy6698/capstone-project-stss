package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.ReportDTO;
import doan2020.SportTournamentSupportSystem.entity.ReportEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;
import doan2020.SportTournamentSupportSystem.service.IUserService;

@Component
public class ReportConverter {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ITournamentService tournamentService;
	
	public ReportEntity toEntity(ReportDTO dto) {
		System.out.println("ReportConverter: toEntity: start");
		ReportEntity entity = new ReportEntity();
		try {
			
			if (dto.getSenderId() != null) {
				Long senderId = dto.getSenderId();
				UserEntity sender = userService.findOneById(senderId);
				entity.setSender(sender);
			}
			
			entity.setSubject(dto.getSubject());

			if (dto.getTournamentId() != null) {
				Long tournamentId = dto.getTournamentId();
				TournamentEntity tournament = tournamentService.findOneById(tournamentId);
				entity.setTournament(tournament);
			}
			
			entity.setContent(dto.getContent());
			
			
			entity.setType(dto.getType());
			System.out.println("ReportConverter: toEntity: no exception");
		} catch (Exception e) {
			System.out.println("ReportConverter: toEntity: has exception");
			return null;
		}
		System.out.println("ReportConverter: toEntity: finish");
		return entity;
	}
	
	public ReportDTO toDTO(ReportEntity entity) {
		System.out.println("ReportConverter: toDTO: start");
		ReportDTO dto = new ReportDTO();
		try {
			dto.setId(entity.getId());
			if (entity.getSender() != null)
				dto.setSenderId(entity.getSender().getId());
			dto.setSubject(entity.getSubject());
			if (entity.getTournament() != null)
				dto.setTournamentId(entity.getTournament().getId());
			dto.setContent(entity.getContent());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			dto.setType(dto.getType());
			System.out.println("ReportConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("ReportConverter: toDTO: has exception");
			return null;
		}
		System.out.println("ReportConverter: toDTO: finish");
		return dto;
	}
}
