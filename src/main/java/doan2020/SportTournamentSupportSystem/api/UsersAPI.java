package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.UserConverter;
import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IRoleService;
import doan2020.SportTournamentSupportSystem.service.IUserService;
import doan2020.SportTournamentSupportSystem.service.impl.VerificationTokenService;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersAPI {
	@Autowired
	private IUserService service;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private UserConverter converter;

	/* ---------------- GET ALL USER ------------------------ */
	@GetMapping()
	public ResponseEntity<Response> getAllUser(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("UsersAPI: getAllUser: start");
		
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		HttpStatus httpStatus = HttpStatus.OK;
		
		List<UserEntity> findPage = new ArrayList<>();
		List<UserDTO> findPageDTO = new ArrayList<>();
		
		try {
			if (limit == null)
				limit = 3;
			if (limit == 0)
				limit = 3;
			if (page == null)
				page = 1;

			Pageable pageable = PageRequest.of(page - 1, limit);
			findPage = (List<UserEntity>) service.findByRoleId(pageable, 2l);
			
			
			for (UserEntity entity : findPage) {
				UserDTO dto = converter.toDTO(entity);
				findPageDTO.add(dto);
			}
			
			long total = service.countByRoleId(2l);
			long totalPage = total / limit;
			if (total % limit != 0) {
				totalPage++;
			}
			result.put("TotalPage", totalPage);
			
			result.put("Users", findPageDTO);
			error.put("MessageCode", 0);
			error.put("Message", "Get page successfully");
			
			System.out.println("UsersAPI: getAllUser: no exception");
		} catch (Exception e) {
			System.out.println("UsersAPI: getAllUser: has exception");
			result.put("Users", findPageDTO);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UsersAPI: getAllUser: finish");
		return new ResponseEntity<Response>(response, httpStatus);

	}
	
	@GetMapping("/getByRoleId")
	public ResponseEntity<Response> getByRoleId(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "roleId") Long roleId) {
		System.out.println("UsersAPI: getByRoleId: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		List<UserEntity> entities = new ArrayList<UserEntity>();
		
		if (limit == null || limit <= 0)
			limit = 10;
		
		if (page == null || page <= 0)
			page = 1;
		
		if (roleId == null) {// roleId null
			result.put("Users", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param roleId");
		} else {//searchString not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<UserEntity>) service.findByRoleId(pageable, roleId);
				
				int totalPage = 0;
				
				int totalEntity = service.countByRoleId(roleId);
				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;
				
				for (UserEntity entity: entities) {
					UserDTO dto = converter.toDTO(entity);
					dtos.add(dto);
				}
				
				result.put("TotalPage", totalPage);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("UsersAPI: getByRoleId: no exception");
			} catch (Exception e) {
				System.out.println("UsersAPI: getByRoleId: has exception");
				result.put("TotalPage", null);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}
			
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("UsersAPI: getByRoleId: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
	
	@GetMapping("/getBySearchString")
	public ResponseEntity<Response> getBySearchString(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "searchString") String searchString) {
		System.out.println("UsersAPI: getBySearchString: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		List<UserEntity> entities = new ArrayList<UserEntity>();
		
		if (limit == null || limit <= 0)
			limit = 10;
		
		if (page == null || page <= 0)
			page = 1;
		
		if (searchString == null) {// searchString null
			result.put("Users", dtos);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Required param searchString");
		} else {//searchString not null
//			Sort sortable = Sort.by("id").ascending();
			try {
				Pageable pageable = PageRequest.of(page - 1, limit);
				entities = (List<UserEntity>) service.findBySearchString(pageable, searchString);
				
				Long totalPage = 0l;

				Long totalEntity = service.countBySearchString(searchString);

				totalPage = totalEntity / limit;
				if (totalEntity % limit != 0)
					totalPage++;
				
				for (UserEntity entity: entities) {
					UserDTO dto = converter.toDTO(entity);
					dtos.add(dto);
				}
				
				result.put("TotalPage", totalPage);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Found");
				System.out.println("UsersAPI: getBySearchString: no exception");
			} catch (Exception e) {
				System.out.println("UsersAPI: getBySearchString: has exception");
				result.put("TotalPage", null);
				result.put("Users", dtos);
				config.put("Global", 0);
				error.put("MessageCode", 0);
				error.put("Message", "Server error");
			}
			
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("UsersAPI: getBySearchString: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}
	
}
