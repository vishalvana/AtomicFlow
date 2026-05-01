package com.vishal.AtomicFlow.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String createdBy;
    private List<String> members;
}