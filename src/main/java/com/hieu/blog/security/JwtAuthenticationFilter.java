package com.hieu.blog.security;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request,

			HttpServletResponse response,

			FilterChain filterChain)

			throws ServletException, IOException {
		
		// 1. get token
		String requestToken = request.getHeader("Authorization");
		//



		// Bear 2352523sdgsg

		System.out.println("req token :"+requestToken);

		String username = null;
		String token = null;

		if (requestToken != null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);
			try {
				username = jwtTokenHelper.getUserNameFromToken(token);

			} catch (IllegalArgumentException e) {

				System.out.println("Unable to get Jwt token");

			} catch (ExpiredJwtException e) {

				System.out.println("Jwt token has expired");

			} catch (MalformedJwtException e) {

				System.out.println("invalid jwt");
			}

		}

		else {

			System.out.println("Jwt token does not begin with Bearer");
		}
		
		// once we get the token, now validate

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtTokenHelper.validateToken(token, userDetails)) {

				// authentication
				UsernamePasswordAuthenticationToken

				usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(

						userDetails,

						null,

						userDetails.getAuthorities());

				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()

						.buildDetails(request));

			//	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {

				System.out.println("Invalid jwt token");

			}
		} else {
			System.out.println("username is null or context is not null");
		}
		filterChain.doFilter(request, response);

	}
}
