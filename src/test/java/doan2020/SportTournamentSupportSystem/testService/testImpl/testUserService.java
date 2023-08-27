package doan2020.SportTournamentSupportSystem.testService.testImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;

import doan2020.SportTournamentSupportSystem.entity.RoleEntity;
import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.repository.RoleRepository;
import doan2020.SportTournamentSupportSystem.repository.UserRepository;
import doan2020.SportTournamentSupportSystem.service.impl.RoleService;
import doan2020.SportTournamentSupportSystem.service.impl.UserService;

@RunWith(SpringRunner.class)
public class testUserService {
	/**
	 * Cách này sử dụng @TestConfiguration Nó chỉ tạo ra Bean trong Context test này
	 * mà thôi Tiết kiệm thời gian hơn khi sử dụng @SpringBootTest (vì phải load hết
	 * Bean lên mà không dùng đến)
	 */
	@TestConfiguration
	public static class testUserServiceConfiguration {

		/*
		 * Tạo ra trong Context một Bean TodoService
		 */
		@Bean
		UserService userService() {
			return new UserService();
		}
		
		@Bean
		RoleService roleService() {
			return new RoleService();
		}
	}

	@MockBean
	UserRepository userRepository;
	
	@MockBean
	RoleRepository roleRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;

	@Before
	public void setUp() {
//		userEntity.setId((long) 1);
//		userEntity.setUsername("Cong");
		RoleEntity roleEntity = new RoleEntity();
		List<UserEntity> entities = new ArrayList<UserEntity>();
		Date date = new Date();
		UserEntity userEntity = new UserEntity((long)1, "a", "a", "a", "a", "a", "a", true, date, "a", "a", "a", "a", "a", roleEntity);
//		System.out.println(entities.size());
		Long id = (long) 1;
		Mockito.when(userRepository.findOneById(id)).thenReturn(userEntity);

//		List<UserEntity> entities = new ArrayList<UserEntity>();

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testCount() throws Exception{
		Long id = (long) 1;
		Assert.assertEquals(id, userService.findOneById(id).getId());
	}

}
