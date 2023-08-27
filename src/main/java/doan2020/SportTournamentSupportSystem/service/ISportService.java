
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import doan2020.SportTournamentSupportSystem.entity.SportEntity;

public interface ISportService {
	
	public SportEntity findOneById(Long id);
	
//	public Collection<SportEntity> findAll(Pageable pageable);
	
	public SportEntity create(SportEntity sportEntity);
	
	public SportEntity update(Long id, SportEntity newEntity);
	
	public Collection<SportEntity> findAll();
	
	public SportEntity delete(Long id);
}