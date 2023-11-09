package com.sd.usermanagement;

import com.sd.usermanagement.model.ApplicationUser;
import com.sd.usermanagement.model.Role;
import com.sd.usermanagement.repository.RoleRepository;
import com.sd.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SecureUserManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecureUserManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository,
						  UserRepository userRepository,
						  PasswordEncoder passwordEncoder) {
		return args -> {
			if(roleRepository.findByAuthority("ADMIN").isPresent()) {
				return;
			}
			Role adminRole = roleRepository.save(new Role(1,"ADMIN"));
			roleRepository.save(new Role(2,"USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			ApplicationUser admin = new ApplicationUser("admin", passwordEncoder.encode("admin"), roles);
			userRepository.save(admin);
		};
	}

	@Value("${devicemicroservice.port}")
	private int device_port;
	@Value("${devicemicroservice.host}")
	private String device_host;
	@Bean
	public WebClient webClient() {
		return WebClient.builder().baseUrl("http://localhost:" + device_port + "/api").build();
	}
}
