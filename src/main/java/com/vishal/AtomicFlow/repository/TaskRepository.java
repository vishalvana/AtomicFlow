package com.vishal.AtomicFlow.repository;

import com.vishal.AtomicFlow.entity.Status;
import com.vishal.AtomicFlow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedToId(Long userId);
    long countByAssignedToId(Long userId);

    long countByAssignedToIdAndStatus(Long userId, Status status);

    List<Task> findByAssignedToIdAndDueDateBefore(Long userId, LocalDate date);

    List<Task> findByProjectId(Long projectId);
}