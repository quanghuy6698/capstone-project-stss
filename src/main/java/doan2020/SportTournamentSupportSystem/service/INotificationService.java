
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;

public interface INotificationService {
	
	public NotificationEntity findOneById(Long id);
	
	public Collection<NotificationEntity> findAll(Pageable pageable);
	
	public NotificationEntity create(NotificationEntity notificationEntity);
	
	public NotificationEntity update(Long id, NotificationEntity newEntity);
	
//	public Collection<NotificationEntity> findAll();
	
	public NotificationEntity delete(Long id);
	
}