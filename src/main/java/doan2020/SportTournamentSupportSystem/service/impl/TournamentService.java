
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.CompetitionEntity;
import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;
import doan2020.SportTournamentSupportSystem.repository.CompetitionRepository;
import doan2020.SportTournamentSupportSystem.repository.TournamentRepository;
import doan2020.SportTournamentSupportSystem.service.ITournamentService;

@Service
public class TournamentService implements ITournamentService {

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private CompetitionRepository competitionRepository;

	@Override
	public Long countAll() {
		return tournamentRepository.count();
	}

	@Override
	public TournamentEntity create(TournamentEntity tournamentEntity) {
		TournamentEntity newEntity = null;
		try {

			tournamentEntity.setStatus(Const.TOURNAMENT_STATUS_INITIALIZING);
			newEntity = tournamentRepository.save(tournamentEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public TournamentEntity update(Long id, TournamentEntity newEntity) {
		TournamentEntity updatedEntity = null;
		try {
			updatedEntity = tournamentRepository.findOneById(id);

			updatedEntity.setFullName(newEntity.getFullName());
			updatedEntity.setShortName(newEntity.getShortName());
			updatedEntity.setDescription(newEntity.getDescription());
//			updatedEntity.setCreator(newEntity.getCreator());
			updatedEntity.setOpeningLocation(newEntity.getOpeningLocation());
			updatedEntity.setOpeningTime(newEntity.getOpeningTime());
			updatedEntity.setClosingLocation(newEntity.getClosingLocation());
			updatedEntity.setClosingTime(newEntity.getClosingTime());
			updatedEntity.setDonor(newEntity.getDonor());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity.setCloseRegistrationTime(newEntity.getCloseRegistrationTime());
			updatedEntity.setOpenRegistrationTime(newEntity.getOpenRegistrationTime());
			updatedEntity = tournamentRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public TournamentEntity delete(Long id) {
		TournamentEntity deletedEntity = null;
		try {
			deletedEntity = tournamentRepository.findOneById(id);
			tournamentRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = tournamentRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public TournamentEntity findOneById(Long id) {
		TournamentEntity foundEntity = null;
		try {
			foundEntity = tournamentRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<TournamentEntity> findByCreatorId(Pageable pageable, Long creatorId) {
		Collection<TournamentEntity> findTournaments = null;
		try {
			findTournaments = tournamentRepository.findByCreatorId(pageable, creatorId).getContent();
		} catch (Exception e) {
			return null;
		}
		return findTournaments;
	}

	@Override
	public Collection<TournamentEntity> findAll(Pageable pageable) {
		Collection<TournamentEntity> findTournaments = null;
		try {
			findTournaments = tournamentRepository.findAll(pageable).getContent();
		} catch (Exception e) {
			return null;
		}
		return findTournaments;
	}

	@Override
	public Collection<TournamentEntity> findBySearchString(Pageable pageable, String searchString) {
		List<TournamentEntity> findTournaments = null;
		try {

			findTournaments = (List<TournamentEntity>) tournamentRepository.findBySearchString(searchString);

			System.out
					.println("TournamentService: findBySearchString: findTournaments: size: " + findTournaments.size());

			int start = (int) pageable.getOffset();
			int end = (start + pageable.getPageSize()) > findTournaments.size() ? findTournaments.size()
					: (start + pageable.getPageSize());
			Page<TournamentEntity> pages = new PageImpl<TournamentEntity>(findTournaments.subList(start, end), pageable,
					findTournaments.size());
			findTournaments = pages.getContent();

		} catch (Exception e) {
			return null;
		}
		return findTournaments;
	}

	@Override
	public Long countBySearchString(String searchString) {
		List<TournamentEntity> findTournaments = null;
		try {
			findTournaments = (List<TournamentEntity>) tournamentRepository.findBySearchString(searchString);
		} catch (Exception e) {
			return 0l;
		}
		return new Long(findTournaments.size());
	}

	@Override
	public TournamentEntity updateAvatar(Long id, TournamentEntity newEntity) {
		TournamentEntity updatedEntity = null;
		try {
			updatedEntity = tournamentRepository.findOneById(id);

			updatedEntity.setAvatar(newEntity.getAvatar());
			updatedEntity = tournamentRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public TournamentEntity updateBackground(Long id, TournamentEntity newEntity) {
		TournamentEntity updatedEntity = null;
		try {
			updatedEntity = tournamentRepository.findOneById(id);

			updatedEntity.setBackground(newEntity.getBackground());
			updatedEntity = tournamentRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public Map<String, Object> getOtherInformation(Long id) {
		Map<String, Object> option = new HashMap<String, Object>();
		TournamentEntity thisTournament = tournamentRepository.findOneById(id);
		Collection<CompetitionEntity> competitions = thisTournament.getCompetitions();

		HashSet<String> sportsName = new HashSet<>();

		int countTeam = 0;

		for (CompetitionEntity comp : competitions) {

			sportsName.add(comp.getSport().getFullName());

			countTeam += comp.getTeams().size();
		}
		option.put("sportsName", sportsName);
		option.put("countTeam", countTeam);
		option.put("process", 0);
		return option;
	}

	@Override
	public TournamentEntity updateStatus(TournamentEntity entity, String status) {
		try {

			entity.setStatus(status);
			entity = tournamentRepository.save(entity);
		} catch (Exception e) {
			return null;
		}

		return entity;
	}

}