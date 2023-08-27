package doan2020.SportTournamentSupportSystem.testApi;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import doan2020.SportTournamentSupportSystem.api.LoginAPI;
import doan2020.SportTournamentSupportSystem.converter.UserConverter;
import doan2020.SportTournamentSupportSystem.dto.UserDTO;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.repository.UserRepository;
import doan2020.SportTournamentSupportSystem.response.Response;
import doan2020.SportTournamentSupportSystem.service.IVerificationTokenService;
import doan2020.SportTournamentSupportSystem.service.impl.JwtService;
import doan2020.SportTournamentSupportSystem.service.impl.LoginService;
import doan2020.SportTournamentSupportSystem.service.impl.UserService;

@RunWith(SpringRunner.class)
public class TestLoginAPI {
	
	/*
	Các Bean được tạo bởi @TestConfiguration chỉ tồn tại trong môi trường Test
	Cần Bean gì thì tự tạo ra trong @TestConfiguration
	*/
	@TestConfiguration
	public static class testloginAPIConfiguration {
		
		@Bean
		LoginService loginService() {
			return new LoginService();
		}
		
		@Bean
		BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		JwtService jwtService() {
			return new JwtService();
		}
		
		@Bean
		UserService userService() {
			return new UserService();
		}

		@Bean
		UserConverter converter() {
			return new UserConverter();
		}
		
		@Bean
		LoginAPI loginApi() {
			return new LoginAPI();
		}
	}
	
	@MockBean
	LoginService loginService;
	
	@MockBean
	BCryptPasswordEncoder passwordEncoder;

	@MockBean
	JwtService jwtService;

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	UserService userService;

	@MockBean
	UserConverter converter;

	@MockBean
	IVerificationTokenService verificationTokenService;
	
	@Autowired
	LoginAPI loginApi;
	
	private UserEntity userEntity;
	private UserDTO userDto;
	
	/*
	 setup trước khi thực hiện test
	 */
	@Before
	public void setUp() {
		Date returnUserDob = new Date(2020 - 1900, 6, 12);
		Date returnUserCreatedDate = new Date(2020 - 1900, 6, 12);
		Date returnUserModifiedDate = new Date(2020 - 1900, 6, 12);
		
		userEntity = new UserEntity((long)1, "Cong", "123456", 
				"Do Van", "Cong", "Thanh Xuan, Ha Noi", 
				"12345678", true, returnUserDob, 
				"cong123@gmail.com", "ava1.jpg", "background1.jpg", 	
				"active", "stss.com/user/abcdef", null);
		userDto = new UserDTO((long)1, "Cong", "123456", 
				"Do Van", "Cong", "Thanh Xuan, Ha Noi", 
				"12345678", true, "1998-01-01", 
				"cong123@gmail.com", "ava1.jpg", "background1.jpg", 
				(long)2, "active", "stss.com/user/abcdef");
		/*
		Giả lập kết quả trả về của hàm findByUsername trong userService
		 */
		Mockito.when(userService.findByUsername("Cong")).thenReturn(userEntity);
		Mockito.when(jwtService.generateTokenLogin(userEntity)).thenReturn("tempToken");
		Mockito.when(converter.toDTO(userEntity)).thenReturn(userDto);
		Mockito.when(userService.findByUsername("Thanh")).thenReturn(null);
		
		
		MockitoAnnotations.initMocks(this);
	}
	
	/*
	 * Verify kết quả trả về của hàm login trong login API
	 */
	@Test
	public void testLogin() {
		//phần data test (thay đổi theo các test case tương ứng)
		String username = "Cong";
		String password = "123456";
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		
		//phần expected result
		HttpStatus expectedHttpStatus = HttpStatus.OK;
		String expectedMessage = "Login successfull";
		int expectedConfigGlobal = 0;
		
		//phần execute test
		ResponseEntity<Response> response = loginApi.login(user);
		
		HttpStatus actualHttpStatus = response.getStatusCode();
		String actualMessage = (String)response.getBody().getError().get("Message");
		int actualConfigGlobal = (int)response.getBody().getConfig().get("Global");
		UserDTO actualUser = (UserDTO)response.getBody().getResult().get("User");
		String actualToken = (String)response.getBody().getResult().get("Authentication");
		
		Assert.assertEquals(expectedHttpStatus, actualHttpStatus);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedConfigGlobal, actualConfigGlobal);
		Assert.assertEquals(userDto, actualUser);
		Assert.assertEquals("tempToken", actualToken);
	}
	
	@Test
	public void testLoginCaseUserNotExist() {
		//phần data test (thay đổi theo các test case tương ứng)
		String username = "Thanh";
		String password = "123456";
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		
		//phần expected result
		HttpStatus expectedHttpStatus = HttpStatus.OK;
		String expectedMessage = "User is not Exist";
		int expectedConfigGlobal = 0;
		
		//phần execute test
		ResponseEntity<Response> response = loginApi.login(user);
		
		HttpStatus actualHttpStatus = response.getStatusCode();
		String actualMessage = (String)response.getBody().getError().get("Message");
		int actualConfigGlobal = (int)response.getBody().getConfig().get("Global");
		UserDTO actualUser = (UserDTO)response.getBody().getResult().get("User");
		String actualToken = (String)response.getBody().getResult().get("Authentication");
		
		Assert.assertEquals(expectedHttpStatus, actualHttpStatus);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedConfigGlobal, actualConfigGlobal);
		Assert.assertEquals(null, actualUser);
		Assert.assertEquals(null, actualToken);
	}
	/*
	@Test
	public void testLoginCaseUserUnactive() {
		//phần data test (thay đổi theo các test case tương ứng)
		String username = "Cong";
		String password = "123456";
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		
		//phần expected result
		HttpStatus expectedHttpStatus = HttpStatus.OK;
		String expectedMessage = "Login successfull";
		int expectedConfigGlobal = 0;
		
		//phần execute test
		ResponseEntity<Response> response = loginApi.login(user);
		
		HttpStatus actualHttpStatus = response.getStatusCode();
		String actualMessage = (String)response.getBody().getError().get("Message");
		int actualConfigGlobal = (int)response.getBody().getConfig().get("Global");
		System.out.println("____"+actualMessage);
		Assert.assertEquals(expectedHttpStatus, actualHttpStatus);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedConfigGlobal, actualConfigGlobal);
	}
	
	
	@Test
	public void testLoginCaseIncorrectPassword() {
		//phần data test (thay đổi theo các test case tương ứng)
		String username = "Cong";
		String password = "123456";
		UserDTO user = new UserDTO();
		user.setUsername(username);
		user.setPassword(password);
		
		//phần expected result
		HttpStatus expectedHttpStatus = HttpStatus.OK;
		String expectedMessage = "Login successfull";
		int expectedConfigGlobal = 0;
		
		//phần execute test
		ResponseEntity<Response> response = loginApi.login(user);
		
		HttpStatus actualHttpStatus = response.getStatusCode();
		String actualMessage = (String)response.getBody().getError().get("Message");
		int actualConfigGlobal = (int)response.getBody().getConfig().get("Global");
		System.out.println("____"+actualMessage);
		Assert.assertEquals(expectedHttpStatus, actualHttpStatus);
		Assert.assertEquals(expectedMessage, actualMessage);
		Assert.assertEquals(expectedConfigGlobal, actualConfigGlobal);
	}
	*/
}