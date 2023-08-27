
package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import doan2020.SportTournamentSupportSystem.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>{
    CommentEntity findOneById(Long id);
    
    Collection<CommentEntity> findByPostId(Long postId);
    
    Collection<CommentEntity> findByCreatorId(Long creatorId);
}