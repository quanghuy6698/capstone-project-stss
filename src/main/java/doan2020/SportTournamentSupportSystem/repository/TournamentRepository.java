
package doan2020.SportTournamentSupportSystem.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import doan2020.SportTournamentSupportSystem.entity.TournamentEntity;

public interface TournamentRepository extends JpaRepository<TournamentEntity, Long>{
    TournamentEntity findOneById(Long id);
    Page<TournamentEntity> findByCreatorId(Pageable pageable, Long creatorId);

    @Query(value="SELECT t FROM TournamentEntity t WHERE t.fullName LIKE CONCAT('%',:str,'%') or t.shortName LIKE CONCAT('%',:str,'%')")
    Collection<TournamentEntity> findBySearchString(@Param("str") String searchString);

}