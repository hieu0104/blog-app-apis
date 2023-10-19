package com.hieu.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hieu.blog.entities.User;
import com.hieu.blog.exceptions.ResourceNotFoundException;
import com.hieu.blog.repositories.UserRepository;

import jakarta.websocket.server.ServerEndpoint;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user from database by username
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "emai: " + username, 0));

		return user;
	}

}
