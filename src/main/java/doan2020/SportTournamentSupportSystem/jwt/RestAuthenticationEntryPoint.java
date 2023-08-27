<<<<<<< HEAD
package doan2020.SportTournamentSupportSystem.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;



public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		
		Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "UNAUTHORIZED");
        body.put("message", "account can't access to page");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
        }.getType(), response.getWriter());
	}
}
=======
package doan2020.SportTournamentSupportSystem.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;



public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		
		Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "UNAUTHORIZED");
        body.put("message", "account with this role can't access to page, so you must to login again");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        new Gson().toJson(body, new TypeReference<Map<String, Object>>() {
        }.getType(), response.getWriter());
	}
}
>>>>>>> develop
