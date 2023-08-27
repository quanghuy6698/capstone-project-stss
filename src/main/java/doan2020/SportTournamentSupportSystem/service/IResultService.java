
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;
import org.springframework.data.domain.Pageable;
import doan2020.SportTournamentSupportSystem.entity.ResultEntity;

public interface IResultService {
	
	public ResultEntity findOneById(Long id);
	
	public Collection<ResultEntity> findAll(Pageable pageable);
	
	public ResultEntity create(ResultEntity resultEntity);
	
	public ResultEntity update(Long id, ResultEntity newEntity);
	
//	public Collection<ResultEntity> findAll();
	
	public ResultEntity delete(Long id);
	
	Collection<ResultEntity> findByMatchId(Long matchId);
	
	
}