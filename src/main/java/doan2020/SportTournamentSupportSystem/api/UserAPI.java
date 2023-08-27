package doan2020.SportTournamentSupportSystem.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import doan2020.SportTournamentSupportSystem.config.Const;
import doan2020.SportTournamentSupportSystem.converter.PermissionConverter;
import doan2020.SportTournamentSupportSystem.converter.UserConverter;
import doan2020.SportTournamentSupportSystem.dto.PermissionDTO;
import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.PermissionEntity;
import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IPermissionService;
import doan2020.SportTournamentSupportSystem.service.IRoleService;
import doan2020.SportTournamentSupportSystem.service.IUserService;
import doan2020.SportTournamentSupportSystem.service.impl.AzureBlobAdapterService;
import doan2020.SportTournamentSupportSystem.service.impl.FileStorageService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;
import doan2020.SportTournamentSupportSystem.service.impl.VerificationTokenService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserAPI {
	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPermissionService permissionService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private PermissionConverter permissionConverter;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AzureBlobAdapterService azureBlobAdapterService;

	/* get One User */

	@GetMapping("")
	public ResponseEntity<Response> getById(@RequestHeader(value = Const.TOKEN_HEADER, required = false) String jwt,
			@RequestParam(value = "id", required = false) Long id) {
		System.out.println("UserAPI: getById: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		UserEntity user = new UserEntity();
		UserDTO dto = new UserDTO();
		PermissionEntity permissionEntity = new PermissionEntity();
		PermissionDTO permissionDTO = new PermissionDTO();

		try {

			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				user = userService.findOneById(id);
				if (user == null) {// not found
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else {// found

					dto = userConverter.toDTO(user);
					System.out.println("UserAPI: getById: CP1");
					Long curentUserId = -1l;

					try {
						System.out.println("UserAPI: getById: jwt: " + jwt);
						String curentUserName = jwtService.getUserNameFromJwtToken(jwt);
						user = userService.findByUsername(curentUserName);
						curentUserId = user.getId();
					} catch (Exception e) {

					}

					if (curentUserId == id) {
						permissionEntity = permissionService.findOneByName(Const.OWNER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					} else {
						permissionEntity = permissionService.findOneByName(Const.VIEWER);

						permissionDTO = permissionConverter.toDTO(permissionEntity);
					}
					result.put("User", dto);
					config.put("Global", permissionDTO);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("UserAPI: getById: no exception");

		} catch (Exception e) {
			System.out.println("UserAPI: getById: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}

		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: getById: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/getByUsername")
	public ResponseEntity<Response> getByUserName(@RequestParam(value = "username") String username) {
		System.out.println("UserAPI: getByUserName: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		UserEntity user = new UserEntity();
		UserDTO dto = new UserDTO();

		try {
			if (username == null) {// username null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param username");
			} else {// username not null
				user = userService.findByUsername(username);
				if (user == null) {// not found
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else {// found
					dto = userConverter.toDTO(user);
					result.put("User", dto);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("UserAPI: getByUserName: no exception");
		} catch (Exception e) {
			System.out.println("UserAPI: getByUserName: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: getByUserName: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@GetMapping("/getByEmail")
	public ResponseEntity<Response> getByEmail(@RequestParam(value = "email") String email) {
		System.out.println("UserAPI: getByUserName: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		UserEntity user = new UserEntity();
		UserDTO dto = new UserDTO();
		try {
			if (email == null) {// email null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param email");
			} else {// email not null
				user = userService.findByEmail(email);
				if (user == null) {// not found
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Not found");
				} else {// found
					dto = userConverter.toDTO(user);
					result.put("User", dto);
					config.put("Global", 0);
					error.put("MessageCode", 0);
					error.put("Message", "Found");
				}
			}
			System.out.println("UserAPI: getByUserName: no exception");
		} catch (Exception e) {
			System.out.println("UserAPI: getByUserName: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: getByUserName: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/* ---------------- register NEW USER ------------------------ */
	@PostMapping
	public ResponseEntity<Response> createUser(@RequestBody UserDTO userDTO) {
		HttpStatus httpStatus = HttpStatus.OK;
		System.out.println("UserAPI: createUser: start");
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		try {
			UserEntity newUser = userConverter.toEntity(userDTO);

			UserEntity userCheckUsername = userService.findByUsername(newUser.getUsername());
			UserEntity userCheckEmail = userService.findByEmail(newUser.getEmail());

			if (userCheckUsername != null || userCheckEmail != null) {// User exist
				error.put("MessageCode", 1);
				error.put("Message", "User is Exists");
			} else {

				RoleEntity roleEntity = roleService.findOneByName("ROLE_USER");
				if (roleEntity != null)
					newUser.setRole(roleEntity);
				else {
					roleEntity = roleService.findOneById((long) 1);
					newUser.setRole(roleEntity);
				}

				newUser.setStatus("deactive");

				newUser = userService.create(newUser);

				System.out.println("UserAPI: createUser: newUser: " + newUser);

				userDTO = userConverter.toDTO(newUser);

				verificationTokenService.createVerification(newUser.getEmail(), newUser.getUsername());

				result.put("User", userDTO);

				error.put("MessageCode", 0);
				error.put("Message", "Create user successful");
			}
			System.out.println("UserAPI: createUser: no exception");
		} catch (Exception e) {
			System.out.println("UserAPI: createUser: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "Server error");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: createUser: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/* ---------------- Edit Profile User ------------------------ */
	@PutMapping("")
	public ResponseEntity<Response> editUser(@RequestParam(value = "id") Long id, @RequestBody UserDTO dto) {
		System.out.println("UserAPI: editUser: start");
		HttpStatus httpStatus = HttpStatus.OK;
		Response response = new Response();
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		try {
			UserEntity userEntity = new UserEntity();
			if (id != null) {
				userEntity = userConverter.toEntity(dto);
				userEntity = userService.update(id, userEntity);

				result.put("User", userConverter.toDTO(userEntity));
				error.put("MessageCode", 0);
				error.put("Message", "Edit Profile User Successfull");
			} else {
				error.put("MessageCode", 1);
				error.put("Message", "required user id");
			}
			System.out.println("UserAPI: editUser: no exception");
		} catch (Exception ex) {
			System.out.println("UserAPI: editUser: has exception");
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "edit  User fail");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: editUser: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	/* delete one User */
	@DeleteMapping("")
	public ResponseEntity<Response> deleteUser(@RequestParam(value = "id") Long id) {
		Response response = new Response();
		HttpStatus httpStatus = null;
		httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		try {
			if (id != null) {
				userService.delete(id);
				error.put("MessageCode", 0);
				error.put("Message", "Delete User Successfull");
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
				error.put("MessageCode", 1);
				error.put("Message", "required user id");
			}
		} catch (Exception e) {
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "delete  User fail");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/sendMail")
	public ResponseEntity<Response> sendMail(@RequestParam(value = "username") String username) {
		Response response = new Response();
		HttpStatus httpStatus = null;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		httpStatus = HttpStatus.OK;
		UserEntity user = null;

		try {
			user = userService.findByUsername(username);
			if (user == null) {
				result.put("User", null);
				error.put("MessageCode", 1);
				error.put("Message", "User is not exist");
			} else {
				String email = user.getEmail();
				UserDTO dto = userConverter.toDTO(user);
				if (verificationTokenService.createVerification(email, username)) {
					result.put("User", dto);
					error.put("MessageCode", 0);
					error.put("Message", "Sending mail successfully");

				} else {
					result.put("User", null);
					error.put("MessageCode", 1);
					error.put("Message", "Sending mail fail");
				}
			}

		} catch (Exception e) {
			result.put("User", null);
			error.put("MessageCode", 1);
			error.put("Message", "send  Mail fail");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		return new ResponseEntity<Response>(response, httpStatus);

	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody UserDTO userDTO) {
		Response response = new Response();
		HttpStatus httpStatus = null;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);

		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/uploadAvatar")
	public ResponseEntity<Response> uploadAvatar(@RequestBody MultipartFile file, @RequestParam(value = "id") Long id) {
		System.out.println("UserAPI: uploadAvatar: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();

		System.out.println("UserAPI: uploadAvatar: CP1");
		try {
			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				System.out.println("UserAPI: uploadAvatar: CP2");
				String containName = "user-"+String.valueOf(id);
				
				// create container / folder to azure
				azureBlobAdapterService.createContainer(containName);
				
				// upload image to this container
				String urlImage = azureBlobAdapterService.upload(file, containName, Const.AVATAR).toString();
				
				System.out.println("UserAPI: uploadAvatar: CP3");
				System.out.println("UserAPI: uploadAvatar: fileName: " + urlImage);
				if (urlImage == null) {// urlImage invalid
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Could not store file");
				} else {// fileName valid
					System.out.println(urlImage);
					UserDTO dto = new UserDTO();
					dto.setAvatar(urlImage);
					UserEntity userEntity = userConverter.toEntity(dto);
					userEntity = userService.updateAvatar(id, userEntity);
					System.out.println(userEntity.getAvatar());

					result.put("User", userConverter.toDTO(userEntity));
					error.put("MessageCode", 0);
					error.put("Message", "Upload Avatar and Edit User Successfull");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: uploadAvatar: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

	@PostMapping("/uploadBackground")
	public ResponseEntity<Response> uploadBackground(@RequestBody MultipartFile file, @RequestParam Long id) {

		System.out.println("UserAPI: uploadBackground: start");
		Response response = new Response();
		HttpStatus httpStatus = HttpStatus.OK;
		Map<String, Object> config = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> error = new HashMap<String, Object>();
		System.out.println("UserAPI: uploadAvatar: CP1");
		System.out.println(id);
		System.out.println(file.getOriginalFilename());
		try {
			if (id == null) {// id null
				result.put("User", null);
				config.put("Global", 0);
				error.put("MessageCode", 1);
				error.put("Message", "Required param id");
			} else {// id not null
				System.out.println("UserAPI: uploadAvatar: CP2");
				System.out.println(file);

				String containName = "user-"+String.valueOf(id);

				// create container / folder to azure
				azureBlobAdapterService.createContainer(containName);
				
				// upload image to this container
				String urlImage = azureBlobAdapterService.upload(file, containName, Const.BACKGROUND).toString();

				System.out.println("UserAPI: uploadAvatar: CP3");
				if (urlImage == null) {// fileName invalid
					result.put("User", null);
					config.put("Global", 0);
					error.put("MessageCode", 1);
					error.put("Message", "Could not store file");
				} else {// fileName valid
					System.out.println("check point");
					UserDTO dto = new UserDTO();
					dto.setBackground(urlImage);
					UserEntity userEntity = userConverter.toEntity(dto);
					userEntity = userService.updateBackGround(id, userEntity);

					result.put("User", userConverter.toDTO(userEntity));
					error.put("MessageCode", 0);
					error.put("Message", "Upload background and Edit User Successfull");
				}
			}
			System.out.println("UserAPI: uploadBackground: no exception");
		} catch (Exception e) {
			System.out.println("UserAPI: uploadBackground: has exception");
		}
		response.setError(error);
		response.setResult(result);
		response.setConfig(config);
		System.out.println("UserAPI: uploadBackground: finish");
		return new ResponseEntity<Response>(response, httpStatus);
	}

}
