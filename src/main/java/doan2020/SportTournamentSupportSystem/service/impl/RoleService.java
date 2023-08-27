
package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.repository.RoleRepository;
import doan2020.SportTournamentSupportSystem.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleEntity create(RoleEntity roleEntity) {
		RoleEntity newEntity = null;
		try {
			newEntity = roleRepository.save(roleEntity);
		} catch (Exception e) {
			return null;
		}
		return newEntity;
	}


	@Override
	public RoleEntity findOneById(Long id) {
		RoleEntity foundEntity = null;
		try {
			foundEntity = roleRepository.findOneById(id);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

	@Override
	public RoleEntity findOneByName(String name) {
		RoleEntity foundEntity = null;
		try {
			foundEntity = roleRepository.findOneByName(name);
		} catch (Exception e) {
			return null;
		}
		return foundEntity;
	}

}