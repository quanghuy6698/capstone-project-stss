
package doan2020.SportTournamentSupportSystem.service;

import java.util.Collection;

import org.springframework.data.domain.Pageable;

import doan2020.SportTournamentSupportSystem.entity.UserEntity;

public interface IUserService {
	
	public UserEntity findOneById(Long id);
	
	public Collection<UserEntity> findAll(Pageable pageable);
	
	public UserEntity create(UserEntity userEntity);
	
	public UserEntity update(Long id, UserEntity newEntity);
	
	public UserEntity updateAvatar(Long id, UserEntity newEntity);
	
	public UserEntity updateBackGround(Long id, UserEntity newEntity);
	
	public Collection<UserEntity> findAll();
	
	public UserEntity delete(Long id);
	
	public UserEntity findByUsername(String username);
	
	public UserEntity findByEmail(String email);
	
	public Collection<UserEntity> findBySearchString(Pageable pageable, String searchString);
	
	public Long countBySearchString(String searchString);
	
	public Long countAll();
	
	public Long countAllByRoleId(Long roleId);

	public Collection<UserEntity> findByRoleId(Pageable pageable, Long roleId);
	
	public int countByRoleId(Long roleId);
	
	public UserEntity updateStatus(UserEntity newEntity, String status); 
	
	public UserEntity updateRole(UserEntity entity, String roleName);
	
	public Collection<UserEntity> findBySearchStringAndStatus(Pageable pageable, String searchString, String status);
	
	public Long countBySearchStringAndStatus(String searchString, String status);
}