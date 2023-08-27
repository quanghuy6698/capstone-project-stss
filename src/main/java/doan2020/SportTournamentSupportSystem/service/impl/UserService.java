
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.repository.RoleRepository;
import doan2020.SportTournamentSupportSystem.repository.UserRepository;
import doan2020.SportTournamentSupportSystem.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Long countAll() {
		return userRepository.count();
	}

	@Override
	public UserEntity create(UserEntity userEntity) {
		System.out.println("UserService: create: start");
		UserEntity newEntity = null;
		try {
			newEntity = userRepository.save(userEntity);
			System.out.println("UserService: create: no exception");
		} catch (Exception e) {
			System.out.println("UserService: create: has exception");
		}
		System.out.println("UserService: create: finish");
		return newEntity;
	}

	@Override
	public UserEntity update(Long id, UserEntity newEntity) {
		UserEntity updatedEntity = null;
		try {
			updatedEntity = userRepository.findOneById(id);

			updatedEntity.setUsername(newEntity.getUsername());
//			updatedEntity.setPassword(newEntity.getPassword());
			updatedEntity.setFirstName(newEntity.getFirstName());
			updatedEntity.setLastName(newEntity.getLastName());
			updatedEntity.setAddress(newEntity.getAddress());
			updatedEntity.setPhoneNumber(newEntity.getPhoneNumber());
			updatedEntity.setGender(newEntity.getGender());
			updatedEntity.setDob(newEntity.getDob());
//			updatedEntity.setEmail(newEntity.getEmail());
//			updatedEntity.setAvatar(newEntity.getAvatar());
//			updatedEntity.setBackground(newEntity.getBackground());
			updatedEntity.setRole(newEntity.getRole());
			if (newEntity.getStatus() != null) {
				updatedEntity.setStatus(newEntity.getStatus());
			}
			updatedEntity.setUrl(newEntity.getUrl());
			updatedEntity = userRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public UserEntity updateBackGround(Long id, UserEntity newEntity) {
		UserEntity updatedEntity = null;
		try {
			updatedEntity = userRepository.findOneById(id);
			updatedEntity.setBackground(newEntity.getBackground());
			updatedEntity = userRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public UserEntity updateAvatar(Long id, UserEntity newEntity) {
		UserEntity updatedEntity = null;
		try {
			updatedEntity = userRepository.findOneById(id);
			updatedEntity.setAvatar(newEntity.getAvatar());
			updatedEntity = userRepository.save(updatedEntity);
		} catch (Exception e) {
			return null;
		}

		return updatedEntity;
	}

	@Override
	public UserEntity delete(Long id) {
		UserEntity deletedEntity = null;
		try {
			deletedEntity = userRepository.findOneById(id);
			userRepository.delete(deletedEntity);
//			deletedEntity.setStatus("deleted");
//			deletedEntity = userRepository.save(deletedEntity);
		} catch (Exception e) {
			return null;
		}
		return deletedEntity;
	}

	@Override
	public UserEntity findOneById(Long id) {
		UserEntity foundEntity = null;
		try {
			foundEntity = userRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public UserEntity findByUsername(String username) {
		UserEntity foundEntity = null;
		try {
			foundEntity = userRepository.findByUsername(username);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<UserEntity> findAll(Pageable pageable) {
		Collection<UserEntity> userEntities = null;
		try {
			userEntities = userRepository.findAll(pageable).getContent();
		} catch (Exception e) {
			return null;
		}
		return userEntities;
	}

	@Override
	public Collection<UserEntity> findAll() {
		Collection<UserEntity> userEntities = null;
		try {
			userEntities = userRepository.findAll();
		} catch (Exception e) {
			return null;
		}
		return userEntities;
	}

	@Override
	public UserEntity findByEmail(String email) {
		UserEntity foundEntity = null;
		try {
			foundEntity = userRepository.findByEmail(email);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public Collection<UserEntity> findBySearchString(Pageable pageable, String searchString) {
		List<UserEntity> findUsers = null;
		try {

			findUsers = (List) userRepository.findBySearchString(searchString);

			int start = (int) pageable.getOffset();
			int end = (start + pageable.getPageSize()) > findUsers.size() ? findUsers.size()
					: (start + pageable.getPageSize());
			Page<UserEntity> pages = new PageImpl<UserEntity>(findUsers.subList(start, end), pageable,
					findUsers.size());
			findUsers = pages.getContent();

		} catch (Exception e) {
			return null;
		}
		return findUsers;
	}

	@Override
	public Collection<UserEntity> findByRoleId(Pageable pageable, Long roleId) {
		List<UserEntity> findUsers = null;
		try {
			findUsers = userRepository.findByRoleId(pageable, roleId).getContent();
		} catch (Exception e) {
			return null;
		}
		return findUsers;
	}

	@Override
	public int countByRoleId(Long roleId) {
		int count = 0;
		try {
			count = userRepository.countByRoleId(roleId);
		} catch (Exception e) {
			return 0;
		}
		return count;
	}

	@Override
	public Long countBySearchString(String searchString) {
		List<UserEntity> findUsers = null;
		try {
			findUsers = (List<UserEntity>) userRepository.findBySearchString(searchString);
		} catch (Exception e) {
			return 0l;
		}
		return new Long(findUsers.size());
	}

	@Override
	public UserEntity updateStatus(UserEntity entity, String status) {
		try {

			entity.setStatus(status);
			entity = userRepository.save(entity);
		} catch (Exception e) {
			return null;
		}

		return entity;
	}

	@Override
	public UserEntity updateRole(UserEntity entity, String roleName) {
		try {

			RoleEntity roleEntity = roleRepository.findOneByName(roleName);
			if (roleEntity != null)
				entity.setRole(roleEntity);
			
			entity = userRepository.save(entity);
		} catch (Exception e) {
			return null;
		}

		return entity;
	}
	
	@Override
	public Collection<UserEntity> findBySearchStringAndStatus(Pageable pageable, String searchString, String status) {
		ArrayList<UserEntity> findUsers = null;
		try {

			findUsers = (ArrayList<UserEntity>) userRepository.findBySearchStringAndStatus(searchString, status);

			int start = (int) pageable.getOffset();
			int end = (start + pageable.getPageSize()) > findUsers.size() ? findUsers.size()
					: (start + pageable.getPageSize());
			Page<UserEntity> pages = new PageImpl<UserEntity>(findUsers.subList(start, end), pageable,
					findUsers.size());
			findUsers = (ArrayList<UserEntity>) pages.getContent();

		} catch (Exception e) {
			return null;
		}
		return findUsers;
	}
	
	@Override
	public Long countBySearchStringAndStatus(String searchString, String status) {
		List<UserEntity> findUsers = null;
		try {
			findUsers = (List<UserEntity>) userRepository.findBySearchStringAndStatus(searchString, status);
		} catch (Exception e) {
			return 0l;
		}
		return new Long(findUsers.size());
	}
	
	@Override
	public Long countAllByRoleId(Long roleId) {
		return (long)userRepository.countByRoleId(roleId);
	}

}