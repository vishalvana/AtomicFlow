package com.vishal.AtomicFlow.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponse {

    private long totalTasks;
    private long todo;
    private long inProgress;
    private long done;

    private List<TaskResponse> overdueTasks;
    private List<TaskResponse> myTasks;
}