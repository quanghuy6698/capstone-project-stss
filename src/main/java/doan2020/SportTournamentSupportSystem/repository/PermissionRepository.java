
package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>{
    PermissionEntity findOneById(Long id);
    
    PermissionEntity findOneByName(String name);
}