package doan2020.SportTournamentSupportSystem.converter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.TournamentDTO;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.service.IUserService;
import doan2020.SportTournamentSupportSystem.validator.Validator;

@Component
public class TournamentConverter {

	@Autowired
	IUserService userService;

	@Autowired
	private Validator validator;

	public TournamentEntity toEntity(TournamentDTO dto) {
		System.out.println("TournamentConverter: toEntity: start");
		TournamentEntity entity = new TournamentEntity();
		System.out.println("In toEntity:");
		try {
			if (dto.getFullName() != null)
				entity.setFullName(dto.getFullName());
			if (dto.getShortName() != null)
				entity.setShortName(dto.getShortName());
			entity.setDescription(dto.getDescription());

			if (dto.getCreatorId() != null) {
				Long tournamentCreatorId = dto.getCreatorId();
				UserEntity tournamentCreator = userService.findOneById(tournamentCreatorId);
				entity.setCreator(tournamentCreator);
			}
				
			entity.setOpeningLocation(dto.getOpeningLocation());
			entity.setClosingLocation(dto.getClosingLocation());

			try {
				Date openingTime = validator.formatStringToDate(dto.getOpeningTime());
				entity.setOpeningTime(openingTime);

				
			} catch (Exception e) {
			}
			try {
				Date closingTime = validator.formatStringToDate(dto.getClosingTime());
				entity.setClosingTime(closingTime);
			} catch (Exception e) {
			}

			entity.setDonor(dto.getDonor());

			entity.setAvatar(dto.getAvatar());
			entity.setBackground(dto.getBackground());
			Date closeRegistrationTime = validator.formatStringToDate(dto.getClosingTime());
			entity.setCloseRegistrationTime(closeRegistrationTime);
			Date openRegistrationTime = validator.formatStringToDate(dto.getClosingTime());
			entity.setOpenRegistrationTime(openRegistrationTime);
			System.out.println("TournamentConverter: toEntity: no exception");
		} catch (Exception e) {
			System.out.println("TournamentConverter: toEntity: has exception");
			return null;
		}
		System.out.println("TournamentConverter: toEntity: finish");
		return entity;
	}

	public TournamentDTO toDTO(TournamentEntity entity) {
		System.out.println("TournamentConverter: toDTO: finish");
		TournamentDTO dto = new TournamentDTO();
		try {
			dto.setId(entity.getId());
			dto.setFullName(entity.getFullName());
			dto.setShortName(entity.getShortName());
			dto.setDescription(entity.getDescription());

			UserEntity tournamentCreator = entity.getCreator();
			Long tournamentCreatorId = tournamentCreator.getId();
			dto.setCreatorId(tournamentCreatorId);

			System.out.println("TournamentConverter: toDTO: CP1");

			dto.setOpeningLocation(entity.getOpeningLocation());

			String openingTime = validator.formatDateToString(entity.getOpeningTime());
			dto.setOpeningTime(openingTime);

			dto.setClosingLocation(entity.getClosingLocation());

			String closingTime = validator.formatDateToString(entity.getClosingTime());
			dto.setClosingTime(closingTime);

			dto.setDonor(entity.getDonor());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			dto.setAvatar(entity.getAvatar());
			dto.setBackground(entity.getBackground());
			String closeRegistrationTime = validator.formatDateToString(entity.getCloseRegistrationTime());
			dto.setCloseRegistrationTime(closeRegistrationTime);
			String openRegistrationTime = validator.formatDateToString(entity.getOpenRegistrationTime());
			dto.setOpenRegistrationTime(openRegistrationTime);
			System.out.println("TournamentConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("TournamentConverter: toDTO: has exception");
			return null;
		}

		System.out.println("TournamentConverter: toDTO: finish");
		return dto;
	}

}
