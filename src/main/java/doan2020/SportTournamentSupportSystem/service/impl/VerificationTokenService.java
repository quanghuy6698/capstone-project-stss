
package doan2020.SportTournamentSupportSystem.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.entity.VerificationTokenEntity;
import doan2020.SportTournamentSupportSystem.repository.UserRepository;
import doan2020.SportTournamentSupportSystem.repository.VerificationTokenRepository;
import doan2020.SportTournamentSupportSystem.service.IVerificationTokenService;

@Service
public class VerificationTokenService implements IVerificationTokenService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SendingMailService sendingMailService;

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public VerificationTokenEntity create(VerificationTokenEntity verificationTokenEntity) {
		VerificationTokenEntity newEntity = null;
		try {
			newEntity = verificationTokenRepository.save(verificationTokenEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}

	@Override
	public VerificationTokenEntity update(Long id, VerificationTokenEntity newEntity) {
		VerificationTokenEntity updatedEntity = null;
		try {
			updatedEntity = verificationTokenRepository.findOneById(id);

			updatedEntity.setToken(newEntity.getToken());
			updatedEntity.setExpiredDateTime(newEntity.getExpiredDateTime());
			updatedEntity.setIssuedDateTime(newEntity.getIssuedDateTime());
			updatedEntity.setConfirmedDateTime(newEntity.getConfirmedDateTime());
			updatedEntity.setUser(newEntity.getUser());
			if (newEntity.getStatus() != null) {updatedEntity.setStatus(newEntity.getStatus());}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = verificationTokenRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}
        
		return updatedEntity;
	}

	@Override
	public VerificationTokenEntity delete(Long id) {
		VerificationTokenEntity deletedEntity = null;
		try {
			deletedEntity = verificationTokenRepository.findOneById(id);
			deletedEntity.setStatus("deleted");
			deletedEntity = verificationTokenRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public VerificationTokenEntity findOneById(Long id) {
		VerificationTokenEntity foundEntity = null;
		try {
			foundEntity = verificationTokenRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	

	@Autowired
	public VerificationTokenService(UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository, SendingMailService sendingMailService) {
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.sendingMailService = sendingMailService;
	}

	public boolean createVerification(String email, String username) {
		UserEntity user = userRepository.findByEmailAndUsername(email, username);
		if (user == null) {
			return false;
		}

		List<VerificationTokenEntity> verificationTokens = verificationTokenRepository
				.findByUserEmailAndUser(email, user);
		VerificationTokenEntity verificationToken;
		if (verificationTokens.isEmpty()) {
			verificationToken = new VerificationTokenEntity();
			String token = UUID.randomUUID().toString();
			verificationToken.setToken(token);
			LocalDateTime issuedDateTimeTemp = LocalDateTime.now();
			Date issuedDateTime = Date.from(issuedDateTimeTemp.atZone(ZoneId.systemDefault()).toInstant());
			verificationToken.setIssuedDateTime(issuedDateTime);
			LocalDateTime expiredDateTimetemp = issuedDateTimeTemp.plusDays(7);
			Date expiredDateTime = Date.from(expiredDateTimetemp.atZone(ZoneId.systemDefault()).toInstant());
			verificationToken.setExpiredDateTime(expiredDateTime);
	        String status = "STATUS_PENDING";
	        verificationToken.setStatus(status);
			verificationToken.setUser(user);
			verificationTokenRepository.save(verificationToken);
		} else {
			verificationToken = verificationTokens.get(0);
		}

		sendingMailService.sendVerificationMail(email, verificationToken.getToken(), username, Const.DOMAIN);
		return true;
	}

	public VerificationTokenEntity verifyEmail(VerificationTokenEntity verificationToken) {
		VerificationTokenEntity token = new VerificationTokenEntity();
		try {
			token = verificationTokenRepository.save(verificationToken);
		} catch (Exception e) {
			return null;
		}
		return token;
	}

	public List<VerificationTokenEntity> findByUserEntityEmailAndUserEntity(String email, UserEntity userEntity) {
		List<VerificationTokenEntity> verificationTokens = null;
		try {
			verificationTokens = verificationTokenRepository.findByUserEmailAndUser(email, userEntity);
		} catch (Exception e) {
			return null;
		}
		return verificationTokens;
	}

	public VerificationTokenEntity findOneByToken(String token) {
		VerificationTokenEntity verificationToken = null;
		try {
			verificationToken = verificationTokenRepository.findOneByToken(token);
		} catch (Exception e) {
			return null;
		}
		return verificationToken;
	}
	
}