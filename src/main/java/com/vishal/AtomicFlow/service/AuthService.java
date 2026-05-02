package com.vishal.AtomicFlow.service;

import com.vishal.AtomicFlow.dto.AuthRequest;
import com.vishal.AtomicFlow.entity.Role;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.UserRepository;
import com.vishal.AtomicFlow.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String signup(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.MEMBER);
        userRepository.save(user);

        return jwtUtil.generateToken(user);
    }

    public String login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user);
    }
}