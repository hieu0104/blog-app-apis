package com.hieu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hieu.blog.payloads.JwtAuthRequest;
import com.hieu.blog.payloads.JwtAuthResponse;
import com.hieu.blog.payloads.UserDTO;
import com.hieu.blog.security.JwtTokenHelper;
import com.hieu.blog.service.UserService;

@RestController
@RequestMapping("/auth")

public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken =

				new UsernamePasswordAuthenticationToken(

						username, password);
		try {
			authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {

			System.out.println("Invalid Details !!");

			throw new Exception("Invalid username or password");
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
		UserDTO registerUser = userService.registerNewUser(userDTO);
		return new ResponseEntity<UserDTO>(registerUser, HttpStatus.CREATED);
	}

}
