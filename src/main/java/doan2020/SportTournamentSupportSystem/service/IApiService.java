
package doan2020.SportTournamentSupportSystem.service;

import doan2020.SportTournamentSupportSystem.entity.ApiEntity;

public interface IApiService {
	
	public ApiEntity findOneById(Long id);
	
//	public Collection<ApiEntity> findAll(Pageable pageable);
	
	public ApiEntity create(ApiEntity apiEntity);
	
//	public Collection<ApiEntity> findAll();
}