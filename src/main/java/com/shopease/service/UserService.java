package com.shopease.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopease.entity.User;
import com.shopease.repository.UserRepository;


@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    public User register(String name, String email, String password) {
    	 if (userRepository.existsByEmail(email)) {
             throw new RuntimeException("Email already registered!");
         }

         User user = new User();

         user.setName(name);
         user.setEmail(email);
         user.setPassword(passwordEncoder.encode(password));
         user.setRole(User.Role.USER);

         return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public long getTotalUsers() {
        return userRepository.count();
    }
}