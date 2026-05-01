package com.vishal.AtomicFlow.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskRequest {

    private String title;
    private String description;
    private Long projectId;
    private Long assignedTo;
    private LocalDate dueDate;
}