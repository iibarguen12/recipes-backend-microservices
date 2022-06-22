package com.abn.userservice;

import com.abn.userservice.domain.Role;
import com.abn.userservice.domain.Roles;
import com.abn.userservice.domain.User;
import com.abn.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableEurekaClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			//Add roles
			userService.saveRole(Role.builder().id(1L).name(Roles.ROLE_USER.name()).build());
			userService.saveRole(Role.builder().id(2L).name(Roles.ROLE_ADMIN.name()).build());

			//Add users

			userService.saveUser(User.builder().name("ABN User").username("abn_user").password("user").build());
			userService.saveUser(User.builder().name("ABN Admin").username("abn_admin").password("admin").build());

			//Add roles to users
			userService.addRoleToUser("abn_user", Roles.ROLE_USER.name());
			userService.addRoleToUser("abn_admin", Roles.ROLE_ADMIN.name());

		};
	}

}
