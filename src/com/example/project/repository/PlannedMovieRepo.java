package com.example.project.repository;

import com.example.project.entiy.PlannedMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannedMovieRepo extends JpaRepository<PlannedMovie, Long> {
}
