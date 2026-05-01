package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.ProjectRequest;
import com.vishal.AtomicFlow.dto.ProjectResponse;
import com.vishal.AtomicFlow.security.JwtUtil;
import com.vishal.AtomicFlow.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    public ProjectController(ProjectService projectService, JwtUtil jwtUtil) {
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ProjectResponse createProject(
            @RequestBody ProjectRequest request,
            @RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractEmail(token.substring(7));
        return projectService.createProject(request, email);
    }

    @GetMapping
    public List<ProjectResponse> getAllProjects() {
        return projectService.getAllProjects();
    }
}