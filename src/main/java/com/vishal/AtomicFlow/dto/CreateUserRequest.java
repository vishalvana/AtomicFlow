package com.vishal.AtomicFlow.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private String role; // ADMIN or MEMBER
}