package com.project.movies;

import com.project.movies.role.Role;
import com.project.movies.role.RoleRepository;
import com.project.movies.user.User;
import com.project.movies.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@SpringBootApplication
@EnableJpaAuditing
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}



	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initializeRoles(roleRepository);
			initializeAdmin(roleRepository, userRepository, passwordEncoder);
		};
	}

	@Transactional  // Ensures data consistency and avoids detached entity issues
	public void initializeRoles(RoleRepository roleRepository) {
		if (roleRepository.findByName("USER").isEmpty()) {
			roleRepository.save(Role.builder().name("USER").build());
		}

		if (roleRepository.findByName("ADMIN").isEmpty()) {
			roleRepository.save(Role.builder().name("ADMIN").build());
		}
	}

	@Transactional
	public void initializeAdmin(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		Role adminRole = roleRepository.findByName("ADMIN")
				.orElseGet(() -> {
					Role newRole = Role.builder().name("ADMIN").build();
					return roleRepository.save(newRole);
				});

		if (userRepository.findByEmail("admin@example.com").isEmpty()) {
			User adminUser = User.builder()
					.email("admin@example.com")
					.password(passwordEncoder.encode("Admin@1234"))
					.firstname("Admin")
					.lastname("User")
					.roles(Collections.singletonList(adminRole)) // Ensure role is attached
					.build();
			userRepository.save(adminUser);
			System.out.println("Default admin user created successfully.");
		} else {
			System.out.println("Admin user already exists.");
		}
	}

}
