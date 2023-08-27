package doan2020.SportTournamentSupportSystem.converter;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.service.IRoleService;
import doan2020.SportTournamentSupportSystem.validator.Validator;

@Component
public class UserConverter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private IRoleService roleService;
	
	public UserEntity toEntity(UserDTO dto) {
		System.out.println("UserConverter: toEntity: start");
		UserEntity entity = new UserEntity();
		try {
			if (dto.getUsername() != null)
				entity.setUsername(dto.getUsername());
				
			if(dto.getPassword() != null) {
//				userPassword = passwordEncoder.encode(dto.getPassword());
				String userPassword = dto.getPassword();
				entity.setPassword(userPassword);
			}
			System.out.println("UserConverter: toEntity: CP");
			
			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setAddress(dto.getAddress());
			entity.setPhoneNumber(dto.getPhoneNumber());
			entity.setGender(dto.getGender());
			
			System.out.println("UserConverter: toEntity: CP1");
			Date dob = validator.formatStringToDate(dto.getDob());
			entity.setDob(dob);
			
			System.out.println("UserConverter: toEntity: CP2");
			if (dto.getEmail() != null)
				entity.setEmail(dto.getEmail());
			
			if (dto.getAvatar() != null)
				entity.setAvatar(dto.getAvatar());
			if (dto.getBackground() != null)
				entity.setBackground(dto.getBackground());
			
			System.out.println("UserConverter: toEntity: CP3");
			if (dto.getRoleId() != null) {
				Long roleId = dto.getRoleId();
				RoleEntity role = roleService.findOneById(roleId);
				entity.setRole(role);
			}
			System.out.println("UserConverter: toEntity: CP4");
			
			
			System.out.println("UserConverter: toEntity: no exception");
		} catch (Exception e) {
			System.out.println("UserConverter: toEntity: has exception");
			return null;
		}
		System.out.println("UserConverter: toEntity: finish");
		return entity;
	}

	public UserDTO toDTO(UserEntity entity) {
		System.out.println("UserConverter: toDTO: start");
		UserDTO dto = new UserDTO();
		System.out.println(entity);
		try {
			dto.setId(entity.getId());
			dto.setUsername(entity.getUsername());
//			dto.setPassword(entity.getPassword());
			dto.setFirstName(entity.getFirstName());
			dto.setLastName(entity.getLastName());
			dto.setAddress(entity.getAddress());
			dto.setPhoneNumber(entity.getPhoneNumber());
			dto.setGender(entity.getGender());
			System.out.println("UserConverter: toDTO: CP1");
			
			String dob = validator.formatDateToString(entity.getDob());
			dto.setDob(dob);
			System.out.println("UserConverter: toDTO: CP2");
			dto.setEmail(entity.getEmail());
			if(entity.getAvatar()!=null)
			dto.setAvatar(entity.getAvatar());
			if(entity.getBackground()!=null)
			dto.setBackground(entity.getBackground());
			
			System.out.println("UserConverter: toDTO: CP3");
			RoleEntity userRole = entity.getRole();
			Long userRoleId = userRole.getId();
			dto.setRoleId(userRoleId);
			
			System.out.println("UserConverter: toDTO: CP4");
			dto.setStatus(entity.getStatus());
			dto.setUrl(entity.getUrl());
			System.out.println("UserConverter: toDTO: no exception");
		} catch (Exception e) {
			System.out.println("UserConverter: toDTO: has exception");
			return null;
		}
		System.out.println("UserConverter: toDTO: finish");
		return dto;
	}
}