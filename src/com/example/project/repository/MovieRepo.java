package com.example.project.repository;

import com.example.project.entiy.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, String> {
}
