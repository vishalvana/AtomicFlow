package com.vishal.AtomicFlow.controller;

import com.vishal.AtomicFlow.dto.TaskRequest;
import com.vishal.AtomicFlow.dto.TaskResponse;
import com.vishal.AtomicFlow.entity.Status;
import com.vishal.AtomicFlow.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 🔥 Create task
    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    // 🔹 Get tasks by user
    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTasks(@PathVariable Long userId) {
        return taskService.getTasksByUser(userId);
    }

    // 🔹 Update status
    @PutMapping("/{taskId}/status")
    public TaskResponse updateStatus(
            @PathVariable Long taskId,
            @RequestParam Status status) {

        return taskService.updateStatus(taskId, status);
    }
}