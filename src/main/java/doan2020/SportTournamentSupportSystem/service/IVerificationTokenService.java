
package doan2020.SportTournamentSupportSystem.service;

import java.util.List;

import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.entity.VerificationTokenEntity;

public interface IVerificationTokenService {
	
	public VerificationTokenEntity findOneById(Long id);
	
//	public Collection<VerificationTokenEntity> findAll(Pageable pageable);
	
	public VerificationTokenEntity create(VerificationTokenEntity verificationTokenEntity);
	
	public VerificationTokenEntity update(Long id, VerificationTokenEntity newEntity);
	
//	public Collection<VerificationTokenEntity> findAll();
	
	public VerificationTokenEntity delete(Long id);
	
	public boolean createVerification(String email, String UserName);

	public VerificationTokenEntity verifyEmail(VerificationTokenEntity verificationToken);

	public List<VerificationTokenEntity> findByUserEntityEmailAndUserEntity(String email, UserEntity userEntity);

	public VerificationTokenEntity findOneByToken(String token);
}