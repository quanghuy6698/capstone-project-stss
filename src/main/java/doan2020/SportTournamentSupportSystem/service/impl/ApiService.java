
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.ApiEntity;
import doan2020.SportTournamentSupportSystem.repository.ApiRepository;
import doan2020.SportTournamentSupportSystem.service.IApiService;

@Service
public class ApiService implements IApiService {

	@Autowired
	private ApiRepository apiRepository;

	@Override
	public ApiEntity create(ApiEntity apiEntity) {
		ApiEntity newEntity = null;
		try {
			newEntity = apiRepository.save(apiEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public ApiEntity findOneById(Long id) {
		ApiEntity foundEntity = null;
		try {
			foundEntity = apiRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

}