
package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long>{
    NotificationEntity findOneById(Long id);
}