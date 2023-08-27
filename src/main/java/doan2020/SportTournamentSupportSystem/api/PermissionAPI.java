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

import doan2020.SportTournamentSupportSystem.converter.PermissionConverter;
import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IPermissionService;

@RestController
@CrossOrigin
@RequestMapping("/permission")
public class PermissionAPI {
	
	@Autowired
	private PermissionConverter converter;
	
	@Autowired
	private IPermissionService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getPermission(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("PermissionAPI: getPermission: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		PermissionEntity permissionEntity = new PermissionEntity();
		PermissionDTO permissionDTO = new PermissionDTO();
		try {
			if (id == null) { // id null
				result.put("Permission", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				permissionEntity = service.findOneById(id);
				
				if (permissionEntity == null) { // not found
					result.put("Permission", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					permissionDTO = converter.toDTO(permissionEntity);
					
					result.put("Permission", permissionDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("PermissionAPI: getPermission: no exception");
		} catch (Exception e) {
			System.out.println("PermissionAPI: getPermission: has exception");
			result.put("Permission", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("PermissionAPI: getPermission: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
