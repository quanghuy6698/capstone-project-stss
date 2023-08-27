
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import doan2020.SportTournamentSupportSystem.entity.ReportEntity;

public interface IReportService {
	
	public ReportEntity findOneById(Long id);
	
	public Collection<ReportEntity> findAll(Pageable pageable);
	
	public ReportEntity create(ReportEntity reportEntity);
	
	public ReportEntity update(Long id, ReportEntity newEntity);
	
//	public Collection<ReportEntity> findAll();
	
	public ReportEntity delete(Long id);
	
	public Collection<ReportEntity> findBySenderId(Pageable pageable,Long senderId);
    
	public Collection<ReportEntity> findByTournamentId(Pageable pageable,Long tournamentId);
	
	public Collection<ReportEntity> findByTournamentIdAndType(Pageable pageable,Long tournamentId, String type);
	
	public int countReports();
	
	public Collection<ReportEntity> findByType(Pageable pageable,String type);
	
	public Collection<ReportEntity> findByType(String type);
}