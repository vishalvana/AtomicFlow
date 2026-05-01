package com.vishal.AtomicFlow.dto;

import lombok.Data;
import java.util.List;

@Data
public class ProjectRequest {
    private String name;
    private List<Long> memberIds; // users to add
}