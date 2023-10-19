package com.hieu.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hieu.blog.config.AppConstants;
import com.hieu.blog.entities.Role;
import com.hieu.blog.entities.User;
import com.hieu.blog.exceptions.ResourceNotFoundException;
import com.hieu.blog.payloads.UserDTO;
import com.hieu.blog.repositories.RoleRepository;
import com.hieu.blog.repositories.UserRepository;
import com.hieu.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	/* The method is create new user */
	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = dtoToUser(userDto);
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	/* The method updateUser(UserDTO userDto, Integer userId) which update user */

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		// The code under to update informations of user
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		// Then save it into database and converts user to userDto
		User updateUser = userRepository.save(user);
		UserDTO userDto1 = userToDto(updateUser);
		return userDto1;
	}

	/*
	 * The method getUserById(Integer userId) is get a user by user id
	 */

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return userToDto(user);
	}

	/*
	 * The method getAllUser() to get all user has in database
	 */

	@Override
	public List<UserDTO> getAllUser() {

		// Get users in database
		List<User> users = userRepository.findAll();

		// Using Stream API of java 8 , which converts list User object to UserDTO
		List<UserDTO> userDtos = users.stream()

				.map(user -> userToDto(user))

				.collect(Collectors.toList());

		return userDtos;
	}

	/*
	 * The method deleteUser(Integer userId) to delete user by user id
	 * 
	 */
	@Override
	public void deleteUser(Integer userId) {
		// find user in database ,if not fond , then return new
		// ResourceNotFoundException
		User user = userRepository.findById(userId).orElseThrow(

				() -> new ResourceNotFoundException("User ", "Id", userId)

		);
		// if found ,then delete
		userRepository.delete(user);

	}

	/*
	 * Date transfer object here
	 * 
	 * The method dtoToUser which converts userDto to user object using modelMapper
	 */

	private User dtoToUser(UserDTO userDto) {
		User user = modelMapper.map(userDto, User.class);
		/*
		 * user.setId(userDto.getId());
		 * 
		 * user.setName(userDto.getName());
		 * 
		 * user.setEmail(userDto.getEmail());
		 * 
		 * user.setPassword(userDto.getPassword());
		 * 
		 * user.setAbout(userDto.getAbout());
		 * 
		 */
		return user;
	}
	/*
	 * The method userToDto(User user) which converts user to userDto using
	 * modelMapper
	 */

	public UserDTO userToDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		/*
		 * userDto.setId(user.getId());
		 * 
		 * userDto.setName(user.getName());
		 * 
		 * userDto.setEmail(user.getEmail());
		 * 
		 * userDto.setPassword(user.getPassword());
		 * 
		 * userDto.setAbout(user.getAbout());
		 * 
		 */
		return userDto;

	}

	@Override
	public UserDTO registerNewUser(UserDTO userDTO) {

		User user = modelMapper.map(userDTO, User.class);

		// encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = roleRepository.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = userRepository.save(user);

		return modelMapper.map(newUser, UserDTO.class);
	}
}
