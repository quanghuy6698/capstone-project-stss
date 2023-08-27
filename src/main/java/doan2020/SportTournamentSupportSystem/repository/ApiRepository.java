
package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.ApiEntity;

public interface ApiRepository extends JpaRepository<ApiEntity, Long>{
    ApiEntity findOneById(Long id);
}