package com.example.project.repos;

import com.example.project.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepos extends JpaRepository<Movie, String> {
}
