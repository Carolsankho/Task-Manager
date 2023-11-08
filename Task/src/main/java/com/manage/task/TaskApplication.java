package com.manage.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.manage.task.model.Role;
import com.manage.task.model.User;
import com.manage.task.repository.RoleRepository;
import com.manage.task.repository.UserRepository;

@SpringBootApplication
public class TaskApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
//		return args -> {
//			Role user = roleRepo.save(new Role("ROLE_USER"));
//			Role admin = roleRepo.save(new Role("ROLE_ADMIN"));
//			userRepo.save(new User("carolsankho@gmail.com","Caroline",encoder.encode("admin"),null,null,roleRepo.findByRole("ROLE_ADMIN")));
//		};
//	}

}
