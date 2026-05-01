package com.vishal.AtomicFlow.repository;

import com.vishal.AtomicFlow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}