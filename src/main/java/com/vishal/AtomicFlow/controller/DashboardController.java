package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.DashboardResponse;
import com.vishal.AtomicFlow.security.JwtUtil;
import com.vishal.AtomicFlow.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final JwtUtil jwtUtil;

    public DashboardController(DashboardService dashboardService,
                               JwtUtil jwtUtil) {
        this.dashboardService = dashboardService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public DashboardResponse getDashboard(
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.substring(7));
        return dashboardService.getDashboard(email);
    }
}