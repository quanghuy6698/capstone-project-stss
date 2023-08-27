package doan2020.SportTournamentSupportSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationTokenEntity, Long> {
	List<VerificationTokenEntity> findByUserEmailAndUser(String email,UserEntity userEntity);

	VerificationTokenEntity findOneByToken(String token);
	
	VerificationTokenEntity findOneById(Long id);
}
