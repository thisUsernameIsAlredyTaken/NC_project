package com.example.project.repository;

import com.example.project.entiy.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedMovieRepo extends JpaRepository<WatchedMovie, Long> {
}
