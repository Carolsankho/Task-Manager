package com.manage.task.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manage.task.model.Role;
import com.manage.task.model.User;
import com.manage.task.repository.RoleRepository;
import com.manage.task.repository.TaskRepository;
import com.manage.task.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
    private static final String ADMIN="ROLE_ADMIN";
    private static final String USER="ROLE_USER";

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = encoder;
    }

    @Override
    public User createUser(User user) {
    	System.out.println("in here");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRole(USER);
        System.out.println(userRole);
        user.setRoles(userRole);
//        user.setRoles(userrol)
        System.out.println(user);
        return userRepository.save(user);
    }

    @Override
    public User changeRoleToAdmin(User user) {
        Role adminRole = roleRepository.findByRole(ADMIN);
        user.setRoles(adminRole);
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public boolean isUserEmailPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.getOne(id);
        user.getTasksOwned().forEach(task -> task.setOwner(null));
        userRepository.delete(user);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("USER DOES NOT EXIST");
		}
		return user.get();
	}

}

