package com.hieu.blog.service;

import java.util.List;

import com.hieu.blog.payloads.UserDTO;

public interface UserService {
	
	UserDTO registerNewUser(UserDTO user);

	UserDTO createUser(UserDTO user);

	UserDTO updateUser(UserDTO user, Integer userId);

	UserDTO getUserById(Integer id);

	List<UserDTO> getAllUser();

	void deleteUser(Integer userId);
}
