package com.vishal.AtomicFlow.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String status;
    private String assignedTo;
    private String project;
    private String dueDate;
}