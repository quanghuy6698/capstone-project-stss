
package doan2020.SportTournamentSupportSystem.jwt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import doan2020.SportTournamentSupportSystem.service.impl.JwtService;
import doan2020.SportTournamentSupportSystem.service.impl.UserDetailsServiceImpl;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private final static String TOKEN_HEADER = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userService;

//	@Autowired
//	private IUserService userService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println(
				"=============================================NEW REQUEST=====================================================");
		System.out.println(request.getRemoteAddr());
		if (request instanceof HttpServletRequest) {
			String url = ((HttpServletRequest) request).getRequestURL().toString();
			String queryString = ((HttpServletRequest) request).getQueryString();
			System.out.println("URL: " + url + "?" + queryString);

//			if ("POST".equalsIgnoreCase(((HttpServletRequest) request).getMethod())) {
//				System.out.println("Data: ");
//				String test = request.getReader().toString();
//				System.out.println(test);
//				System.out.println("-----------------------------");
//			}
		}

		try {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			System.out.println("=========== 0");
			String authToken = httpRequest.getHeader(TOKEN_HEADER);
			
			if (authToken == null) {
				System.out.println("=========== authToken NULL");
			} else {
				if (jwtService.validateJwtToken(authToken)) {
					System.out.println("=========== validate fail");
				}
			}

			if (authToken != null && jwtService.validateJwtToken(authToken)) {
				System.out.println("=========== 1");
				String username = jwtService.getUserNameFromJwtToken(authToken);
				UserDetails userDetails = userService.loadUserByUsername(username);
				if (userDetails != null) {
					System.out.println("=========== userDetails not NULL");
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);

				} else {
					System.out.println("=========== userDetails NULL");
				}
			}
		} catch (Exception e) {
			System.out.println("=========== EXCEPTION");
			logger.error("Cannot set user authentication: {}", e);
		}

		chain.doFilter(request, response);
	}
}