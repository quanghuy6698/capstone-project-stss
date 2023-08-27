package doan2020.SportTournamentSupportSystem.api;

import java.util.ArrayList;
import java.util.Collection;
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

import doan2020.SportTournamentSupportSystem.converter.NotificationConverter;
import doan2020.SportTournamentSupportSystem.dto.NotificationDTO;
import doan2020.SportTournamentSupportSystem.entity.NotificationEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.INotificationService;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationsAPI {
	
	@Autowired
	private NotificationConverter converter;
	
	@Autowired
	private INotificationService service;
	
	@GetMapping("")
	public ResponseEntity<Response> getAllNotification(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "limit", required = false) Integer limit) {
		System.out.println("NotificationsAPI: getAllNotification: start");

		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		HttpStatus httpStatus = HttpStatus.OK;

		Collection<NotificationEntity> findPage = new ArrayList<>();
		List<NotificationDTO> findPageDTO = new ArrayList<>();

		try {
			if (limit == null)
				limit = 3;
			if (limit == 0)
				limit = 3;
			if (page == null)
				page = 1;

			Pageable pageable = PageRequest.of(page - 1, limit);
			findPage = service.findAll(pageable);

			for (NotificationEntity entity : findPage) {
				NotificationDTO dto = converter.toDTO(entity);
				findPageDTO.add(dto);
			}

			result.put("Notifications", findPageDTO);
			error.put("MessageCode", 0);
			error.put("Message", "Get page successfully");

			System.out.println("NotificationsAPI: getAllNotification: no exception");
		} catch (Exception e) {
			System.out.println("NotificationsAPI: getAllNotification: has exception");
			result.put("Users", findPageDTO);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("NotificationsAPI: getAllNotification: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
