
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;
import doan2020.SportTournamentSupportSystem.repository.NotificationRepository;
import doan2020.SportTournamentSupportSystem.service.INotificationService;

@Service
public class NotificationService implements INotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public NotificationEntity create(NotificationEntity notificationEntity) {
		NotificationEntity newEntity = null;
		try {
			newEntity = notificationRepository.save(notificationEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public NotificationEntity update(Long id, NotificationEntity newEntity) {
		NotificationEntity updatedEntity = null;
		try {
			updatedEntity = notificationRepository.findOneById(id);

			updatedEntity.setTitle(newEntity.getTitle());
			updatedEntity.setContent(newEntity.getContent());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = notificationRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public NotificationEntity delete(Long id) {
		NotificationEntity deletedEntity = null;
		try {
			deletedEntity = notificationRepository.findOneById(id);
			notificationRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = notificationRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public NotificationEntity findOneById(Long id) {
		NotificationEntity foundEntity = null;
		try {
			foundEntity = notificationRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<NotificationEntity> findAll(Pageable pageable) {
		Collection<NotificationEntity> foundEntitys = null;
		try {
			foundEntitys = notificationRepository.findAll(pageable).getContent();
		} catch (Exception e) {
			return null;
		}
		return foundEntitys;
	}

}