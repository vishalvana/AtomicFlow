package com.vishal.AtomicFlow.service;

import com.vishal.AtomicFlow.dto.TaskRequest;
import com.vishal.AtomicFlow.dto.TaskResponse;
import com.vishal.AtomicFlow.entity.*;
import com.vishal.AtomicFlow.repository.ProjectRepository;
import com.vishal.AtomicFlow.repository.TaskRepository;
import com.vishal.AtomicFlow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    // 🔥 Create Task
    public TaskResponse createTask(TaskRequest request) {


        User user = userRepository.findById(request.getAssignedTo())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setAssignedTo(user);
        task.setProject(project);
        task.setStatus(Status.TODO);
        task.setDueDate(request.getDueDate());

        taskRepository.save(task);

        return mapToResponse(task);
    }

    // 🔹 Get tasks for a user
    public List<TaskResponse> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedToId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 Update status
    public TaskResponse updateStatus(Long taskId, Status status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(status);
        taskRepository.save(task);

        return mapToResponse(task);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus().name())
                .assignedTo(task.getAssignedTo().getEmail())
                .project(task.getProject().getName())
                .dueDate(task.getDueDate().toString())
                .build();
    }
}