package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.CreateUserRequest;
import com.vishal.AtomicFlow.dto.UserResponse;
import com.vishal.AtomicFlow.entity.Role;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.UserRepository;
import com.vishal.AtomicFlow.security.JwtUtil;
import com.vishal.AtomicFlow.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserRepository userRepository,
                          UserService userService,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // 🔥 ADMIN create user
    @PostMapping
    public UserResponse createUser(
            @RequestBody CreateUserRequest request,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.substring(7));
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create users");
        }

        User saved = userService.createUser(request);

        return UserResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }

    // 🔹 Get all users
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .toList();
    }
}