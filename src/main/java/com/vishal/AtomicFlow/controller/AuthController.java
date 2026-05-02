package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.AuthRequest;
import com.vishal.AtomicFlow.dto.AuthResponse;
import com.vishal.AtomicFlow.dto.UserResponse;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.UserRepository;
import com.vishal.AtomicFlow.security.JwtUtil;
import com.vishal.AtomicFlow.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          JwtUtil jwtUtil,
                          UserRepository userRepository) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // 🔹 Signup
    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody User user) {
        return new AuthResponse(authService.signup(user));
    }

    // 🔹 Login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return new AuthResponse(authService.login(request));
    }

    // 🔥 Get current logged-in user
    @GetMapping("/me")
    public UserResponse getCurrentUser(
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.substring(7));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}