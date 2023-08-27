package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doan2020.SportTournamentSupportSystem.entity.UserEntity;
import doan2020.SportTournamentSupportSystem.repository.UserRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Kiểm tra xem user có tồn tại trong database không?
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserEntity user = userRepository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(user !=null) {
		authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        if (user != null) {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new User(username, /*passwordEncoder.encode*/(user.getPassword()), enabled, accountNonExpired, credentialsNonExpired,
            accountNonLocked, authorities);
        }
		return null;
	}

}
