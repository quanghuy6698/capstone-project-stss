package doan2020.SportTournamentSupportSystem.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doan2020.SportTournamentSupportSystem.converter.UserConverter;
import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.entity.VerificationTokenEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IVerificationTokenService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;
import doan2020.SportTournamentSupportSystem.service.impl.UserService;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginAPI {

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserConverter converter;

	@Autowired
	private IVerificationTokenService verificationTokenService;

	@PostMapping
	public ResponseEntity<Response> login(@RequestBody UserDTO user) {
		System.out.println("LoginAPI: login: start");
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		
		String username = user.getUsername();
		String password = user.getPassword();
		
		System.out.println(username);
		System.out.println(password);
		
		try {
			
			UserEntity findUser = userService.findByUsername(username);

			if (findUser == null) { // User is not Exist
				System.out.println("LoginAPI: login: User is not Exist");
				result.put("User", null);
				result.put("Authentication", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "User is not Exist");
			} else { // User exist
				System.out.println("LoginAPI: login: User is Exist");
				System.out.println("LoginAPI: login: found: " + findUser.getId().toString());
				String status = findUser.getStatus();
				if(status==null)
					status = "";
				if (!status.equals("active")) { // User is not active
					System.out.println("LoginAPI: login: User is not active");
					result.put("User", null);
					result.put("Authentication", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "User is not active");
				} else { // User is active
					System.out.println("LoginAPI: login: User is active");
//					boolean checkPW = passwordEncoder.matches(user.getPassword(), findUser.getPassword());
					
					System.out.println("LoginAPI: login: Password: " + findUser.getPassword());
					int checkPW = password.compareTo(findUser.getPassword());
					
//					if (!checkPW) {// password wrong
					if (checkPW != 0) {
						System.out.println("LoginAPI: login: Password wrong");
						result.put("User", null);
						result.put("Authentication", null);
						config.put("Global", 0);
						error.put("MessageCode", 1);
						error.put("Message", "Password wrong");
					} else {// password right
						System.out.println("LoginAPI: login: Password right");
						
						UserDTO userDTO = converter.toDTO(findUser);
						System.out.println(findUser.getId());
						System.out.println(findUser.getUsername());
						String token = jwtService.generateTokenLogin(findUser);

						result.put("User", userDTO);
						result.put("Authentication", token);
						config.put("Global", 0);
						error.put("MessageCode", 0);
						error.put("Message", "Login successfull");
					
						System.out.println("LoginAPI: login: " + findUser.getUsername() + " login success");
					}
				}
			}
			System.out.println("LoginAPI: login: no exception");
		} catch (Exception e) {
			System.out.println("LoginAPI: login: has exception");
			result.put("User", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("LoginAPI: login: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/verify-authentication")
	public ResponseEntity<Response> verifyEmail(@RequestBody Map<String, String> data) {
		System.out.println("LoginAPI - verifyEmail");

		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		try {
			String token = data.get("code");
			VerificationTokenEntity verificationToken = verificationTokenService.findOneByToken(token);
			if (verificationToken == null) {
				error.put("MessageCode", 1);
				error.put("Message", "Invalid token.");
				response.setError(error);
				response.setError(error);
				response.setResult(result);
				response.setConfig(config);
				return new ResponseEntity<Response>(response, httpStatus);
			}
			
			Date in = new Date();
			LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
			Date confirmedDateTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
			
			if (verificationToken.getExpiredDateTime().compareTo(confirmedDateTime) < 0) {
				error.put("MessageCode", 1);
				error.put("Message", "Expired token.");
				response.setError(error);
				response.setError(error);
				response.setResult(result);
				response.setConfig(config);
				return new ResponseEntity<Response>(response, httpStatus);
			}
			
			verificationToken.setConfirmedDateTime(confirmedDateTime);
			verificationToken.setStatus("STATUS_VERIFIED");
			verificationToken.getUser().setStatus("active");

			verificationToken = verificationTokenService.verifyEmail(verificationToken);

			System.out.println("LoginAPI - cp1");
//			result.put("verificationToken", verificationToken);
			config.put("Global", 0);
			error.put("MessageCode", 0);
			error.put("Message", "");
			System.out.println("LoginAPI - cp2");
		} catch (Exception e) {
			System.out.println("LoginAPI - exception");
			result.put("VerificationToken", null);
			config.put("Global", 0);
			error.put("MessageCode", 1);
			error.put("Message", "");
		}

		System.out.println("LoginAPI - cp3");
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("LoginAPI - cp pass");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
