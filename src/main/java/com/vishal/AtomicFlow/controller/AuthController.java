package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.AuthRequest;
import com.vishal.AtomicFlow.dto.AuthResponse;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody User user) {
        return new AuthResponse(authService.signup(user));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return new AuthResponse(authService.login(request));
    }
}