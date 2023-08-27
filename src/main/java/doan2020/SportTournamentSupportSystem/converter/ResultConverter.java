package doan2020.SportTournamentSupportSystem.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.ResultDTO;
import doan2020.SportTournamentSupportSystem.entity.MatchEntity;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;
import doan2020.SportTournamentSupportSystem.entity.TeamEntity;
import doan2020.SportTournamentSupportSystem.service.IMatchService;
import doan2020.SportTournamentSupportSystem.service.ITeamService;

@Component
public class ResultConverter {
	
	@Autowired
	private IMatchService matchService;
	
	public ResultEntity toEntity(ResultDTO dto){
		System.out.println("ResultConverter: toEntity: start");
		ResultEntity entity = new ResultEntity();
		try {
			if (dto.getMatchId() != null) {
				Long matchId = dto.getMatchId();
				MatchEntity match = matchService.findOneById(matchId);
				entity.setMatch(match);			
			}
			
			entity.setSetNo(dto.getSetNo());
			entity.setTeam1Score(dto.getTeam1Score());
			entity.setTeam2Score(dto.getTeam2Score());

			
			
			System.out.println("ResultConverter: toEntity: no exception");
		}catch (Exception e) {
			System.out.println("ResultConverter: toEntity: has exception");
			return null;
		}
		System.out.println("ResultConverter: toEntity: finish");
		return entity;
	}

	public ResultDTO toDTO(ResultEntity entity){
		System.out.println("ResultConverter: toDTO: finish");
		ResultDTO dto = new ResultDTO();
		try {
			
			dto.setMatchId(entity.getMatch().getId());
            dto.setSetNo(entity.getSetNo());
            dto.setTeam1Score(entity.getTeam1Score());
            dto.setTeam2Score(entity.getTeam2Score());
			dto.setId(entity.getId());
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("ResultConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("ResultConverter: toDTO: has exception");
			return null;
		}
		
		System.out.println("ResultConverter: toDTO: finish");
		return dto;
	}
}
