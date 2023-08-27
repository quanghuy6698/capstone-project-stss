<<<<<<< HEAD
package doan2020.SportTournamentSupportSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import doan2020.SportTournamentSupportSystem.jwt.CustomAccessDeniedHandler;
import doan2020.SportTournamentSupportSystem.jwt.JwtAuthenticationTokenFilter;
import doan2020.SportTournamentSupportSystem.jwt.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
		http.csrf().ignoringAntMatchers("/**");

		http.authorizeRequests().antMatchers("/login**").permitAll();
		http.authorizeRequests().antMatchers("/register**").permitAll();
		

		http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		.antMatchers(HttpMethod.PUT, "/user**").access("hasRole('ROLE_USER')")
		.antMatchers(HttpMethod.POST, "/tournament**").access("hasRole('ROLE_USER')")
		.antMatchers(HttpMethod.PUT, "/tournament**").access("hasRole('ROLE_USER')")
		.and()
		.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
=======
package doan2020.SportTournamentSupportSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import doan2020.SportTournamentSupportSystem.jwt.CustomAccessDeniedHandler;
import doan2020.SportTournamentSupportSystem.jwt.JwtAuthenticationTokenFilter;
import doan2020.SportTournamentSupportSystem.jwt.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
		http.csrf().ignoringAntMatchers("/**");

		http.authorizeRequests().antMatchers("/login/**").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/user**").permitAll();
		

		http.antMatcher("/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.antMatchers(HttpMethod.PUT, "/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.antMatchers(HttpMethod.DELETE, "/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
		.and()
		.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
>>>>>>> develop
}