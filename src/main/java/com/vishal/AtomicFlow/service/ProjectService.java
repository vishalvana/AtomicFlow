package com.vishal.AtomicFlow.service;

import com.vishal.AtomicFlow.dto.ProjectRequest;
import com.vishal.AtomicFlow.dto.ProjectResponse;
import com.vishal.AtomicFlow.entity.Project;
import com.vishal.AtomicFlow.entity.Role;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.ProjectRepository;
import com.vishal.AtomicFlow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // 🔥 Create Project (ADMIN only)
    public ProjectResponse createProject(ProjectRequest request, String userEmail) {

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // RBAC check
        if (creator.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only ADMIN can create project");
        }

        List<User> members = userRepository.findByIdIn(request.getMemberIds());

        Project project = new Project();
        project.setName(request.getName());
        project.setCreatedBy(creator);
        project.setMembers(members);

        projectRepository.save(project);

        return mapToResponse(project);
    }

    // 🔹 Get all projects
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProjectResponse mapToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .createdBy(project.getCreatedBy().getEmail())
                .members(project.getMembers()
                        .stream()
                        .map(User::getEmail)
                        .collect(Collectors.toList()))
                .build();
    }
}