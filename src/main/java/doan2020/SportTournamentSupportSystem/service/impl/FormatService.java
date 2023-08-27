
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.FormatEntity;
import doan2020.SportTournamentSupportSystem.repository.FormatRepository;
import doan2020.SportTournamentSupportSystem.service.IFormatService;

@Service
public class FormatService implements IFormatService {

	@Autowired
	private FormatRepository formatRepository;

	@Override
	public FormatEntity create(FormatEntity formatEntity) {
		FormatEntity newEntity = null;
		try {
			newEntity = formatRepository.save(formatEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}


	@Override
	public FormatEntity findOneById(Long id) {
		FormatEntity foundEntity = null;
		try {
			foundEntity = formatRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<FormatEntity> findAll() {
		Collection<FormatEntity> foundEntitys = null;
		try {
			foundEntitys = formatRepository.findAll();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}
	
	@Override
	public FormatEntity findByName(String name) {
		FormatEntity foundEntity = null;
		try {
			foundEntity = formatRepository.findByName(name);
		} catch (Exception e) {
		}
		return foundEntity;
	}

}