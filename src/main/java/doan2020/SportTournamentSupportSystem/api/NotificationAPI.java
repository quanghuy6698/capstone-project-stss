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

import doan2020.SportTournamentSupportSystem.converter.NotificationConverter;
import doan2020.SportTournamentSupportSystem.dto.NotificationDTO;
import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.INotificationService;

@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationAPI {
	
	@Autowired
	private NotificationConverter converter;
	
	@Autowired
	private INotificationService service;
	
	
	@GetMapping("")
	public ResponseEntity<Response> getNotification(@RequestParam(value = "id", required = false) Long id) {
		System.out.println("NotificationAPI: getNotification: no exception");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		NotificationEntity notificationEntity = new NotificationEntity();
		NotificationDTO notificationDTO = new NotificationDTO();
		try {
			if (id == null) { // id null
				result.put("Notification", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else { // id not null
				
				notificationEntity = service.findOneById(id);
				
				if (notificationEntity == null) { // not found
					result.put("Notification", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else { // found
					
					notificationDTO = converter.toDTO(notificationEntity);
					
					result.put("Notification", notificationDTO);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("NotificationAPI: getNotification: no exception");
		} catch (Exception e) {
			System.out.println("NotificationAPI: getNotification: has exception");
			result.put("Notification", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("NotificationAPI: getNotification: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Tao moi mot Notification
	 * 
	 */
	@PostMapping
	@CrossOrigin
	public ResponseEntity<Response> createNotification(@RequestBody NotificationDTO newNotification) {
		System.out.println("NotificationAPI: createNotification: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		NotificationEntity notificationEntity = new NotificationEntity();
		
		try {
			notificationEntity = converter.toEntity(newNotification);
			
			notificationEntity = service.create(notificationEntity);
			
			NotificationDTO dto = converter.toDTO(notificationEntity);

			result.put("Notification", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Notification create successfuly");
			System.out.println("NotificationAPI: createNotification: no exception");
		} catch (Exception e) {
			System.out.println("NotificationAPI: createNotification: has exception");
			result.put("Notification", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("NotificationAPI: createNotification: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/*
	 * Edit mot Notification
	 * 
	 */
	@PutMapping
	@CrossOrigin
	public ResponseEntity<Response> editNotification(
			@RequestBody NotificationDTO notification,
			@RequestParam Long id) {
		System.out.println("NotificationAPI: editNotification: start");
		
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		NotificationEntity notificationEntity = new NotificationEntity();
		
		try {
			notificationEntity = converter.toEntity(notification);
			
			notificationEntity = service.update(id, notificationEntity);
			
			NotificationDTO dto = converter.toDTO(notificationEntity);

			result.put("Notification", dto);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "Notification update successfuly");
			System.out.println("NotificationAPI: editNotification: no exception");
		} catch (Exception e) {
			System.out.println("NotificationAPI: editNotification: has exception");
			result.put("Notification", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setConfig(config);
		response.setResult(result);
		response.setError(error);
		System.out.println("NotificationAPI: editNotification: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
