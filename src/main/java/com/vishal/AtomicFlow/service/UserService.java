package com.vishal.AtomicFlow.service;

import com.vishal.AtomicFlow.dto.CreateUserRequest;
import com.vishal.AtomicFlow.entity.Role;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        // role handling
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        } else {
            user.setRole(Role.MEMBER);
        }

        return userRepository.save(user);
    }
}