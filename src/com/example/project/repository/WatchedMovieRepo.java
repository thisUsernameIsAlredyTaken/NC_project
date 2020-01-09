package com.example.project.repository;

import com.example.project.entiy.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchedMovieRepo extends JpaRepository<WatchedMovie, Long> {
}
