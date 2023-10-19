package com.hieu.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hieu.blog.config.AppConstants;
import com.hieu.blog.entities.Role;
import com.hieu.blog.repositories.RoleRepository;

@SpringBootApplication
//@ComponentScan("com.hieu.blog.*")
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("test1"));

		try {

			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role roleUser = new Role();
			roleUser.setId(AppConstants.NORMAL_USER);
			roleUser.setName("NORMAL_USER");

			List<Role> roles = List.of(role, roleUser);
			List<Role> result = roleRepository.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());

			});
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
