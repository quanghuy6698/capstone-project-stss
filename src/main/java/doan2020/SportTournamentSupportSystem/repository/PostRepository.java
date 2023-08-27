
package doan2020.SportTournamentSupportSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long>{
    PostEntity findOneById(Long id);
    
    Page<PostEntity> findByTournamentId(Pageable pageable, Long tournamentId);
    
    Page<PostEntity> findByCreatorId(Pageable pageable, Long creatorId);
}