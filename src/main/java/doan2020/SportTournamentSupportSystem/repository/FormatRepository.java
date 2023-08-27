
package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.FormatEntity;

public interface FormatRepository extends JpaRepository<FormatEntity, Long>{
    FormatEntity findOneById(Long id);
    
    FormatEntity findByName(String name);
}