package com.vishal.AtomicFlow.service;

import com.vishal.AtomicFlow.dto.DashboardResponse;
import com.vishal.AtomicFlow.dto.TaskResponse;
import com.vishal.AtomicFlow.entity.Status;
import com.vishal.AtomicFlow.entity.Task;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.TaskRepository;
import com.vishal.AtomicFlow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public DashboardService(TaskRepository taskRepository,
                            UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long userId = user.getId();

        long total = taskRepository.countByAssignedToId(userId);
        long todo = taskRepository.countByAssignedToIdAndStatus(userId, Status.TODO);
        long inProgress = taskRepository.countByAssignedToIdAndStatus(userId, Status.IN_PROGRESS);
        long done = taskRepository.countByAssignedToIdAndStatus(userId, Status.DONE);

        List<Task> overdue = taskRepository
                .findByAssignedToIdAndDueDateBefore(userId, LocalDate.now());

        List<TaskResponse> overdueTasks = overdue.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        List<TaskResponse> myTasks = taskRepository.findByAssignedToId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return DashboardResponse.builder()
                .totalTasks(total)
                .todo(todo)
                .inProgress(inProgress)
                .done(done)
                .overdueTasks(overdueTasks)
                .myTasks(myTasks)
                .build();
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