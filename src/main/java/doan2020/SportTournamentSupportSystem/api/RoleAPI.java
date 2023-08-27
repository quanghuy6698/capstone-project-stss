package doan2020.SportTournamentSupportSystem.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.RoleConverter;
import doan2020.SportTournamentSupportSystem.dto.RoleDTO;
import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IRoleService;

@RestController
@CrossOrigin
@RequestMapping("/role")
public class RoleAPI {
	
	@Autowired
	private RoleConverter converter;
	
	@Autowired
	private IRoleService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getRole(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("RoleAPI: getRole: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		RoleEntity roleEntity = new RoleEntity();
		RoleDTO roleDTO = new RoleDTO();
		try {
			if (id == null) { // id null
				result.put("Role", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				roleEntity = service.findOneById(id);
				
				if (roleEntity == null) { // not found
					result.put("Role", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					roleDTO = converter.toDTO(roleEntity);
					
					result.put("Role", roleDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("RoleAPI: getRole: no exception");
		} catch (Exception e) {
			System.out.println("RoleAPI: getRole: has exception");
			result.put("Role", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("RoleAPI: getRole: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Role
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createRole(@RequestBody RoleDTO newRole) {
		System.out.println("RoleAPI: createRole: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		RoleEntity roleEntity = new RoleEntity();
		
		try {
			roleEntity = converter.toEntity(newRole);
			
			roleEntity = service.create(roleEntity);
			
			RoleDTO dto = converter.toDTO(roleEntity);

			result.put("Role", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Role create successfuly");
			System.out.println("RoleAPI: createRole: no exception");
		} catch (Exception e) {
			System.out.println("RoleAPI: createRole: has exception");
			result.put("Role", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("RoleAPI: createRole: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Role
	 * 
	 */
	

}
